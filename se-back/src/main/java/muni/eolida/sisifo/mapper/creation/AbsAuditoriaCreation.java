package gloit.ixionida.neso.util.mapper.creation;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@MappedSuperclass
@Setter
public abstract class AbsAuditoriaCreation {
    private String creador_id;
    private String creada;
    private String eliminador_id;
    private String eliminada;
    private String modificador_id;
    private String modificada;
}
