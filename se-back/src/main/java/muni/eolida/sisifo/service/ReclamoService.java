package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.model.ReclamoModel;
import java.time.LocalDateTime;

public interface ReclamoService extends GenericService<ReclamoCreation, ReclamoModel> {
    EntityMessenger<ReclamoModel> buscarMisReclamos();
    EntityMessenger<ReclamoModel> buscarPorCreador(Long id);
    EntityMessenger<ReclamoModel> buscarPorCreadorConEliminadas(Long id);
    EntityMessenger<ReclamoModel> buscarPorCreadorId(Long id);
    EntityMessenger<ReclamoModel> buscarPorCreadorIdConEliminadas(Long id);
    EntityMessenger<ReclamoModel> buscarPorTipoReclamoId(Long id);
    EntityMessenger<ReclamoModel> buscarPorTipoReclamoIdConEliminadas(Long id);
    EntityMessenger<ReclamoModel> buscarPorBarrioId(Long id);
    EntityMessenger<ReclamoModel> buscarPorBarrioIdConEliminadas(Long id);
    EntityMessenger<ReclamoModel> buscarPorCalleId(Long id);
    EntityMessenger<ReclamoModel> buscarPorCalleIdConEliminadas(Long id);
    EntityMessenger<ReclamoModel> buscarTodasPorByCreadorNombre(String nombre);
    EntityMessenger<ReclamoModel> buscarTodasPorCreadorNombreConEliminadas(String nombre);
    EntityMessenger<ReclamoModel> buscarTodasPorCalleCalle(String calle);
    EntityMessenger<ReclamoModel> buscarTodasPorCalleCalleConEliminadas(String calle);
    EntityMessenger<ReclamoModel> buscarTodasPorTipoReclamoTipo(String tipo);
    EntityMessenger<ReclamoModel> buscarTodasPorTipoReclamoTipoConEliminadas(Long tipo);
    EntityMessenger<ReclamoModel> buscarTodasPorDescripcion(String descripcion);
    EntityMessenger<ReclamoModel> buscarTodasPorDescripcionConEliminadas(Long descripcion);
    EntityMessenger<ReclamoModel> buscarTodasPorBarrioCalle(String barrio);
    EntityMessenger<ReclamoModel> buscarTodasPorBarrioCalleConEliminadas(String barrio);
    EntityMessenger<ReclamoModel> buscarTodasPorCreadaEntreFechas(LocalDateTime inicio, LocalDateTime fin);
    EntityMessenger<ReclamoModel> buscarTodasPorCreadaEntreFechasConEliminadas(LocalDateTime inicio, LocalDateTime fin);
}
