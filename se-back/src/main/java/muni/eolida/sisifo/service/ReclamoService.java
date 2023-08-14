package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.mapper.creation.ReclamoCreation;
import muni.eolida.sisifo.model.ReclamoModel;

import java.util.List;

public interface ReclamoService extends GenericService<ReclamoCreation, ReclamoModel> {
    EntityMessenger<ReclamoModel> buscarPorCreador(Long id);
    EntityMessenger<ReclamoModel> buscarPorCreadorConBorrados(Long id);
    EntityMessenger<ReclamoModel> buscarMisReclamos();
}
