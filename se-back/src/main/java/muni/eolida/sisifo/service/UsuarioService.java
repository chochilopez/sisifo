package muni.eolida.sisifo.service;

import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;

public interface UsuarioService extends GenericService<UsuarioModel, UsuarioCreation> {
    UsuarioModel buscarMisDatos();
    UsuarioModel buscarPorNombreDeUsuario(String nombreUsuario);
    UsuarioModel buscarPorNombreDeUsuarioConEliminadas(String nombreUsuario);
    UsuarioModel darRol(UsuarioModel usuarioModel, String rolEnum);
    UsuarioModel obtenerUsuario();
    UsuarioModel buscarPorNombreDeUsuarioHabilitado(String nombreUsuario);
    Boolean existeUsuarioPorNombreDeUsuario(String username);
}
