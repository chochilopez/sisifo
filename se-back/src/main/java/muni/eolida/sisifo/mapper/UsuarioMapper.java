package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.mapper.dto.UsuarioDTO;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.TokenModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.repository.RolDAO;
import muni.eolida.sisifo.repository.TokenDAO;
import muni.eolida.sisifo.repository.UsuarioDAO;
import muni.eolida.sisifo.util.Helper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioMapper {
    private final PasswordEncoder bcryptEncoder;
    private final UsuarioDAO usuarioDAO;
    private final TokenDAO tokenDAO;
    private final RolDAO rolDAO;

    public UsuarioModel toEntity(UsuarioCreation usuarioCreation) {
        UsuarioModel usuarioModel = new UsuarioModel();

        if (Helper.getLong(usuarioCreation.getId()) != null)
            usuarioModel = usuarioDAO.findByIdAndEliminadaIsNull(Helper.getLong(usuarioCreation.getId())).get();
        usuarioModel.setNombre(usuarioCreation.getNombre());
        usuarioModel.setDni(usuarioCreation.getDni());
        usuarioModel.setDireccion(usuarioCreation.getDireccion());
        usuarioModel.setTelefono(usuarioCreation.getTelefono());
        if (Helper.getBoolean(usuarioCreation.getHabilitada()) != null)
            usuarioModel.setHabilitada(Helper.getBoolean(usuarioCreation.getHabilitada()));
        else
            usuarioModel.setHabilitada(false);
        usuarioModel.setUsername(usuarioCreation.getUsername());
        usuarioModel.setPassword(bcryptEncoder.encode(usuarioCreation.getPassword()));
        if (usuarioCreation.getToken() != null)
            usuarioModel.setToken(usuarioCreation.getToken());


        if (usuarioCreation.getTokens_id() != null) {
            List<TokenModel> tokens = new ArrayList<>();
            for (String tokenId:usuarioCreation.getTokens_id()) {
                if (Helper.getLong(tokenId) != null) {
                    Optional<TokenModel> token = tokenDAO.findById(Helper.getLong(tokenId));
                    if (token.isPresent()) {
                        tokens.add(token.get());
                    }
                }
            }
            usuarioModel.setTokens(tokens);
        }

        if (usuarioCreation.getRoles_id() != null) {
            Set<RolModel> roles = new HashSet<>();
            for (String rolId:usuarioCreation.getRoles_id()) {
                if (Helper.getLong(rolId) != null) {
                    Optional<RolModel> rol = rolDAO.findByIdAndEliminadaIsNull(Helper.getLong(rolId));
                    if (rol.isPresent()) {
                        roles.add(rol.get());
                    }
                }
            }
            usuarioModel.setRoles(roles);
        }

        if (Helper.getLong(usuarioCreation.getCreador_id()) != null) {
            Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(usuarioCreation.getCreador_id()));
            if (user.isPresent())
                usuarioModel.setCreador(user.get());
        }
        if (!Helper.isEmptyString(usuarioCreation.getCreada()))
            usuarioModel.setCreada(Helper.stringToLocalDateTime(usuarioCreation.getCreada(), ""));
        if (Helper.getLong(usuarioCreation.getModificador_id()) != null) {
            Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(usuarioCreation.getModificador_id()));
            if (user.isPresent())
                usuarioModel.setModificador(user.get());
        }
        if (!Helper.isEmptyString(usuarioCreation.getModificada()))
            usuarioModel.setModificada(Helper.stringToLocalDateTime(usuarioCreation.getModificada(), ""));
        if (Helper.getLong(usuarioCreation.getEliminador_id()) != null) {
            Optional<UsuarioModel> user = usuarioDAO.findById(Helper.getLong(usuarioCreation.getEliminador_id()));
            if (user.isPresent())
                usuarioModel.setEliminador(user.get());
        }
        if (!Helper.isEmptyString(usuarioCreation.getEliminada()))
            usuarioModel.setEliminada(Helper.stringToLocalDateTime(usuarioCreation.getEliminada(), ""));

        return usuarioModel;
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
            dto.setRoles(roles);

            return dto;
        } catch (Exception e) {
            log.info("Usuario entity to dto error. Exception: " + e);
            return null;
        }
    }
}