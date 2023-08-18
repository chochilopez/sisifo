package muni.eolida.sisifo.helper;


public interface ServicioGenerico<E, T> {
    EntidadMensaje<T> buscarPorId(Long id);
    EntidadMensaje<T> buscarPorIdConEliminadas(Long id);
    EntidadMensaje<T> buscarTodas();
    EntidadMensaje<T> buscarTodasConEliminadas();
    Long contarTodas();
    Long contarTodasConEliminadas();
    EntidadMensaje<T> insertar(E e);
    EntidadMensaje<T> actualizar(T t);
    EntidadMensaje<T> reciclar(Long id);
    EntidadMensaje<T> eliminar(Long id);
    EntidadMensaje<T> destruir(Long id);
}
