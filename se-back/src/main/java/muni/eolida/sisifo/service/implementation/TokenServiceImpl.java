package muni.eolida.sisifo.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.model.TokenModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.TipoToken;
import muni.eolida.sisifo.repository.TokenDAO;
import muni.eolida.sisifo.service.TokenService;
import muni.eolida.sisifo.util.exception.CustomDataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenDAO tokenDAO;

    @Override
    public void guardarTokenUsuario(UsuarioModel usuario, String jwt) {
        tokenDAO.save(new TokenModel(null, jwt, TipoToken.BEARER, false, false, usuario));
    }

    @Override
    public Boolean revocarTokensUsuario(UsuarioModel user) {
        List<TokenModel> validUserTokens = tokenDAO.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return false;
        validUserTokens.forEach(token -> {
            token.setExpirado(true);
            token.setRevocado(true);
        });
        tokenDAO.saveAll(validUserTokens);
        return true;
    }

    @Override
    public TokenModel buscarPorId(Long id) {
        log.info("Buscando la entidad Token con id: {}.", id);
        TokenModel tokenModel = tokenDAO.findById(id).orElseThrow(()-> new CustomDataNotFoundException("No se encontro la entidad Token con id " + id + "."));
        log.info("Se encontro una entidad Token con id: {}.");
        return tokenModel;
    }

    @Override
    public List<TokenModel> buscarTodas() {
        log.info("Buscando todas las entidades Token.");
        List<TokenModel> listado = tokenDAO.findAll();
        if (listado.isEmpty())
            throw new CustomDataNotFoundException("No se encontraron entidades Token.");
        return listado;
    }

    @Override
    public Long contarTodas() {
        Long cantidad = tokenDAO.count();
        log.info("Existen {} entidades Token.", cantidad);
        return cantidad;
    }

    @Override
    public TokenModel guardar(TokenModel tokenModel) {
        log.info("Insertando la entidad Token: {}.",  tokenModel);
        return tokenDAO.save(tokenModel);
    }

    @Override
    public Boolean destruir(Long id) {
        log.info("Destruyendo la entidad Token con id: {}.", id);
        TokenModel objeto = this.buscarPorId(id);
        tokenDAO.delete(objeto);
        log.info("La entidad fue destruida.", id);
        return true;
    }
}
