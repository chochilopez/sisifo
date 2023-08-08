package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.repository.RolRepository;
import muni.eolida.sisifo.service.RolServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class RolServiceImpletation implements RolServiceInterface {

    private final RolRepository rolRepository;

    @Override
    public EntityMessenger<RolModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Rol con id: {}.", id);
        Optional<RolModel> object = rolRepository.findById(id);
        if (object.isEmpty()) {
            String message = "No existe la entidad Rol con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<RolModel>(null, null, message, 202);
        } else {
            String message = "Una entidad Rol encontrada.";
            log.info(message);
            return new EntityMessenger<RolModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<RolModel> buscarTodos() {
        log.info("Buscando todas las entidades Rol.");
        List<RolModel> list = rolRepository.findAll();
        if (list.isEmpty()) {
            String message = "No existen entidades Rol.";
            log.warn(message);
            return new EntityMessenger<RolModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entidades Rol fueron encontradas.";
            log.info(message);
            return new EntityMessenger<RolModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodos() {
        Long count = rolRepository.count();
        log.info("Existen {} entidades Rol.", count);
        return count;
    }

    @Override
    public EntityMessenger<RolModel> destruir(Long id) {
        log.info("Destruyendo la entidad Rol con id: {}.", id);
        EntityMessenger<RolModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        rolRepository.delete(entityMessenger.getObject());
        String message = "La entidad fue destruida.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<RolModel> insertar(RolModel rol) {
        try {
            log.info("Insertando entidad Rol: {}.",  rol);
            RolModel rolModel = rolRepository.save(rol);
            String message = "La entidad Rol con id: " + rolModel.getId() + " fue insertada correctamente.";
            log.info(message);
            return new EntityMessenger<RolModel>(rolModel, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrio un error al intentar persistir la entidad. Excepcion: " + e + ".";
            log.error(message);
            return new EntityMessenger<RolModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<RolModel> actualizar(RolModel rol) {
        try {
            log.info("Actualizando entidad Rol: {}.",  rol);
            if (rol.getId() != null) {
                EntityMessenger<RolModel> existant = this.buscarPorId(rol.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            RolModel rolModel = rolRepository.save(rol);
            String message = "La entidad Rol con id: " + rolModel.getId() + "fue actualizada correctamente.";
            log.info(message);
            return new EntityMessenger<RolModel>(rolModel, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrio un error al intentar persistir la entidad. Excepcion: " + e + ".";
            log.error(message);
            return new EntityMessenger<RolModel>(null, null, message, 204);
        }
    }
}
