package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.mapper.dto.UsuarioDTO;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.UsuarioModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioMapper {
    private final PasswordEncoder bcryptEncoder;

    public UsuarioModel toEntity(UsuarioCreation usuarioCreation) {
        try {
            UsuarioModel usuarioModel = new UsuarioModel();

            usuarioModel.setNombre(usuarioCreation.getNombre());
            usuarioModel.setDni(usuarioCreation.getDni());
            usuarioModel.setDireccion(usuarioCreation.getDireccion());
            usuarioModel.setTelefono(usuarioCreation.getTelefono());
            usuarioModel.setUsername(usuarioCreation.getUsername());
            usuarioModel.setHabilitada(false);
            usuarioModel.setPassword(bcryptEncoder.encode(usuarioCreation.getPassword()));

            return usuarioModel;
        } catch (Exception e) {
            log.info("Usuario creation to entity error. Exception: " + e);
            return null;
        }
    }

    public UsuarioDTO toDto(UsuarioModel usuarioModel) {
        try {
            UsuarioDTO dto = new UsuarioDTO();

            dto.setId(usuarioModel.getId().toString());
            dto.setNombre(usuarioModel.getNombre());
            dto.setDni(usuarioModel.getDni());
            dto.setDireccion(usuarioModel.getDireccion());
            dto.setTelefono(usuarioModel.getTelefono());
            dto.setUsername(usuarioModel.getUsername());

            return dto;
        } catch (Exception e) {
            log.info("Usuario entity to dto error. Exception: " + e);
            return null;
        }
    }
}