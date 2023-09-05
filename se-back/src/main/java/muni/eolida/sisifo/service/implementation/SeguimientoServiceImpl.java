package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.EstadoReclamoMapper;
import muni.eolida.sisifo.mapper.creation.EstadoReclamoCreation;
import muni.eolida.sisifo.mapper.creation.SeguimientoCreation;
import muni.eolida.sisifo.model.SeguimientoModel;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.SeguimientoMapper;
import muni.eolida.sisifo.repository.SeguimientoDAO;
import muni.eolida.sisifo.service.SeguimientoService;
import muni.eolida.sisifo.util.exception.CustomDataNotFoundException;
import muni.eolida.sisifo.util.exception.CustomObjectNotDeletedException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeguimientoServiceImpl implements SeguimientoService {
    private final SeguimientoDAO seguimientoDAO;
    private final SeguimientoMapper seguimientoMapper;
    private final UsuarioServiceImpl usuarioService;
    private final EstadoReclamoMapper estadoReclamoMapper;

    @Override
    public SeguimientoModel agregarEstadoReclamo(Long idSeguimiento, EstadoReclamoCreation estadoReclamoCreation) {
        log.info("Agregando al Seguimiento con id: {}, el estado {}, y la descripcion.", idSeguimiento, estadoReclamoCreation.getEstado(), estadoReclamoCreation.getDescripcion());
        SeguimientoModel seguimiento = seguimientoDAO.findByIdAndEliminadaIsNull(idSeguimiento).orElseThrow(() -> new CustomDataNotFoundException("No se encontro la entidad Seguimiento con id: " + idSeguimiento + "."));
        seguimiento.getEstados().add(estadoReclamoMapper.toEntity(estadoReclamoCreation));
        log.info("Se agrego correctamente al Seguimiento con id: {}, el estado {}, y la descripcion", idSeguimiento, estadoReclamoCreation.getEstado(), estadoReclamoCreation.getDescripcion());
        return seguimientoDAO.save(seguimiento);
    }

    @Override
    public List<SeguimientoModel> buscarTodasPorCreadaEntreFechas(String inicio, String fin) {
        log.info("Buscando todas la entidades Seguimiento con fecha de creacion entre {} y {}.", inicio, fin);
        List<SeguimientoModel> listado = seguimientoDAO.findAllByCreadaBetweenInicioAndFinAndEliminadaIsNull(
                Helper.stringToLocalDateTime(inicio, ""),
                Helper.stringToLocalDateTime(fin, "")
        );
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Seguimiento con fecha de creacion entre " + inicio + " y " + fin + ".");
        return listado;
    }

    @Override
    public List<SeguimientoModel> buscarTodasPorCreadaEntreFechasConEliminadas(String inicio, String fin) {
        log.info("Buscando todas la entidades Seguimiento con fecha de creacion entre {} y {}, incluidas las eliminadas.", inicio, fin);
        List<SeguimientoModel> listado = seguimientoDAO.findAllByCreadaBetweenInicioAndFin(
                Helper.stringToLocalDateTime(inicio, ""),
                Helper.stringToLocalDateTime(fin, "")
        );
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Seguimiento con fecha de creacion entre " + inicio + " y " + fin + ", incluidas las eliminadas.");
        return listado;
    }

    @Override
    public SeguimientoModel buscarPorId(Long id) {
        log.info("Buscando la entidad Seguimiento con id: {}.", id);
        SeguimientoModel seguimientoModel = seguimientoDAO.findByIdAndEliminadaIsNull(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Seguimiento con id: " + id + "."));
        log.info("Se encontro una entidad Seguimiento con id: {}.", id);
        return seguimientoModel;
    }

    @Override
    public SeguimientoModel buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Seguimiento con id: {}, incluidas las eliminadas.", id);
        SeguimientoModel seguimientoModel = seguimientoDAO.findById(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Seguimiento con id: " + id +", incluidas las eliminadas."));
        log.info("Se encontro una entidad Seguimiento con id: {}, incluidas las eliminadas.", id);
        return seguimientoModel;
    }

    @Override
    public List<SeguimientoModel> buscarTodas() {
        log.info("Buscando todas las entidades Seguimiento.");
        List<SeguimientoModel> listado = seguimientoDAO.findAllByEliminadaIsNull();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Seguimiento.");
        return listado;
    }

    @Override
    public List<SeguimientoModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Seguimiento, incluidas las eliminadas.");
        List<SeguimientoModel> listado = seguimientoDAO.findAll();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Seguimiento, incluidas las eliminadas.");
        return listado;
    }

    @Override
    public Long contarTodas() {
        Long cantidad = seguimientoDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Seguimiento.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = seguimientoDAO.count();
        log.info("Existen {} entidades Seguimiento, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public SeguimientoModel guardar(SeguimientoCreation creation) {
        log.info("Insertando la entidad Seguimiento: {}.",  creation);
        SeguimientoModel seguimientoModel = seguimientoDAO.save(seguimientoMapper.toEntity(creation));
        if (creation.getId() == null) {
            seguimientoModel.setCreada(Helper.getNow(""));
            seguimientoModel.setCreador(usuarioService.obtenerUsuario());
            log.info("Se persistio correctamente la nueva entidad.");
        } else {
            seguimientoModel.setModificada(Helper.getNow(""));
            seguimientoModel.setModificador(usuarioService.obtenerUsuario());
            log.info("Se persistio correctamente la entidad.");
        }
        return seguimientoDAO.save(seguimientoModel);
    }

    @Override
    public SeguimientoModel eliminar(Long id) {
        log.info("Eliminando la entidad Seguimiento con id: {}.", id);
        SeguimientoModel objeto = this.buscarPorId(id);
        objeto.setEliminada(Helper.getNow(""));
        objeto.setEliminador(usuarioService.obtenerUsuario());
        log.info("La entidad Seguimiento con id: {}, fue eliminada correctamente.", id);
        return seguimientoDAO.save(objeto);
    }

    @Override
    public SeguimientoModel reciclar(Long id) {
        log.info("Reciclando la entidad Seguimiento con id: {}.", id);
        SeguimientoModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Seguimiento con id: {}, no se encuentra eliminada, por lo tanto no es necesario reciclarla.", id);
            throw new CustomObjectNotDeletedException("No se puede reciclar la entidad.");
        }
        objeto.setEliminada(null);
        objeto.setEliminador(null);
        log.info("La entidad Seguimiento con id: {}, fue reciclada correctamente.", id);
        return seguimientoDAO.save(objeto);
    }

    @Override
    public void destruir(Long id) {
        log.info("Destruyendo la entidad Seguimiento con id: {}.", id);
        SeguimientoModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Seguimiento con id: {}, no se encuentra eliminada, por lo tanto no puede ser destruida.", id);
            throw new CustomObjectNotDeletedException("No se puede destruir la entidad.");
        }
        seguimientoDAO.delete(objeto);
        log.info("La entidad fue destruida.");
    }
}
