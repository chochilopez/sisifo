package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.TipoReclamoCreation;
import muni.eolida.sisifo.model.TipoReclamoModel;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.TipoReclamoMapper;
import muni.eolida.sisifo.repository.TipoReclamoDAO;
import muni.eolida.sisifo.service.TipoReclamoService;
import muni.eolida.sisifo.util.exception.CustomDataNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TipoReclamoServiceImpl implements TipoReclamoService {
    private final TipoReclamoDAO tipoReclamoDAO;
    private final TipoReclamoMapper tipoReclamoMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public List<TipoReclamoModel> buscarTodasPorAreaId(Long id) {
        log.info("Buscando todas las entidades TipoReclamo con id de Area: {}.", id);
        List<TipoReclamoModel> listado = tipoReclamoDAO.findAllByAreaIdAndEliminadaIsNull(id);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades TipoReclamo con id de Area: " + id + ".");
        return listado;
    }

    @Override
    public List<TipoReclamoModel> buscarTodasPorAreaIdConEliminadas(Long id) {
        log.info("Buscando todas las entidades TipoReclamo con id de Area: {}, incluidas las eliminadas.", id);
        List<TipoReclamoModel> listado = tipoReclamoDAO.findAllByAreaId(id);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades TipoReclamo con id de Area: " + id + ", incluidas las eliminadas.");
        return listado;
    }

    @Override
    public List<TipoReclamoModel> buscarTodasPorTipo(String tipo) {
        log.info("Buscando todas las entidades TipoReclamo con tipo: {}.", tipo);
        List<TipoReclamoModel> listado = tipoReclamoDAO.findAllByTipoIgnoreCaseContainingAndEliminadaIsNull(tipo);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades TipoReclamo con tipo: " + tipo + ".");
        return listado;
    }

    @Override
    public List<TipoReclamoModel> buscarTodasPorTipoConEliminadas(String tipo) {
        log.info("Buscando todas las entidades TipoReclamo con tipo: {}, incluidas las eliminadas.", tipo);
        List<TipoReclamoModel> listado = tipoReclamoDAO.findAllByTipoIgnoreCaseContaining(tipo);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades TipoReclamo con tipo: " + tipo + ", incluidas las eliminadas.");
        return listado;
    }

    @Override
    public TipoReclamoModel buscarPorId(Long id) {
        log.info("Buscando la entidad TipoReclamo con id: {}.", id);
        TipoReclamoModel tipoReclamoModel = tipoReclamoDAO.findByIdAndEliminadaIsNull(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad TipoReclamo con id: " + id + "."));
        log.info("Se encontro una entidad TipoReclamo con id: {}.", id);
        return tipoReclamoModel;
    }

    @Override
    public TipoReclamoModel buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad TipoReclamo con id: {}, incluidas las eliminadas.", id);
        TipoReclamoModel tipoReclamoModel = tipoReclamoDAO.findById(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad TipoReclamo con id: " + id +", incluidas las eliminadas."));
        log.info("Se encontro una entidad TipoReclamo con id: {}, incluidas las eliminadas.", id);
        return tipoReclamoModel;
    }

    @Override
    public List<TipoReclamoModel> buscarTodas() {
        log.info("Buscando todas las entidades TipoReclamo.");
        List<TipoReclamoModel> listado = tipoReclamoDAO.findAllByEliminadaIsNull();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades TipoReclamo.");
        return listado;
    }

    @Override
    public List<TipoReclamoModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades TipoReclamo, incluidas las eliminadas.");
        List<TipoReclamoModel> listado = tipoReclamoDAO.findAll();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades TipoReclamo, incluidas las eliminadas.");
        return listado;
    }

    @Override
    public Long contarTodas() {
        Long cantidad = tipoReclamoDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades TipoReclamo.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = tipoReclamoDAO.count();
        log.info("Existen {} entidades TipoReclamo, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public TipoReclamoModel guardar(TipoReclamoCreation creation) {
        log.info("Insertando la entidad TipoReclamo: {}.",  creation);
        TipoReclamoModel tipoReclamoModel = tipoReclamoDAO.save(tipoReclamoMapper.toEntity(creation));
        if (creation.getId() != null) {
            tipoReclamoModel.setCreada(Helper.getNow(""));
            tipoReclamoModel.setCreador(usuarioService.obtenerUsuario());
            log.info("Se persistio correctamente la nueva entidad.");
        } else {
            tipoReclamoModel.setModificada(Helper.getNow(""));
            tipoReclamoModel.setModificador(usuarioService.obtenerUsuario());
            log.info("Se persistio correctamente la entidad.");
        }
        return tipoReclamoDAO.save(tipoReclamoModel);
    }

    @Override
    public TipoReclamoModel eliminar(Long id) {
        log.info("Eliminando la entidad TipoReclamo con id: {}.", id);
        TipoReclamoModel objeto = this.buscarPorId(id);
        objeto.setEliminada(Helper.getNow(""));
        objeto.setEliminador(usuarioService.obtenerUsuario());
        log.info("La entidad TipoReclamo con id: {}, fue eliminada correctamente.", id);
        return tipoReclamoDAO.save(objeto);
    }

    @Override
    public TipoReclamoModel reciclar(Long id) {
        log.info("Reciclando la entidad TipoReclamo con id: {}.", id);
        TipoReclamoModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad TipoReclamo con id: {}, no se encuentra eliminada, por lo tanto no es necesario reciclarla.", id);
            return null;
        }
        objeto.setEliminada(null);
        objeto.setEliminador(null);
        log.info("La entidad TipoReclamo con id: {}, fue reciclada correctamente.", id);
        return tipoReclamoDAO.save(objeto);
    }

    @Override
    public Boolean destruir(Long id) {
        log.info("Destruyendo la entidad TipoReclamo con id: {}.", id);
        TipoReclamoModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad TipoReclamo con id: {}, no se encuentra eliminada, por lo tanto no puede ser destruida.", id);
            return false;
        }
        tipoReclamoDAO.delete(objeto);
        log.info("La entidad fue destruida.");
        return true;
    }
}
