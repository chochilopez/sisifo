package muni.eolida.sisifo.util.email.repository;

import muni.eolida.sisifo.repository.GenericDTO;
import muni.eolida.sisifo.util.email.EmailModel;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailDAO extends GenericDTO<EmailModel> {
}
