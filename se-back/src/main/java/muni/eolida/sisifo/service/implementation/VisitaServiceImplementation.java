package muni.eolida.sisifo.service.implementation;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.model.VisitaModel;
import muni.eolida.sisifo.repository.VisitaRepository;
import muni.eolida.sisifo.service.VisitaServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class VisitaServiceImplementation implements VisitaServiceInterface {

    private final VisitaRepository visitaRepository;

    @Override
    public EntityMessenger<VisitaModel> buscarTodosPorIp(String ip) {
        log.info(" Buscando entidades Visita with ip: {}.", ip);
        List<VisitaModel> visitaModels = visitaRepository.findAllByIpContaining(ip);
        if (visitaModels.isEmpty()) {
            String message = "No existen entidades Visita con ip " + ip + ".";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = visitaModels.size() + " entidades Visita encontrada con ip " + ip + ".";
            log.info(message);
            return new EntityMessenger<VisitaModel>(null, visitaModels, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> findTopN(int number) {
        log.info("Buscando las primeras " + number + " entidades en Visita.");
        List<VisitaModel> visitaModels = visitaRepository.findTopN(number);
        if (visitaModels.isEmpty()) {
            String message = "No existen entidades Visita.";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = visitaModels.size() + " entidades Visita encontradas.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(null, visitaModels, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Visita con id: {}.", id);
        Optional<VisitaModel> object = visitaRepository.findById(id);
        if (object.isEmpty()) {
            String message = "No existe la entidad Visita con id: " + id + ".";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = "Una entidad Visita encontrada.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(object.get(), null, message, 200);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> buscarTodos() {
        log.info("Buscando todas las entidades Visita.");
        List<VisitaModel> list = visitaRepository.findAll();
        if (list.isEmpty()) {
            String message = "No existen entidades Visita.";
            log.warn(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 202);
        } else {
            String message = list.size() + " entidades Visita fueron encontradas.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(null, list, message, 200);
        }
    }

    @Override
    public Long contarTodos() {
        Long count = visitaRepository.count();
        log.info("Existen {} entidades Visita.", count);
        return count;
    }

    @Override
    public EntityMessenger<VisitaModel> insertar(VisitaModel newVisit) {
        try {
            log.info("Insertando entidad Visita: {}.",  newVisit);
            VisitaModel visitaModel = visitaRepository.save(newVisit);
            String message = "La entidad Visita con id: " + visitaModel.getId() + " fue insertada correctamente.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(visitaModel, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrio un error al intentar persistir la entidad. Excepcion: " + e + ".";
            log.error(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> actualizar(VisitaModel actualizarVisit) {
        try {
            log.info("Actualizando entidad Visita: {}.",  actualizarVisit);
            if (actualizarVisit.getId() != null) {
                EntityMessenger<VisitaModel> existant = this.buscarPorId(actualizarVisit.getId());
                if (existant.getStatusCode() == 202)
                    return existant;
            }
            VisitaModel visitaModel = visitaRepository.save(actualizarVisit);
            String message = "La entidad Visita con id: " + visitaModel.getId() + "fue actualizada correctamente.";
            log.info(message);
            return new EntityMessenger<VisitaModel>(visitaModel, null, message, 201);
        } catch (Exception e) {
            String message = "Ocurrio un error al intentar persistir la entidad. Excepcion: " + e + ".";
            log.error(message);
            return new EntityMessenger<VisitaModel>(null, null, message, 204);
        }
    }

    @Override
    public EntityMessenger<VisitaModel> destruir(Long id) {
        log.info("Destruyendo la entidad Visita con id: {}.", id);
        EntityMessenger<VisitaModel> entityMessenger = this.buscarPorId(id);
        if (entityMessenger.getStatusCode() == 202) {
            return entityMessenger;
        }
        visitaRepository.delete(entityMessenger.getObject());
        String message = "La entidad fue destruida.";
        entityMessenger.setMessage(message);
        entityMessenger.setObject(null);
        log.info(message);
        return entityMessenger;
    }
}
