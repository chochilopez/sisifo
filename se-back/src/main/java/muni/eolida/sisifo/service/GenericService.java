package muni.eolida.sisifo.service;


import muni.eolida.sisifo.util.EntityMessenger;

public interface GenericService<M, C> {
    EntityMessenger<M> buscarPorId(Long id);
    EntityMessenger<M> buscarPorIdConEliminadas(Long id);
    EntityMessenger<M> buscarTodas();
    EntityMessenger<M> buscarTodasConEliminadas();
    Long contarTodas();
    Long contarTodasConEliminadas();
    EntityMessenger<M> insertar(C c);
    EntityMessenger<M> actualizar(C c);
    EntityMessenger<M> reciclar(Long id);
    EntityMessenger<M> eliminar(Long id);
    EntityMessenger<M> destruir(Long id);
}
