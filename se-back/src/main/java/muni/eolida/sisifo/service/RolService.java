package muni.eolida.sisifo.service;

import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.model.RolModel;

public interface RolService extends GenericService<RolModel, RolCreation> {
    RolModel buscarPorRol(String rol);
    RolModel buscarPorRolConEliminadas(String rol);
}
