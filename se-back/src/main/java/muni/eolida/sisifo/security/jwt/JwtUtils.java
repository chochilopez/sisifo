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
        if (roles.contains(new SimpleGrantedAuthority(RolEnum.JEFE.name())))
            claims.put("JEFE", true);
        if (roles.contains(new SimpleGrantedAuthority(RolEnum.CAPATAZ.name())))
            claims.put("CAPATAZ", true);
        if (roles.contains(new SimpleGrantedAuthority(RolEnum.EMPLEADO.name())))
            claims.put("EMPLEADO", true);
        if (roles.contains(new SimpleGrantedAuthority(RolEnum.CONTRIBUYENTE.name())))
            claims.put("CONTRIBUYENTE", true);

        return doGenerateToken(claims, userdetails.getUsername());
    }


//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
//        if (roles.contains(new SimpleGrantedAuthority(RolEnum.USUARIO.name())))
//            claims.put("USUARIO", true);
//        if (roles.contains(new SimpleGrantedAuthority(RolEnum.ADMINISTRADOR.name())))
//            claims.put("ADMINISTRADOR", true);
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

        Boolean jefe = claims.get("JEFE", Boolean.class);
        Boolean capataz = claims.get("CAPATAZ", Boolean.class);
        Boolean empleado = claims.get("EMPLEADO", Boolean.class);
        Boolean contribuyente = claims.get("CONTRIBUYENTE", Boolean.class);

        if (jefe != null && jefe)
            roles.add(new SimpleGrantedAuthority(RolEnum.JEFE.name()));
        if (capataz != null && capataz)
            roles.add(new SimpleGrantedAuthority(RolEnum.CAPATAZ.name()));
        if (empleado != null && empleado)
            roles.add(new SimpleGrantedAuthority(RolEnum.EMPLEADO.name()));
        if (contribuyente != null && contribuyente)
            roles.add(new SimpleGrantedAuthority(RolEnum.CONTRIBUYENTE.name()));

        return roles;
    }
}
