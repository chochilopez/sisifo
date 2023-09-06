package muni.eolida.sisifo.util.email.service;

import lombok.RequiredArgsConstructor;
import muni.eolida.sisifo.util.Helper;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.util.email.repository.EmailDAO;
import muni.eolida.sisifo.util.email.EmailModel;
import muni.eolida.sisifo.util.email.mapper.EmailCreation;
import muni.eolida.sisifo.util.email.mapper.EmailMapper;
import muni.eolida.sisifo.service.implementation.UsuarioServiceImpl;
import muni.eolida.sisifo.util.exception.CustomDataNotFoundException;
import muni.eolida.sisifo.util.exception.CustomObjectNotDeletedException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("emailSenderService")
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final EmailDAO emailDAO;
    private final EmailMapper emailMapper;
    private final JavaMailSender javaMailSender;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EmailModel enviarEmailSimple(EmailCreation emailCreation) {
        SimpleMailMessage objeto = new SimpleMailMessage();
        objeto.setFrom(emailCreation.getSender());
        objeto.setSubject(emailCreation.getSubject());
        objeto.setText(emailCreation.getBody());
        objeto.setTo(emailCreation.getReciever());
        javaMailSender.send(objeto);
        log.info("Email enviado correctamente: {}.", objeto);
        return this.guardar(emailCreation);
    }

    @Override
    public EmailModel buscarPorId(Long id) {
        log.info("Buscando la entidad Email con id: {}.", id);
        EmailModel emailModel = emailDAO.findByIdAndEliminadaIsNull(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Email con id: " + id + "."));
        String mensaje = "Se encontro una entidad Email.";
        log.info(mensaje);
        return emailModel;
    }

    @Override
    public EmailModel buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Email con id: {}, incluidas las eliminadas.", id);
        EmailModel emailModel = emailDAO.findById(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Email con id: " + id +", incluidas las eliminadas."));
        log.info("Se encontro una entidad Email con id: " + id + ".");
        return emailModel;
    }

    @Override
    public List<EmailModel> buscarTodas() {
        log.info("Buscando todas las entidades Email.");
        List<EmailModel> listado = emailDAO.findAllByEliminadaIsNull();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Email.");
        return listado;
    }

    @Override
    public List<EmailModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Email, incluidas las eliminadas.");
        List<EmailModel> listado = emailDAO.findAll();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Email, incluidas las eliminadas.");
        return listado;
    }

    @Override
    public Long contarTodas() {
        Long cantidad = emailDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Email.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = emailDAO.count();
        log.info("Existen {} entidades Email, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public EmailModel guardar(EmailCreation creation) {
        log.info("Insertando la entidad Email: {}.",  creation);
        EmailModel emailModel = emailDAO.save(emailMapper.toEntity(creation));
        if (creation.getId() == null) {
            emailModel.setCreada(Helper.getNow(""));
            emailModel.setCreador(usuarioService.obtenerUsuario());
            log.info("Se persistio correctamente la nueva entidad.");
        } else {
            emailModel.setModificada(Helper.getNow(""));
            emailModel.setModificador(usuarioService.obtenerUsuario());
            log.info("Se persistio correctamente la entidad.");
        }
        return emailDAO.save(emailModel);
    }

    @Override
    public EmailModel eliminar(Long id) {
        log.info("Eliminando la entidad Email con id: {}.", id);
        EmailModel objeto = this.buscarPorId(id);
        objeto.setEliminada(Helper.getNow(""));
        objeto.setEliminador(usuarioService.obtenerUsuario());
        log.info("La entidad Email con id: " + id + ", fue eliminada correctamente.");
        return emailDAO.save(objeto);
    }

    @Override
    public EmailModel reciclar(Long id) {
        log.info("Reciclando la entidad Email con id: {}.", id);
        EmailModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Email con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.");
            throw new CustomObjectNotDeletedException("No se puede reciclar la entidad.");
        }
        objeto.setEliminada(null);
        objeto.setEliminador(null);
        log.info("La entidad Email con id: " + id + ", fue reciclada correctamente.");
        return emailDAO.save(objeto);
    }

    @Override
    public void destruir(Long id) {
        log.info("Destruyendo la entidad Email con id: {}.", id);
        EmailModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Email con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.");
            throw new CustomObjectNotDeletedException("No se puede destruir la entidad.");
        }
        emailDAO.delete(objeto);
        log.info("La entidad fue destruida.");
    }
}
