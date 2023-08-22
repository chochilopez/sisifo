package muni.eolida.sisifo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import muni.eolida.sisifo.model.UsuarioModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${sisifo.app.jwtSecret}")
    private String secretKey;
    @Value("${sisifo.app.jwtExpiration}")
    private long jwtExpiration;
    @Value("${sisifo.app.refresh}")
    private long refreshExpiration;

    public String extraerUsuario(String token) {
        return extraerClaims(token, Claims::getSubject);
    }

    public <T> T extraerClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extraerTodasClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generarToken(UsuarioModel userDetails) {
        return generarToken(new HashMap<>(), userDetails);
    }

    public String generarToken(Map<String, Object> extraClaims, UsuarioModel userDetails) {
        return construirToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generarRefreshToken(UsuarioModel userDetails) {
        return construirToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String construirToken(Map<String, Object> extraClaims, UsuarioModel userDetails, long expiration) {
        extraClaims.put("rol", userDetails.getRoles().toString());

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getLlaveFirma(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean esValidoToken(String token, UserDetails userDetails) {
        final String username = extraerUsuario(token);
        return (username.equals(userDetails.getUsername())) && !esExpiradoToken(token);
    }

    private boolean esExpiradoToken(String token) {
        return extraerExpiracion(token).before(new Date());
    }

    private Date extraerExpiracion(String token) {
        return extraerClaims(token, Claims::getExpiration);
    }

    private Claims extraerTodasClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getLlaveFirma())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getLlaveFirma() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
