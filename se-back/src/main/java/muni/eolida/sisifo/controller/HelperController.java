package muni.eolida.sisifo.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import muni.eolida.sisifo.mapper.creation.RolCreation;
import muni.eolida.sisifo.mapper.creation.UsuarioCreation;
import muni.eolida.sisifo.model.enums.RolEnum;
import muni.eolida.sisifo.service.implementation.AreaServiceImpl;
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
    private final AreaServiceImpl areaService;

    @PutMapping(value = "/cargar-autoridades")
    public ResponseEntity<?> autoridades() {
        rolService.guardar(new RolCreation(RolEnum.CONTRIBUYENTE.name()));
        rolService.guardar(new RolCreation(RolEnum.EMPLEADO.name()));
        rolService.guardar(new RolCreation(RolEnum.CAPATAZ.name()));
        rolService.guardar(new RolCreation(RolEnum.JEFE.name()));

        usuarioServiceImpl.guardar(new UsuarioCreation(
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
        usuarioServiceImpl.guardar(new UsuarioCreation(
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
        usuarioServiceImpl.guardar(new UsuarioCreation(
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
        usuarioServiceImpl.guardar(new UsuarioCreation(
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

        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Soledad Erhardt", "00000000", "Municipalidad", "4951160", "true", "serhardt@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Adriana Suarez", "00000000", "Municipalidad", "4951160", "true", "aSuarez@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Juan Elsesser", "00000000", "Municipalidad", "4951160", "true", "jElsesser@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Dario Schneider", "00000000", "Municipalidad", "4951160", "true", "dschneider@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Belen Audagna", "00000000", "Municipalidad", "4951160", "true", "bAudagna@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Hernan Jacob", "00000000", "Municipalidad", "4951160", "true", "hJacob@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Portal Autogestion", "00000000", "Municipalidad", "4951160", "true", "usuario@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Cristian Benitez", "00000000", "Municipalidad", "4951160", "true", "cbenitez@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Diego Diaz", "00000000", "Municipalidad", "4951160", "true", "ddiaz@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Francisco Canoza", "00000000", "Municipalidad", "4951160", "true", "fcanoza@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Diego Stieben", "00000000", "Municipalidad", "4951160", "true", "dstieben@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Rafael Drescher", "00000000", "Municipalidad", "4951160", "true", "rdrescher@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Facundo Silva", "00000000", "Municipalidad", "4951160", "true", "fsilva@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Ariel Demartin", "00000000", "Municipalidad", "4951160", "true", "ademartin@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Anibal Prado", "00000000", "Municipalidad", "4951160", "true", "aprado@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Alexis Romero", "00000000", "Municipalidad", "4951160", "true", "aromero@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Irene Rondan", "00000000", "Municipalidad", "4951160", "true", "irondan@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Raquel Gorostiaga", "00000000", "Municipalidad", "4951160", "true", "rgorostiaga@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Pablo Riedel", "00000000", "Municipalidad", "4951160", "true", "pRiedel@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Marcelo Canosa", "00000000", "Municipalidad", "4951160", "true", "mcanosa@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Fabian Fernandez", "00000000", "Municipalidad", "4951160", "true", "ffernandez@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Guardia Urbana", "00000000", "Municipalidad", "4951160", "true", "GUM@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Horacio Torres", "00000000", "Municipalidad", "4951160", "true", "htorres@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Leticia Fernandez", "00000000", "Municipalidad", "4951160", "true", "lFernandez@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Sergio Schneider", "00000000", "Municipalidad", "4951160", "true", "sschneider@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Mario Morales", "00000000", "Municipalidad", "4951160", "true", "mmorales@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Silvio Gomez", "00000000", "Municipalidad", "4951160", "true", "sgomez@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Sergio Canoza", "00000000", "Municipalidad", "4951160", "true", "scanoza@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Carlos Albornoz", "00000000", "Municipalidad", "4951160", "true", "calbornoz@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Guillermo Arguello", "00000000", "Municipalidad", "4951160", "true", "garguello@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Gabriel Erhardt", "00000000", "Municipalidad", "4951160", "true", "ge@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Javier Mendez", "00000000", "Municipalidad", "4951160", "true", "jmendez@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Walter Loahiza", "00000000", "Municipalidad", "4951160", "true", "wloahiza@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Miguel Pussineri", "00000000", "Municipalidad", "4951160", "true", "mpusineri@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Jorge Muzzachioddi", "00000000", "Municipalidad", "4951160", "true", "muzza@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Noelia Piedrabuena", "00000000", "Municipalidad", "4951160", "true", "noelia@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Pedro Torres", "00000000", "Municipalidad", "4951160", "true", "ptorres@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Natividad Amoroto", "00000000", "Municipalidad", "4951160", "true", "namoroto@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Oscar Derfler", "00000000", "Municipalidad", "4951160", "true", "oderfler@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Julio Perez", "00000000", "Municipalidad", "4951160", "true", "jperez@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Andrea Colombara", "00000000", "Municipalidad", "4951160", "true", "acolombara@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Monica Erbes", "00000000", "Municipalidad", "4951160", "true", "moni@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Adriana Weinbinder", "00000000", "Municipalidad", "4951160", "true", "aw@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Lucas Erbes", "00000000", "Municipalidad", "4951160", "true", "lerbes@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Mirta Rickert", "00000000", "Municipalidad", "4951160", "true", "mrickert@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Paula Cian", "00000000", "Municipalidad", "4951160", "true", "pcian@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Manuel Boretto", "00000000", "Municipalidad", "4951160", "true", "mboretto@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Diego Marquez", "00000000", "Municipalidad", "4951160", "true", "dmarquez@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Vanesa Musso", "00000000", "Municipalidad", "4951160", "true", "vmusso@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Noemi Frank", "00000000", "Municipalidad", "4951160", "true", "nfrank@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Liliana Riffel", "00000000", "Municipalidad", "4951160", "true", "lriffel@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Jesica Salomon", "00000000", "Municipalidad", "4951160", "true", "jsalomon@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Patricia Battagliero", "00000000", "Municipalidad", "4951160", "true", "pbattagliero@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Carla Yunk", "00000000", "Municipalidad", "4951160", "true", "cGonzalez@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Paola Lencina", "00000000", "Municipalidad", "4951160", "true", "pLencina@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Fernanda Coassolo", "00000000", "Municipalidad", "4951160", "true", "fCoassolo@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Andrea Bernhardt", "00000000", "Municipalidad", "4951160", "true", "abernhardt@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));
        usuarioServiceImpl.guardar(new UsuarioCreation(null, "Betiana Gimenez", "00000000", "Municipalidad", "4951160", "true", "bGimenez@municrespo.gob.ar", "contraseña", null, null, List.of("1", "2")));

        areaService.agregarTipoReclamo((long)2,(long)1);
        areaService.agregarTipoReclamo((long)2,(long)2);
        areaService.agregarTipoReclamo((long)2,(long)3);
        areaService.agregarTipoReclamo((long)2,(long)4);
        areaService.agregarTipoReclamo((long)2,(long)5);
        areaService.agregarTipoReclamo((long)10,(long)6);
        areaService.agregarTipoReclamo((long)10,(long)7);
        areaService.agregarTipoReclamo((long)8,(long)8);
        areaService.agregarTipoReclamo((long)6,(long)9);
        areaService.agregarTipoReclamo((long)6,(long)10);
        areaService.agregarTipoReclamo((long)6,(long)11);
        areaService.agregarTipoReclamo((long)6,(long)12);
        areaService.agregarTipoReclamo((long)6,(long)13);
        areaService.agregarTipoReclamo((long)2,(long)14);
        areaService.agregarTipoReclamo((long)1,(long)15);
        areaService.agregarTipoReclamo((long)10,(long)16);
        areaService.agregarTipoReclamo((long)12,(long)17);
        areaService.agregarTipoReclamo((long)8,(long)18);
        areaService.agregarTipoReclamo((long)8,(long)19);
        areaService.agregarTipoReclamo((long)7,(long)20);
        areaService.agregarTipoReclamo((long)12,(long)21);
        areaService.agregarTipoReclamo((long)8,(long)22);
        areaService.agregarTipoReclamo((long)10,(long)23);
        areaService.agregarTipoReclamo((long)6,(long)24);
        areaService.agregarTipoReclamo((long)10,(long)25);
        areaService.agregarTipoReclamo((long)10,(long)26);
        areaService.agregarTipoReclamo((long)1,(long)27);
        areaService.agregarTipoReclamo((long)2,(long)28);
        areaService.agregarTipoReclamo((long)2,(long)29);
        areaService.agregarTipoReclamo((long)11,(long)30);
        areaService.agregarTipoReclamo((long)6,(long)31);
        areaService.agregarTipoReclamo((long)8,(long)32);
        areaService.agregarTipoReclamo((long)11,(long)33);
        areaService.agregarTipoReclamo((long)12,(long)34);
        areaService.agregarTipoReclamo((long)10,(long)35);
        areaService.agregarTipoReclamo((long)10,(long)36);

        return ResponseEntity.status(HttpStatus.OK).body("Autoridades y usuarios de testeo creados");
    }
}
