package muni.eolida.sisifo.mapper.creation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalFileCreation {
    private String id;
    private String path;
    private String filename;
    private String filetype;
    private String description;
    private String filesize;
}
