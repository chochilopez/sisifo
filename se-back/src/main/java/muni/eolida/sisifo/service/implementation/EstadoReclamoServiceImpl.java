package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.model.EstadoReclamoModel;
import muni.eolida.sisifo.model.enums.TipoEstadoReclamoEnum;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.EstadoReclamoMapper;
import muni.eolida.sisifo.repository.EstadoReclamoDAO;
import muni.eolida.sisifo.service.EstadoReclamoService;
import muni.eolida.sisifo.util.exception.CustomDataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstadoReclamoServiceImpl implements EstadoReclamoService {
    private final EstadoReclamoDAO estadoReclamoDAO;
    private final EstadoReclamoMapper estadoReclamoMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public EstadoReclamoModel buscarPorEstadoReclamo(String estadoReclamo) {
        log.info("Buscando la entidad EstadoReclamo con nombre: {}.", estadoReclamo);
        EstadoReclamoModel estadoReclamoModel = estadoReclamoDAO.findByEstadoAndEliminadaIsNull(TipoEstadoReclamoEnum.valueOf(estadoReclamo)).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad EstadoReclamo con nombre: " + estadoReclamo + "."));
        log.info("Se encontro una entidad EstadoReclamo con nombre: " + estadoReclamo + ".");
        return estadoReclamoModel;
    }

    @Override
    public EstadoReclamoModel buscarPorEstadoReclamoConEliminadas(String estadoReclamo) {
        log.info("Buscando la entidad EstadoReclamo con nombre: {}, incluidas las eliminadas.", estadoReclamo);
        EstadoReclamoModel estadoReclamoModel = estadoReclamoDAO.findByEstado(TipoEstadoReclamoEnum.valueOf(estadoReclamo)).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad EstadoReclamo con nombre: " + estadoReclamo + ", incluidas las eliminadas."));
        log.info("Se encontro una entidad EstadoReclamo con nombre: " + estadoReclamo + ", incluidas las eliminadas.");
        return estadoReclamoModel;
    }

    @Override
    public EstadoReclamoModel buscarPorId(Long id) {
        log.info("Buscando la entidad EstadoReclamo con id: {}.", id);
        EstadoReclamoModel estadoReclamoModel = estadoReclamoDAO.findByIdAndEliminadaIsNull(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad EstadoReclamo con id: " + id + "."));
        String mensaje = "Se encontro una entidad EstadoReclamo.";
        log.info(mensaje);
        return estadoReclamoModel;
    }

    @Override
    public EstadoReclamoModel buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad EstadoReclamo con id: {}, incluidas las eliminadas.", id);
        EstadoReclamoModel estadoReclamoModel = estadoReclamoDAO.findById(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad EstadoReclamo con id: " + id +", incluidas las eliminadas."));
        log.info("Se encontro una entidad EstadoReclamo con id: " + id + ".");
        return estadoReclamoModel;
    }

    @Override
    public List<EstadoReclamoModel> buscarTodas() {
        log.info("Buscando todas las entidades EstadoReclamo.");
        List<EstadoReclamoModel> listado = estadoReclamoDAO.findAllByEliminadaIsNull();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades EstadoReclamo.");
        return listado;
    }

    @Override
    public List<EstadoReclamoModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades EstadoReclamo, incluidas las eliminadas.");
        List<EstadoReclamoModel> listado = estadoReclamoDAO.findAll();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades EstadoReclamo, incluidas las eliminadas.");
        return listado;
    }

    @Override
    public Long contarTodas() {
        Long cantidad = estadoReclamoDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades EstadoReclamo.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = estadoReclamoDAO.count();
        log.info("Existen {} entidades EstadoReclamo, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public EstadoReclamoModel guardar(EstadoReclamoCreation creation) {
        log.info("Insertando la entidad EstadoReclamo: {}.",  creation);
        EstadoReclamoModel estadoReclamoModel = estadoReclamoDAO.save(estadoReclamoMapper.toEntity(creation));
        if (creation.getId() != null) {
            estadoReclamoModel.setCreada(Helper.getNow(""));
            estadoReclamoModel.setCreador(usuarioService.obtenerUsuario());
            log.info("Se persistion correctamente la nueva entidad.");
        } else {
            estadoReclamoModel.setModificada(Helper.getNow(""));
            estadoReclamoModel.setModificador(usuarioService.obtenerUsuario());
            log.info("Se persistion correctamente la entidad.");
        }
        return estadoReclamoDAO.save(estadoReclamoModel);
    }

    @Override
    public EstadoReclamoModel reciclar(Long id) {
        log.info("Reciclando la entidad EstadoReclamo con id: {}.", id);
        EstadoReclamoModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad EstadoReclamo con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.");
            return null;
        }
        objeto.setEliminada(null);
        objeto.setEliminador(null);
        log.info("La entidad EstadoReclamo con id: " + id + ", fue reciclada correctamente.");
        return estadoReclamoDAO.save(objeto);
    }

    @Override
    public EstadoReclamoModel eliminar(Long id) {
        log.info("Eliminando la entidad EstadoReclamo con id: {}.", id);
        EstadoReclamoModel objeto = this.buscarPorId(id);
        objeto.setEliminada(Helper.getNow(""));
        objeto.setEliminador(usuarioService.obtenerUsuario());
        log.info("La entidad EstadoReclamo con id: " + id + ", fue eliminada correctamente.");
        return estadoReclamoDAO.save(objeto);
    }

    @Override
    public Boolean destruir(Long id) {
        log.info("Destruyendo la entidad EstadoReclamo con id: {}.", id);
        EstadoReclamoModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad EstadoReclamo con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.");
            return false;
        }
        estadoReclamoDAO.delete(objeto);
        log.info("La entidad fue destruida.");
        return true;
    }
}
