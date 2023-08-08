package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.mapper.dto.UsuarioDataTransferObject;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import muni.eolida.sisifo.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioMapper {
    private final RolRepository repository;

    public UsuarioDataTransferObject toDto(UsuarioModel userModel) {
        try {
            log.info("User entity to dto.");
            UsuarioDataTransferObject dto = new UsuarioDataTransferObject();

            List<RolEnum> objectList = new ArrayList<>();
            dto.setId(userModel.getId().toString());
            dto.setNombre(userModel.getNombre());
            dto.setDni(userModel.getDni());
            dto.setDireccion(userModel.getDireccion());
            dto.setTelefono(userModel.getTelefono());
            dto.setUsername(userModel.getUsername());
            dto.setPassword(userModel.getPassword());

            List<String> authorities = new ArrayList<>();
            for (RolModel authorityEnum:userModel.getRoles()) {
                authorities.add(authorityEnum.toString());
            }
            dto.setRoles(authorities);

            return dto;
        } catch (Exception e) {
            log.info("User entity to dto error. Exception: " + e);
            return null;
        }
    }

    public UsuarioModel toEntity(UsuarioCreation usuarioCreation) {
        try {
            log.info("User creation to entity.");
            UsuarioModel userModel = new UsuarioModel();

            if (!Helper.isEmptyString(usuarioCreation.getId()))
                userModel.setId(Helper.getLong(usuarioCreation.getId()));
            userModel.setNombre(usuarioCreation.getNombre());
            userModel.setDni(usuarioCreation.getDni());
            userModel.setDireccion(usuarioCreation.getDireccion());
            userModel.setTelefono(usuarioCreation.getTelefono());
            userModel.setUsername(usuarioCreation.getUsername());
            userModel.setPassword(usuarioCreation.getPassword());
            if (!usuarioCreation.getRoles().isEmpty()) {
                HashSet<RolModel> authorityEnums = new HashSet<>();
                for (String authority : usuarioCreation.getRoles()) {
                    authorityEnums.add(repository.findByRol(RolEnum.valueOf(authority)).get());
                }
            }

            return userModel;
        } catch (Exception e) {
            log.info("User creation to entity error. Exception: " + e);
            return null;
        }
    }
}