package muni.eolida.sisifo.helper;


public interface GenericService<E, T> {
    EntityMessenger<T> buscarPorId(Long id);
    EntityMessenger<T> buscarPorIdConBorrados(Long id);
    EntityMessenger<T> buscarTodos();
    EntityMessenger<T> buscarTodosConBorrados();
    Long contarTodos();
    Long contarTodosConBorrados();
    EntityMessenger<T> insertar(E e);
    EntityMessenger<T> actualizar(T t);
    EntityMessenger<T> reciclar(Long id);
    EntityMessenger<T> borrar(Long id);
    EntityMessenger<T> destruir(Long id);
}
