package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.payload.request.LoginRequest;
import muni.eolida.sisifo.helper.payload.response.JwtResponse;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;

public interface AutenticacionService {
    EntityMessenger<UsuarioModel> validarToken (Long id, String token);
    EntityMessenger<UsuarioModel> registrarUsuario (UsuarioCreation usuarioCreation);
    EntityMessenger<JwtResponse> ingresarUsuario (LoginRequest loginRequest);
}
