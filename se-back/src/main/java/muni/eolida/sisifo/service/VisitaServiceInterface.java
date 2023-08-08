package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.model.VisitaModel;


public interface VisitaServiceInterface extends GenericService<VisitaModel> {
    EntityMessenger<VisitaModel> buscarTodosPorIp(String ip);
    EntityMessenger<VisitaModel> findTopN(int numero);
}
