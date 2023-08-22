package muni.eolida.sisifo.service;

import muni.eolida.sisifo.util.EntityMessenger;
import muni.eolida.sisifo.mapper.creation.ArchivoCreation;
import muni.eolida.sisifo.model.ArchivoModel;

public interface ArchivoService extends GenericService<ArchivoCreation, ArchivoModel> {
    EntityMessenger<ArchivoModel> guardarArchivo(byte[] bytes);
}
