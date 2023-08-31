package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.UsuarioMapper;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.repository.UsuarioDAO;
import muni.eolida.sisifo.service.UsuarioService;
import muni.eolida.sisifo.util.exception.CustomDataNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioDAO usuarioDAO;
    private final UsuarioMapper usuarioMapper;
    private final RolServiceImpl rolService;

    @Override
    public UsuarioModel buscarPorNombreDeUsuarioHabilitado(String nombreUsuario) {
        log.info("Buscando la entidad Usuario con nombre de usuario: {}, y habilitada.", nombreUsuario);
        UsuarioModel usuarioModel = usuarioDAO.findByUsernameContainingIgnoreCaseAndHabilitadaIsTrueAndEliminadaIsNull(nombreUsuario).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad con Usuario con nombre de usuario: " + nombreUsuario + ", y habilitada."));
        log.info("Se encontro una entidad usuario habilitada con nombre de usuario: " + nombreUsuario + ".");
        return usuarioModel;
    }

    @Override
    public Boolean existeUsuarioPorNombreDeUsuario(String username) {
        return usuarioDAO.existsByUsernameContainingIgnoreCase(username);
    }

    @Override
    public UsuarioModel buscarPorNombreDeUsuario(String nombreUsuario) {
        log.info("Buscando la entidad Usuario con nombre de usuario: {}.", nombreUsuario);
        UsuarioModel usuarioModel = usuarioDAO.findByUsernameContainingIgnoreCaseAndEliminadaIsNull(nombreUsuario).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad con nombre de usuario: " + nombreUsuario + "."));
        String mensaje = "Se encontro una entidad Usuario con nombre de usuario: " + nombreUsuario + ".";
        log.info(mensaje);
        return usuarioModel;
    }

    @Override
    public UsuarioModel buscarPorNombreDeUsuarioConEliminadas(String nombreUsuario) {
        log.info("Buscando la entidad Usuario con nombre de usuario: {}, incluidas las eliminadas.", nombreUsuario);
        UsuarioModel usuarioModel = usuarioDAO.findByUsernameContainingIgnoreCase(nombreUsuario).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad con nombre de usuario: " + nombreUsuario + ", incluidas las eliminadas."));
        String mensaje = "Se encontro una entidad Usuario con nombre de usuario: " + nombreUsuario + ", incluidas las eliminadas.";
        log.info(mensaje);
        return usuarioModel;
    }

    @Override
    public UsuarioModel darRol(UsuarioModel usuario, String rolEnum) {
        log.info("Agregando el rol {} al usuario {}.", rolEnum, usuario.getUsername());
        RolModel rol =  rolService.buscarPorRol(rolEnum);
        if (usuario.getRoles().isEmpty()) {
            HashSet<RolModel> roles = new HashSet<>();
            usuario.setRoles(roles);
        }
        usuario.getRoles().add(rol);
        log.info("El rol " + rolEnum + " fue añadido correctamente al usuario " + usuario.getUsername() + ".");
        return usuarioDAO.save(usuario);
    }

    @Override
    public UsuarioModel obtenerUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            return this.buscarPorNombreDeUsuario(authentication.getName());
        else
            return this.buscarPorNombreDeUsuario("admin@municrespo.gob.ar");
    }

    @Override
    public UsuarioModel buscarPorId(Long id) {
        log.info("Buscando la entidad Usuario con id: {}.", id);
        UsuarioModel usuarioModel = usuarioDAO.findByIdAndEliminadaIsNull(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad con id " + id + "."));
        String mensaje = "Se encontro una entidad Usuario.";
        log.info(mensaje);
        return usuarioModel;
    }

    @Override
    public UsuarioModel buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Usuario con id: {}, incluidas las eliminadas.", id);
        UsuarioModel usuarioModel = usuarioDAO.findByIdAndEliminadaIsNull(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad con id: " + id +", incluidas las eliminadas."));
        log.info("Se encontro una entidad Usuario con id: " + id + ".");
        return usuarioModel;
    }

    @Override
    public List<UsuarioModel> buscarTodas() {
        log.info("Buscando todas las entidades Usuario.");
        List<UsuarioModel> listado = usuarioDAO.findAllByEliminadaIsNull();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Usuario.");
        return listado;
    }

    @Override
    public List<UsuarioModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Usuario, incluidas las eliminadas.");
        List<UsuarioModel> listado = usuarioDAO.findAllByEliminadaIsNull();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Usuario, incluidas las eliminadas.");
        return listado;
    }

    @Override
    public Long contarTodas() {
        Long cantidad = usuarioDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Usuario.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = usuarioDAO.count();
        log.info("Existen {} entidades Usuario, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public UsuarioModel guardar(UsuarioCreation creation) {
        log.info("Insertando la entidad Usuario: {}.",  creation);
        UsuarioModel usuarioModel = usuarioDAO.save(usuarioMapper.toEntity(creation));
        if (creation.getId() != null) {
            usuarioModel.setCreada(Helper.getNow(""));
            usuarioModel.setCreador(this.obtenerUsuario());
            log.info("Se persistion correctamente la nueva entidad.");
        } else {
            usuarioModel.setModificada(Helper.getNow(""));
            usuarioModel.setModificador(this.obtenerUsuario());
            log.info("Se persistion correctamente la entidad.");
        }
        return usuarioDAO.save(usuarioModel);
    }

    @Override
    public UsuarioModel reciclar(Long id) {
        log.info("Reciclando la entidad Usuario con id: {}.", id);
        UsuarioModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Usuario con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.");
            return null;
        }
        objeto.setEliminada(null);
        objeto.setEliminador(null);
        log.info("La entidad Usuario con id: " + id + ", fue reciclada correctamente.");
        return usuarioDAO.save(objeto);
    }

    @Override
    public UsuarioModel eliminar(Long id) {
        log.info("Eliminando la entidad Usuario con id: {}.", id);
        UsuarioModel objeto = this.buscarPorId(id);
        objeto.setEliminada(Helper.getNow(""));
        objeto.setEliminador(this.obtenerUsuario());
        log.info("La entidad Usuario con id: " + id + ", fue eliminada correctamente.");
        return usuarioDAO.save(objeto);
    }

    @Override
    public Boolean destruir(Long id) {
        log.info("Destruyendo la entidad Usuario con id: {}.", id);
        UsuarioModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Usuario con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.");
            return false;
        }
        usuarioDAO.delete(objeto);
        log.info("La entidad fue destruida y el usuario " + objeto + " fue eliminado correctamente.");
        return true;
    }
}
