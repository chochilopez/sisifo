package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.mapper.dto.UsuarioDTO;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.repository.UsuarioDAO;
import muni.eolida.sisifo.util.Helper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioMapper {
    private final PasswordEncoder bcryptEncoder;
    private final UsuarioDAO usuarioDAO;

    public UsuarioModel toEntity(UsuarioCreation usuarioCreation) {
        try {
            UsuarioModel usuarioModel = new UsuarioModel();

            if (Helper.getLong(usuarioCreation.getId()) != null) {
                usuarioModel = usuarioDAO.findByIdAndEliminadaIsNull(Helper.getLong(usuarioCreation.getId())).get();
            }
            usuarioModel.setNombre(usuarioCreation.getNombre());
            usuarioModel.setDni(usuarioCreation.getDni());
            usuarioModel.setDireccion(usuarioCreation.getDireccion());
            usuarioModel.setTelefono(usuarioCreation.getTelefono());
            usuarioModel.setUsername(usuarioCreation.getUsername());
            usuarioModel.setPassword(bcryptEncoder.encode(usuarioCreation.getPassword()));
            usuarioModel.setHabilitada(false);

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
            List<String> roles = new ArrayList<>();
            for (RolModel rol:usuarioModel.getRoles()) {
                roles.add(rol.getRol().toString());
            }

            return dto;
        } catch (Exception e) {
            log.info("Usuario entity to dto error. Exception: " + e);
            return null;
        }
    }
}