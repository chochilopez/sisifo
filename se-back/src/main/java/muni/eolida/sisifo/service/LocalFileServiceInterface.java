package muni.eolida.sisifo.service;

import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.GenericService;
import muni.eolida.sisifo.model.LocalFileModel;

public interface LocalFileServiceInterface extends GenericService<LocalFileModel> {
    EntityMessenger<LocalFileModel> saveLocalFile(byte[] pdfFile, String filename, String filetypeEnum, String description, String filesize );
    EntityMessenger<LocalFileModel> findAllByFiletype(String filetypeEnum);
    Long countAllByFiletype(String filetypeEnum);
    EntityMessenger<LocalFileModel> insert(LocalFileModel localFileModel);
    EntityMessenger<LocalFileModel> update(LocalFileModel localFileModel);
}
