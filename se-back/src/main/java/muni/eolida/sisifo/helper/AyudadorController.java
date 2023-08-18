package muni.eolida.sisifo.helper;

import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import muni.eolida.sisifo.service.implementation.RolServiceImpl;
import muni.eolida.sisifo.service.implementation.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/ayuda")
@RestController
public class HelperController {

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;
    @Autowired
    private RolServiceImpl rolService;

    @PutMapping(value = "/cargar-roles")
    public ResponseEntity<?> autoridades() {
        EntityMessenger<RolModel> rol1 = rolService.insertar(new RolCreation(RolEnum.CONTRIBUYENTE.name()));
        EntityMessenger<RolModel> rol2 = rolService.insertar(new RolCreation(RolEnum.EMPLEADO.name()));
        EntityMessenger<RolModel> rol3 = rolService.insertar(new RolCreation(RolEnum.CAPATAZ.name()));
        EntityMessenger<RolModel> rol4 = rolService.insertar(new RolCreation(RolEnum.JEFE.name()));


        return ResponseEntity.status(HttpStatus.OK).body(Helper.httpHeaders(rol1.getMessage() + " - " + rol2.getMessage()));
    }

    @PutMapping(value = "/cargar-usuarios")
    public ResponseEntity<?> usuarios() {
        EntityMessenger<UsuarioModel> user1 = usuarioServiceImpl.insertar(new UsuarioCreation("admin", "123","25 de Mayo 943", "4951160", "admin@municrespo.gob.ar", "admincito"));
        EntityMessenger<UsuarioModel> user2 = usuarioServiceImpl.insertar(new UsuarioCreation("Fulano Fulanito", "98987987","Siempreviva 123", "4951951", "fulano@fulanito.com.ar", "fulanito"));
        EntityMessenger<UsuarioModel> user3 = usuarioServiceImpl.insertar(new UsuarioCreation("Fulano Fulanito", "98987987","Siempreviva 123", "4951951", "fulano@fulanito.com.ar", "fulanito"));
        EntityMessenger<UsuarioModel> user4 = usuarioServiceImpl.insertar(new UsuarioCreation("Fulano Fulanito", "98987987","Siempreviva 123", "4951951", "fulano@fulanito.com.ar", "fulanito"));

        if (user1.getStatusCode() == 201) {
            usuarioServiceImpl.darRol(user1.getObject(), RolEnum.CAPATAZ);
        }
        if (user2.getStatusCode() == 201) {
            usuarioServiceImpl.darRol(user1.getObject(), RolEnum.CAPATAZ);
            usuarioServiceImpl.darRol(user2.getObject(), RolEnum.CONTRIBUYENTE);
        }

        return ResponseEntity.status(HttpStatus.OK).body(Helper.httpHeaders(user1.getMessage() + "\n" +user2.getMessage()));
    }
}
