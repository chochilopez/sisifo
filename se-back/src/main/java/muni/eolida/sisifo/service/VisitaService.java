package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.VisitaCreation;
import muni.eolida.sisifo.model.VisitaModel;


public interface VisitaService extends GenericService<VisitaCreation, VisitaModel> {
    EntityMessenger<VisitaModel> buscarTodosPorIp(String ip);
    EntityMessenger<VisitaModel> buscarTodosPorIpConBorrados(String ip);
    EntityMessenger<VisitaModel> buscarLosPrimerosN(int number);
    EntityMessenger<VisitaModel> buscarLosPrimerosNConBorrados(int number);
}
