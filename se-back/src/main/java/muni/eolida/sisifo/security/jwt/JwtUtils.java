package muni.eolida.sisifo.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
@Slf4j
public class JwtUtils {
    @Value("${sisifo.app.jwtSecret}")
    private String jwtSecret;

    @Value("${sisifo.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateToken(UserDetails userdetails) {
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> roles = userdetails.getAuthorities();
        if (roles.contains(new SimpleGrantedAuthority(RolEnum.ROL_USUARIO.name())))
            claims.put("ROL_USUARIO", true);
        if (roles.contains(new SimpleGrantedAuthority(RolEnum.ROL_ADMINISTRADOR.name())))
            claims.put("ROL_ADMINISTRADOR", true);

        return doGenerateToken(claims, userdetails.getUsername());
    }


//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
//        if (roles.contains(new SimpleGrantedAuthority(RolEnum.ROL_USUARIO.name())))
//            claims.put("ROL_USUARIO", true);
//        if (roles.contains(new SimpleGrantedAuthority(RolEnum.ROL_ADMINISTRADOR.name())))
//            claims.put("ROL_ADMINISTRADOR", true);
//
//        return doGenerateToken(claims, userDetails.getUsername());
//    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
    }

    private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        } catch (ExpiredJwtException ex) {
            throw ex;
        }
    }

    public List<GrantedAuthority> getRolesFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        List<GrantedAuthority> roles=new ArrayList<GrantedAuthority>();

        Boolean usuario = claims.get("ROL_USUARIO", Boolean.class);
        Boolean administrador = claims.get("ROL_ADMINISTRADOR", Boolean.class);

        if (usuario != null && usuario)
            roles.add(new SimpleGrantedAuthority(RolEnum.ROL_USUARIO.name()));
        if (administrador != null && administrador)
            roles.add(new SimpleGrantedAuthority(RolEnum.ROL_ADMINISTRADOR.name()));

        return roles;
    }
}
