package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.ServicioGenerico;
import muni.eolida.sisifo.mapper.creation.ArchivoCreation;
import muni.eolida.sisifo.model.ArchivoModel;

public interface ArchivoService extends ServicioGenerico<ArchivoCreation, ArchivoModel> {
    EntidadMensaje<ArchivoModel> guardarArchivo(byte[] bytes);
}
