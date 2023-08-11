package muni.eolida.sisifo.helper;


public interface GenericService<E> {
    EntityMessenger<E> buscarPorId(Long id);
    EntityMessenger<E> buscarPorIdConBorrados(Long id);
    EntityMessenger<E> buscarTodos();
    EntityMessenger<E> buscarTodosConBorrados();
    Long contarTodos();
    Long contarTodosConBorrados();
    EntityMessenger<E> insertar(E e);
    EntityMessenger<E> actualizar(E e);
    EntityMessenger<E> reciclar(Long id);
    EntityMessenger<E> borrar(Long id);
    EntityMessenger<E> destruir(Long id);
}
