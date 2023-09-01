package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.AreaCreation;
import muni.eolida.sisifo.model.AreaModel;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.AreaMapper;
import muni.eolida.sisifo.repository.AreaDAO;
import muni.eolida.sisifo.service.AreaService;
import muni.eolida.sisifo.util.exception.CustomDataNotFoundException;
import muni.eolida.sisifo.util.exception.CustomObjectNotDeletedException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaDAO areaDAO;
    private final AreaMapper areaMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public List<AreaModel> buscarTodasPorArea(String area) {
        log.info("Buscando todas las entidades Area con nombre: {}.", area);
        List<AreaModel> listado = areaDAO.findAllByAreaIgnoreCaseContainingAndEliminadaIsNull(area);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Area con nombre: " + area + ".");
        return listado;
    }

    @Override
    public List<AreaModel> buscarTodasPorAreaConEliminadas(String area) {
        log.info("Buscando todas las entidades Area con nombre: {}, incluidas las eliminadas.", area);
        List<AreaModel> listado = areaDAO.findAllByAreaIgnoreCaseContaining(area);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Area con nombre: " + area + ", incluidas las eliminadas.");
        return listado;
    }

    @Override
    public AreaModel buscarPorId(Long id) {
        log.info("Buscando la entidad Area con id: {}.", id);
        AreaModel areaModel = areaDAO.findByIdAndEliminadaIsNull(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Area con id: " + id + "."));
        log.info("Se encontro una entidad Area con id: {}.", id);
        return areaModel;
    }

    @Override
    public AreaModel buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Area con id: {}, incluidas las eliminadas.", id);
        AreaModel areaModel = areaDAO.findById(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Area con id: " + id +", incluidas las eliminadas."));
        log.info("Se encontro una entidad Area con id: {}, incluidas las eliminadas.", id);
        return areaModel;
    }

    @Override
    public List<AreaModel> buscarTodas() {
        log.info("Buscando todas las entidades Area.");
        List<AreaModel> listado = areaDAO.findAllByEliminadaIsNull();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Area.");
        return listado;
    }

    @Override
    public List<AreaModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Area, incluidas las eliminadas.");
        List<AreaModel> listado = areaDAO.findAll();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Area, incluidas las eliminadas.");
        return listado;
    }

    @Override
    public Long contarTodas() {
        Long cantidad = areaDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Area.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = areaDAO.count();
        log.info("Existen {} entidades Area, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public AreaModel guardar(AreaCreation creation) {
        log.info("Insertando la entidad Area: {}.",  creation);
        AreaModel areaModel = areaDAO.save(areaMapper.toEntity(creation));
        if (creation.getId() != null) {
            areaModel.setCreada(Helper.getNow(""));
            areaModel.setCreador(usuarioService.obtenerUsuario());
            log.info("Se persistio correctamente la nueva entidad.");
        } else {
            areaModel.setModificada(Helper.getNow(""));
            areaModel.setModificador(usuarioService.obtenerUsuario());
            log.info("Se persistio correctamente la entidad.");
        }
        return areaDAO.save(areaModel);
    }

    @Override
    public AreaModel eliminar(Long id) {
        log.info("Eliminando la entidad Area con id: {}.", id);
        AreaModel objeto = this.buscarPorId(id);
        objeto.setEliminada(Helper.getNow(""));
        objeto.setEliminador(usuarioService.obtenerUsuario());
        log.info("La entidad Area con id: {}, fue eliminada correctamente.", id);
        return areaDAO.save(objeto);
    }

    @Override
    public AreaModel reciclar(Long id) {
        log.info("Reciclando la entidad Area con id: {}.", id);
        AreaModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Area con id: {}, no se encuentra eliminada, por lo tanto no es necesario reciclarla.", id);
            throw new CustomObjectNotDeletedException("No se puede reciclar la entidad.");
        }
        objeto.setEliminada(null);
        objeto.setEliminador(null);
        log.info("La entidad Area con id: {}, fue reciclada correctamente.", id);
        return areaDAO.save(objeto);
    }

    @Override
    public void destruir(Long id) {
        log.info("Destruyendo la entidad Area con id: {}.", id);
        AreaModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Area con id: {}, no se encuentra eliminada, por lo tanto no puede ser destruida.", id);
            throw new CustomObjectNotDeletedException("No se puede destruir la entidad.");
        }
        areaDAO.delete(objeto);
        log.info("La entidad fue destruida.");
    }
}
