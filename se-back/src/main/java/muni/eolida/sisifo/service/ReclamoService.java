package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.ServicioGenerico;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.model.ReclamoModel;
import java.time.LocalDateTime;

public interface ReclamoService extends ServicioGenerico<ReclamoCreation, ReclamoModel> {
    EntidadMensaje<ReclamoModel> buscarMisReclamos();
    EntidadMensaje<ReclamoModel> buscarTodasPorCreadorId(Long id);
    EntidadMensaje<ReclamoModel> buscarTodasPorCreadorIdConEliminadas(Long id);
    EntidadMensaje<ReclamoModel> buscarTodasPorTipoReclamoId(Long id);
    EntidadMensaje<ReclamoModel> buscarTodasPorTipoReclamoIdConEliminadas(Long id);
    EntidadMensaje<ReclamoModel> buscarTodasPorBarrioId(Long id);
    EntidadMensaje<ReclamoModel> buscarTodasPorBarrioIdConEliminadas(Long id);
    EntidadMensaje<ReclamoModel> buscarTodasPorCalleId(Long id);
    EntidadMensaje<ReclamoModel> buscarTodasPorCalleIdConEliminadas(Long id);
    EntidadMensaje<ReclamoModel> buscarTodasPorDescripcion(String descripcion);
    EntidadMensaje<ReclamoModel> buscarTodasPorDescripcionConEliminadas(String descripcion);
    EntidadMensaje<ReclamoModel> buscarTodasPorCreadaEntreFechas(LocalDateTime inicio, LocalDateTime fin);
    EntidadMensaje<ReclamoModel> buscarTodasPorCreadaEntreFechasConEliminadas(LocalDateTime inicio, LocalDateTime fin);
}
