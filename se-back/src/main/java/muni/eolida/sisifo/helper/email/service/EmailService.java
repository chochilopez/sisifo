package muni.eolida.sisifo.helper.email.service;

import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.email.EmailModel;
import muni.eolida.sisifo.helper.email.mapper.EmailCreation;

public interface EmailService {
    EntidadMensaje<EmailModel> buscarPorId(Long id);
    EntidadMensaje<EmailModel> buscarTodas();
    Long contarTodas();
    EntidadMensaje<EmailModel> insertar(EmailCreation e);
    EntidadMensaje<EmailModel> actualizar(EmailModel t);
    EntidadMensaje<EmailModel> destruir(Long id);

    EntidadMensaje<EmailModel> enviarEmailSimple(EmailCreation emailCreation);
}
