package muni.eolida.sisifo.service;

import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.model.ReclamoModel;

import java.util.List;

public interface ReclamoService extends GenericService<ReclamoModel, ReclamoCreation> {
    List<ReclamoModel> buscarMisReclamos();
    List<ReclamoModel> buscarTodasPorCreadorId(Long id);
    List<ReclamoModel> buscarTodasPorCreadorIdConEliminadas(Long id);
    List<ReclamoModel> buscarTodasPorTipoReclamoId(Long id);
    List<ReclamoModel> buscarTodasPorTipoReclamoIdConEliminadas(Long id);
    List<ReclamoModel> buscarTodasPorBarrioId(Long id);
    List<ReclamoModel> buscarTodasPorBarrioIdConEliminadas(Long id);
    List<ReclamoModel> buscarTodasPorCalleId(Long id);
    List<ReclamoModel> buscarTodasPorCalleIdConEliminadas(Long id);
    List<ReclamoModel> buscarTodasPorDescripcion(String descripcion);
    List<ReclamoModel> buscarTodasPorDescripcionConEliminadas(String descripcion);
    List<ReclamoModel> buscarTodasPorCreadaEntreFechas(String inicio, String fin);
    List<ReclamoModel> buscarTodasPorCreadaEntreFechasConEliminadas(String inicio, String fin);
}
