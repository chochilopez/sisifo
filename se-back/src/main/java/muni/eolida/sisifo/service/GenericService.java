package muni.eolida.sisifo.service;


import java.io.IOException;
import java.util.List;

public interface GenericService<M, C> {
    M buscarPorId(Long id);
    M buscarPorIdConEliminadas(Long id);
    List<M> buscarTodas();
    List<M> buscarTodasConEliminadas();
    Long contarTodas();
    Long contarTodasConEliminadas();
    M guardar(C c);
    M reciclar(Long id);
    M eliminar(Long id);
    void destruir(Long id) throws IOException;
}
