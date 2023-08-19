package muni.eolida.sisifo.helper.email.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.email.EmailModel;
import muni.eolida.sisifo.helper.email.mapper.EmailCreation;

public interface EmailService {
    EntityMessenger<EmailModel> buscarPorId(Long id);
    EntityMessenger<EmailModel> buscarTodas();
    Long contarTodas();
    EntityMessenger<EmailModel> insertar(EmailCreation e);
    EntityMessenger<EmailModel> actualizar(EmailModel t);
    EntityMessenger<EmailModel> destruir(Long id);

    EntityMessenger<EmailModel> enviarEmailSimple(EmailCreation emailCreation);
}
