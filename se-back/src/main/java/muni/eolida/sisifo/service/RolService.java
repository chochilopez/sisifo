package muni.eolida.sisifo.service;

import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.model.RolModel;

public interface RolService extends GenericService<RolModel, RolCreation> {
    EntityMessenger<RolModel> buscarPorRol(String rol);
    EntityMessenger<RolModel> buscarPorRolConEliminadas(String rol);
}
