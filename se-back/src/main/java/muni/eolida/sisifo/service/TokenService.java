package muni.eolida.sisifo.service;

import muni.eolida.sisifo.model.TokenModel;
import muni.eolida.sisifo.model.UsuarioModel;

import java.util.List;

public interface TokenService {
    void guardarTokenUsuario(UsuarioModel usuario, String jwt);

    Boolean revocarTokensUsuario(UsuarioModel user);

    TokenModel buscarPorId(Long id);
    List<TokenModel> buscarTodas();
    Long contarTodas();
    TokenModel guardar(TokenModel e);
    Boolean destruir(Long id);
}
