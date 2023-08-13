package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.mapper.dto.UsuarioDTO;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.UsuarioModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioMapper {
    private final PasswordEncoder bcryptEncoder;

    public UsuarioModel toEntity(UsuarioCreation usuarioCreation) {
        try {
            log.info("Usuario creation to entity.");
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
            log.info("Usuario entity to dto.");
            UsuarioDTO dto = new UsuarioDTO();

            dto.setNombre(usuarioModel.getNombre());
            dto.setDni(usuarioModel.getDni());
            dto.setDireccion(usuarioModel.getDireccion());
            dto.setTelefono(usuarioModel.getTelefono());
            dto.setUsername(usuarioModel.getUsername());
            List<String> roles = new ArrayList<>();
            for (RolModel rolModel:usuarioModel.getRoles()) {
                roles.add(rolModel.getRol().name());
            }
            dto.setRoles(roles);

            return dto;
        } catch (Exception e) {
            log.info("Usuario entity to dto error. Exception: " + e);
            return null;
        }
    }
}