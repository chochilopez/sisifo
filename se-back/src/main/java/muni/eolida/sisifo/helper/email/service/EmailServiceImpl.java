package muni.eolida.sisifo.helper.email.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.email.repository.EmailDAO;
import muni.eolida.sisifo.helper.email.EmailModel;
import muni.eolida.sisifo.helper.email.mapper.EmailCreation;
import muni.eolida.sisifo.helper.email.mapper.EmailMapper;
import muni.eolida.sisifo.service.implementation.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public EntityMessenger<EmailModel> enviarEmailSimple(EmailCreation emailCreation) {
        try {
            SimpleMailMessage objeto = new SimpleMailMessage();
            objeto.setFrom(emailCreation.getSender());
            objeto.setSubject(emailCreation.getSubject());
            objeto.setText(emailCreation.getBody());
            objeto.setTo(emailCreation.getReciever());
            javaMailSender.send(objeto);
            log.info("Email enviado correctamente: {}.", objeto);
            return this.insertar(emailCreation);
        } catch (Exception e) {
            String mensaje = "Ocurrio un error al intentar enviar el email. Excepcion: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<EmailModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<EmailModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Email con id: {}.", id);
        Optional<EmailModel> objeto = emailDAO.findById(id);
        if (objeto.isEmpty()) {
            String mensaje = "No se encontro una entidad Email con id: " + id + ".";
            log.warn(mensaje);
            return new EntityMessenger<EmailModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontro una entidad Email.";
            log.info(mensaje);
            return new EntityMessenger<EmailModel>(objeto.get(), null, mensaje, 200);
        }
    }

    @Override
    public EntityMessenger<EmailModel> buscarTodas() {
        log.info("Buscando todas las entidades Email.");
        List<EmailModel> listado = emailDAO.findAll();
        if (listado.isEmpty()) {
            String mensaje = "No se encontraron entidades Email.";
            log.warn(mensaje);
            return new EntityMessenger<EmailModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontraron " + listado.size() + " entidades Email.";
            log.info(mensaje);
            return new EntityMessenger<EmailModel>(null, listado, mensaje, 200);
        }
    }

    @Override
    public Long contarTodas() {
        Long cantidad = emailDAO.count();
        log.info("Existen {} entidades Email.", cantidad);
        return cantidad;
    }

    @Override
    public EntityMessenger<EmailModel> insertar(EmailCreation model) {
        try {
            log.info("Insertando la entidad Email: {}.",  model);
            EmailModel objeto = emailDAO.save(emailMapper.toEntity(model));
            objeto.setCreada(Helper.getNow(""));
            objeto.setCreador(usuarioService.obtenerUsuario().getObjeto());
            emailDAO.save(objeto);
            String mensaje = "La entidad Email con id: " + objeto.getId() + ", fue insertada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<EmailModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar insertar la entidad Email. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<EmailModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<EmailModel> actualizar(EmailModel model) {
        try {
            log.info("Actualizando la entidad Email: {}.",  model);
            if (model.getId() != null) {
                EntityMessenger<EmailModel> entidad = this.buscarPorId(model.getId());
                if (entidad.getEstado() == 202)
                    return entidad;
            }
            model.setModificada(Helper.getNow(""));
            model.setModificador(usuarioService.obtenerUsuario().getObjeto());
            EmailModel objeto = emailDAO.save(model);
            String mensaje = "La entidad Email con id: " + objeto.getId() + ", fue actualizada correctamente.";
            log.info(mensaje);
            return new EntityMessenger<EmailModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar actualizar la entidad Email. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<EmailModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntityMessenger<EmailModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Email con id: {}.", id);
            EntityMessenger<EmailModel> objeto = this.buscarPorId(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad Email con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(mensaje);
                objeto.setEstado(202);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            emailDAO.delete(objeto.getObjeto());
            String mensaje = "La entidad fue destruida correctamente.";
            objeto.setMensaje(mensaje);
            objeto.setObjeto(null);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar destruir la entidad Email. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntityMessenger<EmailModel>(null, null, mensaje, 204);
        }
    }
}
