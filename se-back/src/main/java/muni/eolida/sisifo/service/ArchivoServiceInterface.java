package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.model.ArchivoModel;

public interface ArchivoServiceInterface extends GenericService<ArchivoModel> {
    EntityMessenger<ArchivoModel> guardarArchivo(byte[] bytes, String nombre, String tipo, String descripcion, String tamanio);
    EntityMessenger<ArchivoModel> buscarTodosPorTipoArchivo(String tipo);
    Long contarTodosPorTipoArchivo(String tipo);
}
