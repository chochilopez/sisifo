package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.BarrioCreation;
import muni.eolida.sisifo.model.BarrioModel;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.BarrioMapper;
import muni.eolida.sisifo.repository.BarrioDAO;
import muni.eolida.sisifo.service.BarrioService;
import muni.eolida.sisifo.util.exception.CustomDataNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BarrioServiceImpl implements BarrioService {
    private final BarrioDAO barrioDAO;
    private final BarrioMapper barrioMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public List<BarrioModel> buscarTodasPorBarrio(String barrio) {
        log.info("Buscando todas las entidades Barrio con nombre: {}.", barrio);
        List<BarrioModel> listado = barrioDAO.findAllByBarrioIgnoreCaseContainingAndEliminadaIsNull(barrio);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Barrio con nombre: " + barrio + ".");
        return listado;
    }

    @Override
    public List<BarrioModel> buscarTodasPorBarrioConEliminadas(String barrio) {
        log.info("Buscando todas las entidades Barrio con nombre: {}, incluidas las eliminadas.", barrio);
        List<BarrioModel> listado = barrioDAO.findAllByBarrioIgnoreCaseContaining(barrio);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Barrio con nombre: " + barrio + ", incluidas las eliminadas.");
        return listado;
    }

    @Override
    public BarrioModel buscarPorId(Long id) {
        log.info("Buscando la entidad Barrio con id: {}.", id);
        BarrioModel barrioModel = barrioDAO.findByIdAndEliminadaIsNull(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Barrio con id: " + id + "."));
        String mensaje = "Se encontro una entidad Barrio.";
        log.info(mensaje);
        return barrioModel;
    }

    @Override
    public BarrioModel buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Barrio con id: {}, incluidas las eliminadas.", id);
        BarrioModel barrioModel = barrioDAO.findById(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Barrio con id: " + id +", incluidas las eliminadas."));
        log.info("Se encontro una entidad Barrio con id: " + id + ".");
        return barrioModel;
    }

    @Override
    public List<BarrioModel> buscarTodas() {
        log.info("Buscando todas las entidades Barrio.");
        List<BarrioModel> listado = barrioDAO.findAllByEliminadaIsNull();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Barrio.");
        return listado;
    }

    @Override
    public List<BarrioModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Barrio, incluidas las eliminadas.");
        List<BarrioModel> listado = barrioDAO.findAll();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Barrio, incluidas las eliminadas.");
        return listado;
    }

    @Override
    public Long contarTodas() {
        Long cantidad = barrioDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Barrio.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = barrioDAO.count();
        log.info("Existen {} entidades Barrio, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public BarrioModel guardar(BarrioCreation creation) {
        log.info("Insertando la entidad Barrio: {}.",  creation);
        BarrioModel barrioModel = barrioDAO.save(barrioMapper.toEntity(creation));
        if (creation.getId() != null) {
            barrioModel.setCreada(Helper.getNow(""));
            barrioModel.setCreador(usuarioService.obtenerUsuario());
            log.info("Se persistion correctamente la nueva entidad.");
        } else {
            barrioModel.setModificada(Helper.getNow(""));
            barrioModel.setModificador(usuarioService.obtenerUsuario());
            log.info("Se persistion correctamente la entidad.");
        }
        return barrioDAO.save(barrioModel);
    }

    @Override
    public BarrioModel eliminar(Long id) {
        log.info("Eliminando la entidad Barrio con id: {}.", id);
        BarrioModel objeto = this.buscarPorId(id);
        objeto.setEliminada(Helper.getNow(""));
        objeto.setEliminador(usuarioService.obtenerUsuario());
        log.info("La entidad Barrio con id: " + id + ", fue eliminada correctamente.");
        return barrioDAO.save(objeto);
    }

    @Override
    public BarrioModel reciclar(Long id) {
        log.info("Reciclando la entidad Barrio con id: {}.", id);
        BarrioModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Barrio con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.");
            return null;
        }
        objeto.setEliminada(null);
        objeto.setEliminador(null);
        log.info("La entidad Barrio con id: " + id + ", fue reciclada correctamente.");
        return barrioDAO.save(objeto);
    }

    @Override
    public Boolean destruir(Long id) {
        log.info("Destruyendo la entidad Barrio con id: {}.", id);
        BarrioModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Barrio con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.");
            return false;
        }
        barrioDAO.delete(objeto);
        log.info("La entidad fue destruida.");
        return true;
    }
}
