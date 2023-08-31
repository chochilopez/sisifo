package muni.eolida.sisifo.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.RolModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import muni.eolida.sisifo.service.implementation.RolServiceImpl;
import muni.eolida.sisifo.service.implementation.UsuarioServiceImpl;
import muni.eolida.sisifo.util.EntityMessenger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*")
@Hidden
@RequestMapping(value = "/api/ayuda")
@RequiredArgsConstructor
@RestController
public class HelperController extends BaseController {
    private final UsuarioServiceImpl usuarioServiceImpl;
    private final RolServiceImpl rolService;

    @PutMapping(value = "/cargar-autoridades")
    public ResponseEntity<?> autoridades() {
        try {
            EntityMessenger<RolModel> rol1 = rolService.insertar(new RolCreation(RolEnum.CONTRIBUYENTE.name()));
            EntityMessenger<RolModel> rol2 = rolService.insertar(new RolCreation(RolEnum.EMPLEADO.name()));
            EntityMessenger<RolModel> rol3 = rolService.insertar(new RolCreation(RolEnum.CAPATAZ.name()));
            EntityMessenger<RolModel> rol4 = rolService.insertar(new RolCreation(RolEnum.JEFE.name()));

            EntityMessenger<UsuarioModel> user1 = usuarioServiceImpl.insertar(new UsuarioCreation(
                    null,
                    "contribuyente",
                    "12123123",
                    "Su Casa 1234",
                    "111 111 111",
                    "true",
                    "contribuyente@email.com.ar",
                    "contraseña",
                    null,
                    null,
                    List.of("1")
            ));
            EntityMessenger<UsuarioModel> user2 = usuarioServiceImpl.insertar(new UsuarioCreation(
                    null,
                    "empleado",
                    "45456456",
                    "Su trabajo 2345",
                    "222 222 222",
                    "true",
                    "empleado@municrespo.gob.ar",
                    "contraseña",
                    null,
                    null,
                    List.of("1", "2")
            ));
            EntityMessenger<UsuarioModel> user3 = usuarioServiceImpl.insertar(new UsuarioCreation(
                    null,
                    "capataz",
                    "78789789",
                    "Su oficina 3456",
                    "333 333 333",
                    "true",
                    "capataz@municrespo.gob.ar",
                    "contraseña",
                    null,
                    null,
                    List.of("1","2","3")
            ));
            EntityMessenger<UsuarioModel> user4 = usuarioServiceImpl.insertar(new UsuarioCreation(
                    null,
                    "jefe",
                    "00000000",
                    "Controlando 4567",
                    "444 444 444",
                    "true",
                    "jefe@municrespo.gob.ar",
                    "contraseña",
                    null,
                    null,
                    List.of("1","2","3","4")
            ));


            return ResponseEntity.status(HttpStatus.OK).body(rol1.getMensaje() + "\n" + rol2.getMensaje() + "\n" + rol3.getMensaje() + "\n" + rol4.getMensaje() + "\n" +
                    user1.getMensaje() + "\n" + user2.getMensaje() + "\n" + user3.getMensaje() + "\n" + user4.getMensaje());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
