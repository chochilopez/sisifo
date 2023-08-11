package muni.eolida.sisifo.repository;

import muni.eolida.sisifo.helper.GenericRepository;
import muni.eolida.sisifo.model.EmailModel;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailDAO extends GenericRepository<EmailModel> {
}
