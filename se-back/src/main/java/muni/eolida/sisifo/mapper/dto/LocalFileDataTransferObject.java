package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LocalFileDataTransferObject implements Serializable {
    private String id;
    private String path;
    private String filename;
    private String filetype;
    private String description;
    private String filesize;
}
