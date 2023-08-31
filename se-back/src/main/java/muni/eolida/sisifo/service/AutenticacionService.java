package muni.eolida.sisifo.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import muni.eolida.sisifo.mapper.dto.AutenticacionRequestDTO;
import muni.eolida.sisifo.mapper.dto.AutenticacionResponseDTO;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;
import org.springframework.http.ResponseEntity;

public interface  AutenticacionService {
    UsuarioModel validarToken (Long id, String token);
    UsuarioModel registrar (UsuarioCreation usuarioCreation);
    AutenticacionResponseDTO ingresar (AutenticacionRequestDTO autenticacionRequestDTO);
    ResponseEntity<String> logout(HttpServletRequest request);
}
