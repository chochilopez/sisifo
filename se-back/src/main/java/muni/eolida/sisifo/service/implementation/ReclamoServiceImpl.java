package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.model.ReclamoModel;
import muni.eolida.sisifo.util.Helper;
import muni.eolida.sisifo.mapper.ReclamoMapper;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.repository.ReclamoDAO;
import muni.eolida.sisifo.service.ReclamoService;
import muni.eolida.sisifo.util.exception.CustomDataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReclamoServiceImpl implements ReclamoService {
    private final ReclamoDAO reclamoDAO;
    private final ReclamoMapper reclamoMapper;
    private final UsuarioServiceImpl usuarioService;


    @Override
    public List<ReclamoModel> buscarMisReclamos() {
        UsuarioModel usuario = usuarioService.obtenerUsuario();
        log.info("Obteniendo los reclamos del usuario: {}.", usuario.getUsername());
        return this.buscarTodasPorCreadorId(usuario.getId());
    }

    @Override
    public List<ReclamoModel> buscarTodasPorCreadorId(Long id) {
        log.info("Buscando todas las entidades Reclamo con id de Creador: {}.", id);
        List<ReclamoModel> listado = reclamoDAO.findAllByCreadorIdAndEliminadaIsNull(id);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron Reclamo con id de Creador: " + id + ".");
        return listado;
    }

    @Override
    public List<ReclamoModel> buscarTodasPorCreadorIdConEliminadas(Long id) {
        log.info("Buscando todas las entidades Reclamo con id de Creador: {}, incluidas las eliminadas.", id);
        List<ReclamoModel> listado = reclamoDAO.findAllByCreadorId(id);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron Reclamo con id de Creador: " + id + ", incluidas las eliminadas.");
        return listado;
    }

    @Override
    public List<ReclamoModel> buscarTodasPorTipoReclamoId(Long id) {
        log.info("Buscando todas las entidades Reclamo con id de TipoReclamo: {}.", id);
        List<ReclamoModel> listado = reclamoDAO.findAllByTipoReclamoIdAndEliminadaIsNull(id);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron Reclamo con id de TipoReclamo: " + id + ".");
        return listado;
    }

    @Override
    public List<ReclamoModel> buscarTodasPorTipoReclamoIdConEliminadas(Long id) {
        log.info("Buscando todas las entidades Reclamo con id de TipoReclamo: {}, incluidas las eliminadas.", id);
        List<ReclamoModel> listado = reclamoDAO.findAllByTipoReclamoId(id);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron Reclamo con id de TipoReclamo: " + id + ", incluidas las eliminadas.");
        return listado;
    }

    @Override
    public List<ReclamoModel> buscarTodasPorBarrioId(Long id) {
        log.info("Buscando todas las entidades Reclamo con id de Barrio: {}.", id);
        List<ReclamoModel> listado = reclamoDAO.findAllByBarrioIdAndEliminadaIsNull(id);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron Reclamo con id de Barrio: " + id + ".");
        return listado;
    }

    @Override
    public List<ReclamoModel> buscarTodasPorBarrioIdConEliminadas(Long id) {
        log.info("Buscando todas las entidades Reclamo con id de Barrio: {}, incluidas las eliminadas.", id);
        List<ReclamoModel> listado = reclamoDAO.findAllByBarrioId(id);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron Reclamo con id de Barrio: " + id + ", incluidas las eliminadas.");
        return listado;
    }

    @Override
    public List<ReclamoModel> buscarTodasPorCalleId(Long id) {
        log.info("Buscando todas las entidades Reclamo con id de Calle: {}.", id);
        List<ReclamoModel> listado = reclamoDAO.findAllByCalleIdAndEliminadaIsNull(id);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron Reclamo con id de Calle: " + id + ".");
        return listado;
    }

    @Override
    public List<ReclamoModel> buscarTodasPorCalleIdConEliminadas(Long id) {
        log.info("Buscando todas las entidades Reclamo con id de Calle: {}, incluidas las eliminadas.", id);
        List<ReclamoModel> listado = reclamoDAO.findAllByCalleId(id);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron Reclamo con id de Calle: " + id + ", incluidas las eliminadas.");
        return listado;
    }

    @Override
    public List<ReclamoModel> buscarTodasPorDescripcion(String descripcion) {
        log.info("Buscando todas las entidades Reclamo por descripcion: {}.", descripcion);
        List<ReclamoModel> listado = reclamoDAO.findAllByDescripcionContainingIgnoreCaseAndEliminadaIsNull(descripcion);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron Reclamo por descripcion: " + descripcion + ".");
        return listado;
    }

    @Override
    public List<ReclamoModel> buscarTodasPorDescripcionConEliminadas(String descripcion) {
        log.info("Buscando todas las entidades Reclamo por descripcion: {}, incluidas las eliminadas.", descripcion);
        List<ReclamoModel> listado = reclamoDAO.findAllByDescripcionContainingIgnoreCase(descripcion);
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron Reclamo por descripcion: " + descripcion + ", incluidas las eliminadas.");
        return listado;
    }

    @Override
    public List<ReclamoModel> buscarTodasPorCreadaEntreFechas(String inicio, String fin) {
        log.info("Buscando todas la entidades Reclamo con fecha de creacion entre {} y {}.", inicio, fin);
        List<ReclamoModel> listado = reclamoDAO.findAllByCreadaBetweenInicioAndFinAndEliminadaIsNull(
                Helper.stringToLocalDateTime(inicio, null),
                Helper.stringToLocalDateTime(fin, null)
        );
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Reclamo con fecha de creacion entre " + inicio + " y " + fin + ".");
        return listado;
    }

    @Override
    public List<ReclamoModel> buscarTodasPorCreadaEntreFechasConEliminadas(String inicio, String fin) {
        log.info("Buscando todas la entidades Reclamo con fecha de creacion entre {} y {}, incluidas las eliminadas.", inicio, fin);
        List<ReclamoModel> listado = reclamoDAO.findAllByCreadaBetweenInicioAndFin(
                Helper.stringToLocalDateTime(inicio, null),
                Helper.stringToLocalDateTime(fin, null)
        );
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Reclamo con fecha de creacion entre " + inicio + " y " + fin + ", incluidas las eliminadas.");
        return listado;
    }

    @Override
    public ReclamoModel buscarPorId(Long id) {
        log.info("Buscando la entidad Reclamo con id: {}.", id);
        ReclamoModel reclamoModel = reclamoDAO.findByIdAndEliminadaIsNull(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Reclamo con id: " + id + "."));
        log.info("Se encontro una entidad Reclamo con id: {}.", id);
        return reclamoModel;
    }

    @Override
    public ReclamoModel buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Reclamo con id: {}, incluidas las eliminadas.", id);
        ReclamoModel reclamoModel = reclamoDAO.findById(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Reclamo con id: " + id +", incluidas las eliminadas."));
        log.info("Se encontro una entidad Reclamo con id: {}, incluidas las eliminadas.", id);
        return reclamoModel;
    }

    @Override
    public List<ReclamoModel> buscarTodas() {
        log.info("Buscando todas las entidades Reclamo.");
        List<ReclamoModel> listado = reclamoDAO.findAllByEliminadaIsNull();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Reclamo.");
        return listado;
    }

    @Override
    public List<ReclamoModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Reclamo, incluidas las eliminadas.");
        List<ReclamoModel> listado = reclamoDAO.findAll();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Reclamo, incluidas las eliminadas.");
        return listado;
    }

    @Override
    public Long contarTodas() {
        Long cantidad = reclamoDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Reclamo.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = reclamoDAO.count();
        log.info("Existen {} entidades Reclamo, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public ReclamoModel guardar(ReclamoCreation creation) {
        log.info("Insertando la entidad Reclamo: {}.",  creation);
        ReclamoModel reclamoModel = reclamoDAO.save(reclamoMapper.toEntity(creation));
        if (creation.getId() != null) {
            reclamoModel.setCreada(Helper.getNow(""));
            reclamoModel.setCreador(usuarioService.obtenerUsuario());
            log.info("Se persistio correctamente la nueva entidad.");
        } else {
            reclamoModel.setModificada(Helper.getNow(""));
            reclamoModel.setModificador(usuarioService.obtenerUsuario());
            log.info("Se persistio correctamente la entidad.");
        }
        return reclamoDAO.save(reclamoModel);
    }

    @Override
    public ReclamoModel eliminar(Long id) {
        log.info("Eliminando la entidad Reclamo con id: {}.", id);
        ReclamoModel objeto = this.buscarPorId(id);
        objeto.setEliminada(Helper.getNow(""));
        objeto.setEliminador(usuarioService.obtenerUsuario());
        log.info("La entidad Reclamo con id: {}, fue eliminada correctamente.", id);
        return reclamoDAO.save(objeto);
    }

    @Override
    public ReclamoModel reciclar(Long id) {
        log.info("Reciclando la entidad Reclamo con id: {}.", id);
        ReclamoModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Reclamo con id: {}, no se encuentra eliminada, por lo tanto no es necesario reciclarla.", id);
            return null;
        }
        objeto.setEliminada(null);
        objeto.setEliminador(null);
        log.info("La entidad Reclamo con id: {}, fue reciclada correctamente.", id);
        return reclamoDAO.save(objeto);
    }

    @Override
    public Boolean destruir(Long id) {
        log.info("Destruyendo la entidad Reclamo con id: {}.", id);
        ReclamoModel objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEliminada() == null) {
            log.warn("La entidad Reclamo con id: {}, no se encuentra eliminada, por lo tanto no puede ser destruida.", id);
            return false;
        }
        reclamoDAO.delete(objeto);
        log.info("La entidad fue destruida.");
        return true;
    }
}
