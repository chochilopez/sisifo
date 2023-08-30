package muni.eolida.sisifo.mapper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Schema(name = "Reclamo", description = "Modelo de entidad de un reclamo realizado por un contribuyente.")
@Setter
public class ReclamoDTO implements Serializable {
    @Schema(description = "Identificador unico de la entidad.")
    private String id;
    @Schema(description = "Coordenadas Norte-Sur, latitud, del lugar donde se encuentra el problema que origina reclamo. Modelo de Grados Decimales(DD) de Google Maps.")
    private String coordinadaX;
    @Schema(description = "Coordenadas Este-Oeste, longitud, del lugar donde se encuentra el problema que origina reclamo. Modelo de Grados Decimales(DD) de Google Maps.")
    private String coordinadaY;
    @Schema(description = "Descripcion del peroblema que genera el reclamo.")
    private String descripcion;
    @Schema(description = "Altura en numeros donde se encuentra fisicamente el problema.")
    private String altura;
    @Schema(description = "Archivo de imagen del problema que genera el reclamo.")
    private ArchivoDTO imagen;
    @Schema(description = "Seguimiento del reclamo por parte de los empleados.")
    private SeguimientoDTO seguimiento;
    @Schema(description = "Calle donde se encuentra fisicamente el problema que origina el reclamo.")
    private CalleDTO calle;
    @Schema(description = "Barrio donde se encuentra fisicamente el problema que origina el reclamo.")
    private BarrioDTO barrio;
    @Schema(description = "De existir, calle o interseccion donde se ubica el problema sobre el cual se genera el reclamo.")
    private CalleDTO interseccion;
    @Schema(description = "Calles perpendiculares a la calle donde se ubica el problema que genera el reclamo. En orden de saber la ubicacion mas detallada.")
    private CalleDTO entreCalle1;
    @Schema(description = "Calles perpendiculares a la calle donde se ubica el problema que genera el reclamo. En orden de saber la ubicacion mas detallada.")
    private CalleDTO entreCalle2;
    @Schema(description = "Persona o contribuyente que genera el reclamo.")
    private UsuarioDTO persona;
    @Schema(description = "Tipo de reclamo. Categorias.")
    private TipoReclamoDTO tipoReclamo;
}
