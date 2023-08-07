package muni.eolida.sisifo.helper;


public interface GenericService<E> {
    EntityMessenger<E> findById(Long id);
    EntityMessenger<E> findAll();
    Long countAll();
    EntityMessenger<E> destroy(Long id);
}
