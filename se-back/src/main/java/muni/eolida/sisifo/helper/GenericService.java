package muni.eolida.sisifo.helper;


public interface GenericService<E, T> {
    EntityMessenger<T> buscarPorId(Long id);
    EntityMessenger<T> buscarPorIdConEliminadas(Long id);
    EntityMessenger<T> buscarTodas();
    EntityMessenger<T> buscarTodasConEliminadas();
    Long contarTodas();
    Long contarTodasConEliminadas();
    EntityMessenger<T> insertar(E e);
    EntityMessenger<T> actualizar(T t);
    EntityMessenger<T> reciclar(Long id);
    EntityMessenger<T> eliminar(Long id);
    EntityMessenger<T> destruir(Long id);
}
