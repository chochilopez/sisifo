package muni.eolida.sisifo.helper;


public interface GenericService<E> {
    EntityMessenger<E> buscarPorId(Long id);
    EntityMessenger<E> buscarTodos();
    Long contarTodos();
    EntityMessenger<E> destruir(Long id);
    EntityMessenger<E> insertar(E e);
    EntityMessenger<E> actualizar(E e);

}
