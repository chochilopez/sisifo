package muni.eolida.sisifo.helper.email.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.email.EmailModel;
import muni.eolida.sisifo.helper.email.mapper.EmailCreation;

public interface EmailService {
    EntityMessenger<EmailModel> findById(Long id);
    EntityMessenger<EmailModel> findAll();
    Long countAll();
    EntityMessenger<EmailModel> insert(EmailCreation emailCreation);
    EntityMessenger<EmailModel> destroy(Long id);

    EntityMessenger<EmailModel> sendSimpleEmail(EmailCreation emailCreation);
}
