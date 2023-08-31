package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.CalleCreation;
import muni.eolida.sisifo.model.CalleModel;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.CalleMapper;
import muni.eolida.sisifo.repository.CalleDAO;
import muni.eolida.sisifo.service.CalleService;
import muni.eolida.sisifo.util.exception.CustomDataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalleServiceImpl implements CalleService {
    private final CalleDAO calleDAO;
    private final CalleMapper calleMapper;
    private final UsuarioServiceImpl usuarioService;

    @Override
    public List<CalleModel> buscarTodasPorCalle(String calle) {
        log.info("Buscando todas las entidades Calle con nombre: {}.", calle);
        List<CalleModel> listado = calleDAO.findAllByCalleIgnoreCaseContainingAndEliminadaIsNull(calle);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Calle con nombre " + calle + ".");
        return listado;
    }

    @Override
    public List<CalleModel> buscarTodasPorCalleConEliminadas(String calle) {
        log.info("Buscando todas las entidades Calle con nombre: {}, incluidas las eliminadas.", calle);
        List<CalleModel> listado = calleDAO.findAllByCalleIgnoreCaseContaining(calle);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Calle con nombre: " + calle + ", incluidas las eliminadas.");
        return listado;
    }

    @Override
    public CalleModel buscarPorId(Long id) {
        log.info("Buscando la entidad Calle con id: {}.", id);
        CalleModel calleModel = calleDAO.findByIdAndEliminadaIsNull(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Calle con id: " + id + "."));
        String mensaje = "Se encontro una entidad Calle.";
        log.info(mensaje);
        return calleModel;
    }

    @Override
    public CalleModel buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Calle con id: {}, incluidas las eliminadas.", id);
        CalleModel calleModel = calleDAO.findById(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Calle con id: " + id +", incluidas las eliminadas."));
        log.info("Se encontro una entidad Calle con id: " + id + ".");
        return calleModel;
    }

    @Override
    public List<CalleModel> buscarTodas() {
        log.info("Buscando todas las entidades Calle.");
        List<CalleModel> listado = calleDAO.findAllByEliminadaIsNull();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Calle.");
        return listado;
    }

    @Override
    public List<CalleModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Calle, incluidas las eliminadas.");
        List<CalleModel> listado = calleDAO.findAll();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Calle, incluidas las eliminadas.");
        return listado;
    }

    @Override
    public Long contarTodas() {
        Long cantidad = calleDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Calle.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = calleDAO.count();
        log.info("Existen {} entidades Calle, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public CalleModel guardar(CalleCreation creation) {
        log.info("Insertando la entidad Calle: {}.",  creation);
        CalleModel calleModel = calleDAO.save(calleMapper.toEntity(creation));
        if (creation.getId() != null) {
            calleModel.setCreada(Helper.getNow(""));
            calleModel.setCreador(usuarioService.obtenerUsuario());
            log.info("Se persistion correctamente la nueva entidad.");
        } else {
            calleModel.setModificada(Helper.getNow(""));
            calleModel.setModificador(usuarioService.obtenerUsuario());
            log.info("Se persistion correctamente la entidad.");
        }
        return calleDAO.save(calleModel);
    }

    @Override
    public CalleModel eliminar(Long id) {
        log.info("Eliminando la entidad Calle con id: {}.", id);
        CalleModel objeto = this.buscarPorId(id);
        objeto.setEliminada(Helper.getNow(""));
        objeto.setEliminador(usuarioService.obtenerUsuario());
        log.info("La entidad Calle con id: " + id + ", fue eliminada correctamente.");
        return calleDAO.save(objeto);
    }

    @Override
    public CalleModel reciclar(Long id) {
        log.info("Reciclando la entidad Calle con id: {}.", id);
        CalleModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Calle con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.");
            return null;
        }
        objeto.setEliminada(null);
        objeto.setEliminador(null);
        log.info("La entidad Calle con id: " + id + ", fue reciclada correctamente.");
        return calleDAO.save(objeto);
    }

    @Override
    public Boolean destruir(Long id) {
        log.info("Destruyendo la entidad Calle con id: {}.", id);
        CalleModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Calle con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.");
            return false;
        }
        calleDAO.delete(objeto);
        log.info("La entidad fue destruida.");
        return true;
    }
}
