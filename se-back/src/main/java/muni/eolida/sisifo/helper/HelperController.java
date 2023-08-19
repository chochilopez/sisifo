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


        return ResponseEntity.status(HttpStatus.OK).body(rol1.getMensaje() + "\n" + rol2.getMensaje() + "\n" + rol3.getMensaje() + "\n" + rol4.getMensaje());
    }

    @PutMapping(value = "/cargar-usuarios")
    public ResponseEntity<?> usuarios() {
        EntityMessenger<UsuarioModel> user1 = usuarioServiceImpl.insertar(new UsuarioCreation(
                "contribuyente",
                "12123123",
                "Su Casa 1234",
                "111 111 111",
                "contribuyente@email.com.ar",
                "contraseña"
        ));
        EntityMessenger<UsuarioModel> user2 = usuarioServiceImpl.insertar(new UsuarioCreation(
                "empleado",
                "45456456",
                "Su trabajo 2345",
                "222 222 222",
                "empleado@municrespo.gob.ar",
                "contraseña"
        ));
        EntityMessenger<UsuarioModel> user3 = usuarioServiceImpl.insertar(new UsuarioCreation(
                "capataz",
                "78789789",
                "Su oficina 3456",
                "333 333 333",
                "capataz@municrespo.gob.ar",
                "contraseña"
        ));
        EntityMessenger<UsuarioModel> user4 = usuarioServiceImpl.insertar(new UsuarioCreation(
                "jefe",
                "00000000",
                "Controlando 4567",
                "444 444 444",
                "jefe@municrespo.gob.ar",
                "contraseña"
        ));

        if (user1.getEstado() == 201) {
            usuarioServiceImpl.darRol(user1.getObjeto(), "CONTRIBUYENTE");
        }
        if (user2.getEstado() == 201) {
            usuarioServiceImpl.darRol(user1.getObjeto(), "CONTRIBUYENTE");
            usuarioServiceImpl.darRol(user2.getObjeto(), "EMPLEADO");
        }
        if (user3.getEstado() == 201) {
            usuarioServiceImpl.darRol(user1.getObjeto(), "CONTRIBUYENTE");
            usuarioServiceImpl.darRol(user2.getObjeto(), "EMPLEADO");
            usuarioServiceImpl.darRol(user2.getObjeto(), "CAPATAZ");
        }
        if (user4.getEstado() == 201) {
            usuarioServiceImpl.darRol(user1.getObjeto(), "CONTRIBUYENTE");
            usuarioServiceImpl.darRol(user2.getObjeto(), "EMPLEADO");
            usuarioServiceImpl.darRol(user2.getObjeto(), "CAPATAZ");
            usuarioServiceImpl.darRol(user2.getObjeto(), "JEFE");
        }

        return ResponseEntity.status(HttpStatus.OK).body(user1.getMensaje() + "\n" + user2.getMensaje() + "\n" + user3.getMensaje() + "\n" + user4.getMensaje());
    }
}
