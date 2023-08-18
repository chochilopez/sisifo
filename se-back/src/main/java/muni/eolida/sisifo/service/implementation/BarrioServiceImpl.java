package muni.eolida.sisifo.service.implementation;

import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntidadMensaje;
import muni.eolida.sisifo.helper.Ayudador;
import muni.eolida.sisifo.mapper.BarrioMapper;
import muni.eolida.sisifo.mapper.creation.BarrioCreation;
import muni.eolida.sisifo.model.BarrioModel;
import muni.eolida.sisifo.repository.BarrioDAO;
import muni.eolida.sisifo.service.BarrioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BarrioServiceImpl implements BarrioService {

    @Autowired
    private BarrioDAO barrioDAO;
    @Autowired
    private BarrioMapper barrioMapper;
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Override
    public EntidadMensaje<BarrioModel> buscarTodasPorBarrio(String barrio) {
        log.info("Buscando todas la entidades Barrio con nombre: {}.", barrio);
        List<BarrioModel> listado = barrioDAO.findAllByBarrioIgnoreCaseContainingAndEliminadaIsNull(barrio);
        if (listado.isEmpty()) {
            String mensaje = "No se encontraron entidades Barrio con nombre: " + barrio + ".";
            log.warn(mensaje);
            return new EntidadMensaje<BarrioModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontraron " + listado.size() + " entidades Barrio.";
            log.info(mensaje);
            return new EntidadMensaje<BarrioModel>(null, listado, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<BarrioModel> buscarTodasPorBarrioConEliminadas(String barrio) {
        log.info("Buscando todas la entidades Barrio con nombre: {}, incluidas las eliminadas.", barrio);
        List<BarrioModel> listado = barrioDAO.findAllByBarrioIgnoreCaseContaining(barrio);
        if (listado.isEmpty()) {
            String mensaje = "No se encontraron entidades Barrio con nombre: " + barrio + ", incluidas las eliminadas.";
            log.warn(mensaje);
            return new EntidadMensaje<BarrioModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontraron " + listado.size() + " entidades Barrio, incluidas las eliminadas.";
            log.info(mensaje);
            return new EntidadMensaje<BarrioModel>(null, listado, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<BarrioModel> buscarPorId(Long id) {
        log.info("Buscando la entidad Barrio con id: {}.", id);
        Optional<BarrioModel> objeto = barrioDAO.findByIdAndEliminadaIsNull(id);
        if (objeto.isEmpty()) {
            String mensaje = "No se encontro una entidad Barrio con id: " + id + ".";
            log.warn(mensaje);
            return new EntidadMensaje<BarrioModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontro una entidad Barrio.";
            log.info(mensaje);
            return new EntidadMensaje<BarrioModel>(objeto.get(), null, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<BarrioModel> buscarPorIdConEliminadas(Long id) {
        log.info("Buscando la entidad Barrio con id: {}, incluidas las eliminadas.", id);
        Optional<BarrioModel> objeto = barrioDAO.findById(id);
        if (objeto.isEmpty()) {
            String mensaje = "No se encontro una entidad Barrio con id: " + id + ", incluidas las eliminadas.";
            log.warn(mensaje);
            return new EntidadMensaje<BarrioModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontro una entidad Barrio, incluidas las eliminadas.";
            log.info(mensaje);
            return new EntidadMensaje<BarrioModel>(objeto.get(), null, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<BarrioModel> buscarTodas() {
        log.info("Buscando todas las entidades Barrio.");
        List<BarrioModel> listado = barrioDAO.findAllByEliminadaIsNull();
        if (listado.isEmpty()) {
            String mensaje = "No se encontraron entidades Barrio.";
            log.warn(mensaje);
            return new EntidadMensaje<BarrioModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontraron " + listado.size() + " entidades Barrio.";
            log.info(mensaje);
            return new EntidadMensaje<BarrioModel>(null, listado, mensaje, 200);
        }
    }

    @Override
    public EntidadMensaje<BarrioModel> buscarTodasConEliminadas() {
        log.info("Buscando todas las entidades Barrio, incluidas las eliminadas.");
        List<BarrioModel> listado = barrioDAO.findAll();
        if (listado.isEmpty()) {
            String mensaje = "No se encontrarón entidades Barrio, incluidas las eliminadas.";
            log.warn(mensaje);
            return new EntidadMensaje<BarrioModel>(null, null, mensaje, 202);
        } else {
            String mensaje = "Se encontraron " + listado.size() + " entidades Barrio, incluidas las eliminadas.";
            log.info(mensaje);
            return new EntidadMensaje<BarrioModel>(null, listado, mensaje, 200);
        }
    }

    @Override
    public Long contarTodas() {
        Long cantidad = barrioDAO.countAllByEliminadaIsNull();
        log.info("Existen {} entidades Barrio.", cantidad);
        return cantidad;
    }

    @Override
    public Long contarTodasConEliminadas() {
        Long cantidad = barrioDAO.count();
        log.info("Existen {} entidades Barrio, incluidas las eliminadas.", cantidad);
        return cantidad;
    }

    @Override
    public EntidadMensaje<BarrioModel> insertar(BarrioCreation model) {
        try {
            log.info("Insertando la entidad Barrio: {}.",  model);
            BarrioModel objeto = barrioDAO.save(barrioMapper.toEntity(model));
            objeto.setCreada(Ayudador.getAhora(""));
            objeto.setCreador(usuarioService.obtenerUsuario().getObjeto());
            barrioDAO.save(objeto);
            String mensaje = "La entidad Barrio con id: " + objeto.getId() + ", fue insertada correctamente.";
            log.info(mensaje);
            return new EntidadMensaje<BarrioModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar insertar la entidad Barrio. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntidadMensaje<BarrioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntidadMensaje<BarrioModel> actualizar(BarrioModel model) {
        try {
            log.info("Actualizando la entidad Barrio: {}.",  model);
            if (model.getId() != null) {
                EntidadMensaje<BarrioModel> entidad = this.buscarPorId(model.getId());
                if (entidad.getEstado() == 202)
                    return entidad;
            }
            model.setModificada(Ayudador.getAhora(""));
            model.setModificador(usuarioService.obtenerUsuario().getObjeto());
            BarrioModel objeto = barrioDAO.save(model);
            String mensaje = "La entidad Barrio con id: " + objeto.getId() + ", fue actualizada correctamente.";
            log.info(mensaje);
            return new EntidadMensaje<BarrioModel>(objeto, null, mensaje, 201);
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar actualizar la entidad Barrio. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntidadMensaje<BarrioModel>(null, null, mensaje, 204);
        }
    }

    @Override
    public EntidadMensaje<BarrioModel> reciclar(Long id) {
        log.info("Reciclando la entidad Barrio con id: {}.", id);
        EntidadMensaje<BarrioModel> objeto = this.buscarPorIdConEliminadas(id);
        if (objeto.getEstado() == 202) {
            return objeto;
        }
        if (objeto.getObjeto().getEliminada() == null) {
            String mensaje = "La entidad Barrio con id: " + id + ", no se encuentra eliminada, por lo tanto no es necesario reciclarla.";
            log.warn(mensaje);
            objeto.setMensaje(mensaje);
            return objeto;
        }
        objeto.getObjeto().setEliminada(null);
        objeto.getObjeto().setEliminador(null);
        objeto.setObjeto(barrioDAO.save(objeto.getObjeto()));
        String mensaje = "La entidad Barrio con id: " + id + ", fue reciclada correctamente.";
        objeto.setMensaje(mensaje);
        log.info(mensaje);
        return objeto;
    }

    @Override
    public EntidadMensaje<BarrioModel> eliminar(Long id) {
        log.info("Borrando la entidad Barrio con id: {}.", id);
        EntidadMensaje<BarrioModel> objeto = this.buscarPorId(id);
        if (objeto.getEstado() == 202) {
            return objeto;
        }
        objeto.getObjeto().setEliminada(Ayudador.getAhora(""));
        objeto.getObjeto().setEliminador(usuarioService.obtenerUsuario().getObjeto());
        objeto.setObjeto(barrioDAO.save(objeto.getObjeto()));
        String mensaje = "La entidad Barrio con id: " + id + ", fue borrada correctamente.";
        objeto.setMensaje(mensaje);
        log.info(mensaje);
        return objeto;
    }

    @Override
    public EntidadMensaje<BarrioModel> destruir(Long id) {
        try {
            log.info("Destruyendo la entidad Barrio con id: {}.", id);
            EntidadMensaje<BarrioModel> objeto = this.buscarPorIdConEliminadas(id);
            if (objeto.getEstado() == 202) {
                return objeto;
            }
            if (objeto.getObjeto().getEliminada() == null) {
                String mensaje = "La entidad Barrio con id: " + id + ", no se encuentra eliminada, por lo tanto no puede ser destruida.";
                log.info(mensaje);
                objeto.setEstado(202);
                objeto.setMensaje(mensaje);
                return objeto;
            }
            barrioDAO.delete(objeto.getObjeto());
            String mensaje = "La entidad fue destruida correctamente.";
            objeto.setMensaje(mensaje);
            objeto.setObjeto(null);
            log.info(mensaje);
            return objeto;
        } catch (Exception e) {
            String mensaje = "Ocurrió un error al intentar destruir la entidad Barrio. Excepción: " + e + ".";
            log.error(mensaje);
            return new EntidadMensaje<BarrioModel>(null, null, mensaje, 204);
        }
    }
}
