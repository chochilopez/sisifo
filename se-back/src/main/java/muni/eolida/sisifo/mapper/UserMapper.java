package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.creation.UserCreation;
import muni.eolida.sisifo.mapper.dto.UserDataTransferObject;
import muni.eolida.sisifo.security.models.Role;
import muni.eolida.sisifo.security.models.User;
import muni.eolida.sisifo.security.models.enums.RoleEnum;
import muni.eolida.sisifo.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserMapper {
    @Autowired
    RoleRepository repository;

    public UserDataTransferObject toDto(User userModel) {
        try {
            log.info("User entity to dto.");
            UserDataTransferObject dto = new UserDataTransferObject();

            Set<RoleEnum> objectList = new HashSet<>();
            dto.setId(userModel.getId().toString());
            dto.setName(userModel.getName());
            dto.setIdNumber(userModel.getIdNumber());
            dto.setAddress(userModel.getAddress());
            dto.setTelephone(userModel.getTelephone());
            dto.setUsername(userModel.getUsername());
            dto.setPassword(userModel.getPassword());

            Set<String> authorities = new HashSet<>();
            for (Role authorityEnum:userModel.getRoles()) {
                authorities.add(authorityEnum.toString());
            }
            dto.setRoles(authorities);

            return dto;
        } catch (Exception e) {
            log.info("User entity to dto error. Exception: " + e);
            return null;
        }
    }

    public User toEntity(UserCreation userCreation) {
        try {
            log.info("User creation to entity.");
            User userModel = new User();

            if (!Helper.isEmptyString(userCreation.getId()))
                userModel.setId(Helper.getLong(userCreation.getId()));
            userModel.setName(userCreation.getName());
            userModel.setIdNumber(userCreation.getIdNumber());
            userModel.setAddress(userCreation.getAddress());
            userModel.setTelephone(userCreation.getTelephone());
            userModel.setUsername(userCreation.getUsername());
            userModel.setPassword(userCreation.getPassword());
            if (!userCreation.getRoles().isEmpty()) {
                HashSet<Role> authorityEnums = new HashSet<>();
                for (String authority : userCreation.getRoles()) {
                    authorityEnums.add(repository.findByRole(RoleEnum.valueOf(authority)).get());
                }
            }

            return userModel;
        } catch (Exception e) {
            log.info("User creation to entity error. Exception: " + e);
            return null;
        }
    }
}