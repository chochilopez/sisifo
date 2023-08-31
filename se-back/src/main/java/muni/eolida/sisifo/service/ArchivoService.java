package muni.eolida.sisifo.service;

import muni.eolida.sisifo.mapper.creation.ArchivoCreation;
import muni.eolida.sisifo.model.ArchivoModel;

import java.io.IOException;

public interface ArchivoService extends GenericService<ArchivoModel, ArchivoCreation> {
    ArchivoModel guardarArchivo(byte[] bytes) throws IOException;
}
