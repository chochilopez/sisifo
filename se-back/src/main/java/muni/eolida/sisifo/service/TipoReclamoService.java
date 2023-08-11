package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.model.EmailModel;

public interface SeguimientoService extends GenericService<EmailModel> {
    EntityMessenger<EmailModel> enviarEmailSimple(EmailModel emailModel);
}
