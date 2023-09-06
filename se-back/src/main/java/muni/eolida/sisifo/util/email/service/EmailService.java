package muni.eolida.sisifo.util.email.service;

import muni.eolida.sisifo.service.GenericService;
import muni.eolida.sisifo.util.email.EmailModel;
import muni.eolida.sisifo.util.email.mapper.EmailCreation;

public interface EmailService extends GenericService<EmailModel, EmailCreation> {
    EmailModel enviarEmailSimple(EmailCreation emailCreation);
}
