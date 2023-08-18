package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.payload.request.LoginRequest;
import muni.eolida.sisifo.helper.payload.response.JwtResponse;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;

public interface AutenticacionService {
    EntidadMensaje<UsuarioModel> validarToken (Long id, String token);
    EntidadMensaje<UsuarioModel> registrarUsuario (UsuarioCreation usuarioCreation);
    EntidadMensaje<JwtResponse> ingresarUsuario (LoginRequest loginRequest);
}
