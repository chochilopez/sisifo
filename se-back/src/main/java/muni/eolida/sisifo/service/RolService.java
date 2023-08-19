package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.model.RolModel;

public interface RolService extends GenericService<RolCreation, RolModel> {
    EntityMessenger<RolModel> buscarPorRol(String rol);
    EntityMessenger<RolModel> buscarPorRolConEliminadas(String rol);
}
