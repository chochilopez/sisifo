package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.ServicioGenerico;
import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.enums.RolEnum;

public interface RolService extends ServicioGenerico<RolCreation, RolModel> {
    EntidadMensaje<RolModel> buscarPorRol(RolEnum rol);
    EntidadMensaje<RolModel> buscarPorRolConEliminadas(RolEnum rol);
}
