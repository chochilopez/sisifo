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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
            RolModel rol1 = rolService.guardar(new RolCreation(RolEnum.CONTRIBUYENTE.name()));
            RolModel rol2 = rolService.guardar(new RolCreation(RolEnum.EMPLEADO.name()));
            RolModel rol3 = rolService.guardar(new RolCreation(RolEnum.CAPATAZ.name()));
            RolModel rol4 = rolService.guardar(new RolCreation(RolEnum.JEFE.name()));

            UsuarioModel user1 = usuarioServiceImpl.guardar(new UsuarioCreation(
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
            UsuarioModel user2 = usuarioServiceImpl.guardar(new UsuarioCreation(
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
            UsuarioModel user3 = usuarioServiceImpl.guardar(new UsuarioCreation(
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
            UsuarioModel user4 = usuarioServiceImpl.guardar(new UsuarioCreation(
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


            return ResponseEntity.status(HttpStatus.OK).body("Autoridades y usuarios de testeo creados");
    }
}
