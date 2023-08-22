package muni.eolida.sisifo.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import muni.eolida.sisifo.mapper.dto.AutenticacionRequestDTO;
import muni.eolida.sisifo.mapper.dto.AutenticacionResponseDTO;
import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.io.IOException;

public interface AutenticacionService extends LogoutHandler {
    EntityMessenger<UsuarioModel> validarToken (Long id, String token);
    EntityMessenger<UsuarioModel> registrar (UsuarioCreation usuarioCreation);
    EntityMessenger<AutenticacionResponseDTO> ingresar (AutenticacionRequestDTO autenticacionRequestDTO);
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
