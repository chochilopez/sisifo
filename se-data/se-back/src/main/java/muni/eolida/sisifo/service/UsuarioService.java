package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.RolEnum;


public interface UsuarioService extends GenericService<UsuarioCreation, UsuarioModel> {
    EntityMessenger<UsuarioModel> buscarPorNombreDeUsuario(String nombreUsuario);
    EntityMessenger<UsuarioModel> buscarPorNombreDeUsuarioConBorrados(String nombreUsuario);
    EntityMessenger<UsuarioModel> darRol(UsuarioModel usuarioModel, RolEnum rolEnum);
    EntityMessenger<UsuarioModel> obtenerUsuario();
}
