package muni.eolida.sisifo.service;

import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.model.ReclamoModel;

public interface ReclamoService extends GenericService<ReclamoCreation, ReclamoModel> {
    EntityMessenger<ReclamoModel> buscarMisReclamos();
    EntityMessenger<ReclamoModel> buscarTodasPorCreadorId(Long id);
    EntityMessenger<ReclamoModel> buscarTodasPorCreadorIdConEliminadas(Long id);
    EntityMessenger<ReclamoModel> buscarTodasPorTipoReclamoId(Long id);
    EntityMessenger<ReclamoModel> buscarTodasPorTipoReclamoIdConEliminadas(Long id);
    EntityMessenger<ReclamoModel> buscarTodasPorBarrioId(Long id);
    EntityMessenger<ReclamoModel> buscarTodasPorBarrioIdConEliminadas(Long id);
    EntityMessenger<ReclamoModel> buscarTodasPorCalleId(Long id);
    EntityMessenger<ReclamoModel> buscarTodasPorCalleIdConEliminadas(Long id);
    EntityMessenger<ReclamoModel> buscarTodasPorDescripcion(String descripcion);
    EntityMessenger<ReclamoModel> buscarTodasPorDescripcionConEliminadas(String descripcion);
    EntityMessenger<ReclamoModel> buscarTodasPorCreadaEntreFechas(String inicio, String fin);
    EntityMessenger<ReclamoModel> buscarTodasPorCreadaEntreFechasConEliminadas(String inicio, String fin);
}
