package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.model.UsuarioModel;


public interface UsuarioService extends GenericService<UsuarioModel> {
    EntityMessenger<UsuarioModel> buscarPorNombreDeUsuario(String nombreUsuario);
    EntityMessenger<UsuarioModel> buscarPorNombreDeUsuarioConBorrados(String nombreUsuario);
}
