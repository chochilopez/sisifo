package muni.eolida.sisifo.service;

import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;

public interface UsuarioService extends GenericService<UsuarioModel, UsuarioCreation> {
    EntityMessenger<UsuarioModel> buscarPorNombreDeUsuario(String nombreUsuario);
    EntityMessenger<UsuarioModel> buscarPorNombreDeUsuarioConEliminadas(String nombreUsuario);
    EntityMessenger<UsuarioModel> darRol(UsuarioModel usuarioModel, String rolEnum);
    EntityMessenger<UsuarioModel> obtenerUsuario();
    EntityMessenger<UsuarioModel> buscarPorNombreDeUsuarioHabilitados(String nombreUsuario);
    Boolean existeUsuarioPorNombreDeUsuario(String username);
}
