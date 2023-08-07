package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.model.EmailModel;

public interface EmailServiceInterface extends GenericService<EmailModel> {
    EntityMessenger<EmailModel> sendSimpleMail(EmailModel emailModel);
    EntityMessenger<EmailModel> insert(EmailModel emailModel);
    EntityMessenger<EmailModel> update(EmailModel emailModel);
}
