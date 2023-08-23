package muni.eolida.sisifo.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import muni.eolida.sisifo.mapper.dto.AutenticacionRequestDTO;
import muni.eolida.sisifo.mapper.dto.AutenticacionResponseDTO;
import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface  AutenticacionService {
    EntityMessenger<UsuarioModel> validarToken (Long id, String token);
    EntityMessenger<UsuarioModel> registrar (UsuarioCreation usuarioCreation);
    EntityMessenger<AutenticacionResponseDTO> ingresar (AutenticacionRequestDTO autenticacionRequestDTO);
    EntityMessenger<AutenticacionResponseDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) ;
    ResponseEntity<String> logout(HttpServletRequest request);
}
