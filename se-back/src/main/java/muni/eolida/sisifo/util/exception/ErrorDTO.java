package muni.eolida.sisifo.util.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import muni.eolida.sisifo.util.Helper;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime fecha;

    private int codigo;

    private String estado;

    private String error;

    public ErrorDTO() {
        fecha = Helper.getNow("");
    }

    public ErrorDTO(HttpStatus httpStatus, String message) {
        this();
        this.codigo = httpStatus.value();
        this.estado = httpStatus.name();
        this.error = message;
    }
}