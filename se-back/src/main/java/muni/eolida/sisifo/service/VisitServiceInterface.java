package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.model.VisitModel;


public interface VisitServiceInterface extends GenericService<VisitModel> {
    EntityMessenger<VisitModel> findAllByIp(String ip);
    EntityMessenger<VisitModel> findTopN(int number);
    EntityMessenger<VisitModel> insert(VisitModel visitModel);
    EntityMessenger<VisitModel> update(VisitModel visitModel);
}
