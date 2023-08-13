package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.enums.RolEnum;

public interface RolService extends GenericService<RolCreation, RolModel> {
    EntityMessenger<RolModel> buscarTodosPorRol(RolEnum rol);
    EntityMessenger<RolModel> buscarTodosPorRolConBorrados(RolEnum rol);
}
