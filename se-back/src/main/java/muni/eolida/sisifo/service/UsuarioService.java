package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.ServicioGenerico;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.RolEnum;

public interface UsuarioService extends ServicioGenerico<UsuarioCreation, UsuarioModel> {
    EntidadMensaje<UsuarioModel> buscarPorNombreDeUsuario(String nombreUsuario);
    EntidadMensaje<UsuarioModel> buscarPorNombreDeUsuarioConEliminadas(String nombreUsuario);
    EntidadMensaje<UsuarioModel> darRol(UsuarioModel usuarioModel, RolEnum rolEnum);
    EntidadMensaje<UsuarioModel> obtenerUsuario();
}
