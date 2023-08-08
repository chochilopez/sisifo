package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.repository.UsuarioRepository;
import muni.eolida.sisifo.service.UsuarioServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UsuarioServiceImpletation implements UsuarioServiceInterface {
    
    private final UsuarioRepository usuarioRepository;

    @Override
    public EntityMessenger<UsuarioModel> buscarPorNombreDeUsuario(String nombreUsuario) {
        log.info("Buscando la entidad Usuario con nombre de usuario: {}.", nombreUsuario);
        Optional<UsuarioModel> object = usuarioRepository.findByUsername(nombreUsuario);
        if (object.isEmpty()) {
            String message = "No existe la entidad Usuario con nombre de usuario: " + nombreUsuario + ".";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        } else {
            String message = "Una entidad Usuario encontrada.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(object.get(), null, message, 200);
        }
    }
    
    @Override
    public EntityMessenger<UsuarioModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Usuario con id: {}.", id);
        Optional<UsuarioModel> object = usuarioRepository.findById(id);
        if (object.isEmpty()) {
            String message = "No existe la entidad Usuario con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        } else {
            String message = "Una entidad Usuario encontrada.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> buscarTodos() {
        log.info("Buscando todas las entidades Usuario.");
        List<UsuarioModel> list = usuarioRepository.findAll();
        if (list.isEmpty()) {
            String message = "No existen entidades Usuario.";
            log.warn(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entidades Usuario fueron encontradas.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodos() {
        Long count = usuarioRepository.count();
        log.info("Existen {} entidades Usuario.", count);
        return count;
    }

    @Override
    public EntityMessenger<UsuarioModel> destruir(Long id) {
        log.info("Destruyendo la entidad Usuario con id: {}.", id);
        EntityMessenger<UsuarioModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        usuarioRepository.delete(entityMessenger.getObject());
        String message = "La entidad fue destruida.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }

    @Override
    public EntityMessenger<UsuarioModel> insertar(UsuarioModel usuario) {
        try {
            log.info("Insertando entidad Usuario: {}.",  usuario);
            UsuarioModel usuarioModel = usuarioRepository.save(usuario);
            String message = "La entidad Usuario con id: " + usuarioModel.getId() + " fue insertada correctamente.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(usuarioModel, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrio un error al intentar persistir la entidad. Excepcion: " + e + ".";
            log.error(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<UsuarioModel> actualizar(UsuarioModel usuario) {
        try {
            log.info("Actualizando entidad Usuario: {}.",  usuario);
            if (usuario.getId() != null) {
                EntityMessenger<UsuarioModel> existant = this.buscarPorId(usuario.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            UsuarioModel usuarioModel = usuarioRepository.save(usuario);
            String message = "La entidad Usuario con id: " + usuarioModel.getId() + "fue actualizada correctamente.";
            log.info(message);
            return new EntityMessenger<UsuarioModel>(usuarioModel, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrio un error al intentar persistir la entidad. Excepcion: " + e + ".";
            log.error(message);
            return new EntityMessenger<UsuarioModel>(null, null, message, 204);
        }
    }
}
