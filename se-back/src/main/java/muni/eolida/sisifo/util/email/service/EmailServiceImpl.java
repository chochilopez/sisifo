package muni.eolida.sisifo.util.email.service;

import muni.eolida.sisifo.util.Helper;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.util.email.repository.EmailDAO;
import muni.eolida.sisifo.util.email.EmailModel;
import muni.eolida.sisifo.util.email.mapper.EmailCreation;
import muni.eolida.sisifo.util.email.mapper.EmailMapper;
import muni.eolida.sisifo.service.implementation.UsuarioServiceImpl;
import muni.eolida.sisifo.util.exception.CustomDataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("emailSenderService")
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailDAO emailDAO;
    @Autowired
    private EmailMapper emailMapper;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UsuarioServiceImpl usuarioService;

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
        EmailModel emailModel = emailDAO.findById(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Email con id " + id + "."));
        String mensaje = "Se encontro una entidad Email.";
        log.info(mensaje);
        return emailModel;
    }

    @Override
    public List<EmailModel> buscarTodas() {
        log.info("Buscando todas las entidades Email.");
        List<EmailModel> listado = emailDAO.findAll();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Email.");
        return listado;
    }

    @Override
    public Long contarTodas() {
        Long cantidad = emailDAO.count();
        log.info("Existen {} entidades Email.", cantidad);
        return cantidad;
    }

    @Override
    public EmailModel guardar(EmailCreation creation) {
        log.info("Insertando la entidad Email: {}.",  creation);
        EmailModel emailModel = emailDAO.save(emailMapper.toEntity(creation));
        emailModel.setCreada(Helper.getNow(""));
        emailModel.setCreador(usuarioService.obtenerUsuario());
        log.info("Se persistion correctamente la nueva entidad.");
        return emailDAO.save(emailModel);
    }

    @Override
    public Boolean destruir(Long id) {
        log.info("Destruyendo la entidad Email con id: {}.", id);
        EmailModel objeto = this.buscarPorId(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Email con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.");
            return false;
        }
        emailDAO.delete(objeto);
        log.info("La entidad fue destruida y el email " + objeto + " fue eliminado correctamente.");
        return true;
    }
}
