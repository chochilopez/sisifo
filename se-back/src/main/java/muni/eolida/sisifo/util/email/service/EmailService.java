package muni.eolida.sisifo.util.email.service;

import muni.eolida.sisifo.util.email.EmailModel;
import muni.eolida.sisifo.util.email.mapper.EmailCreation;

import java.util.List;

public interface EmailService {
    EmailModel buscarPorId(Long id);
    List<EmailModel> buscarTodas();
    Long contarTodas();
    EmailModel guardar(EmailCreation e);
    Boolean destruir(Long id);

    EmailModel enviarEmailSimple(EmailCreation emailCreation);
}
