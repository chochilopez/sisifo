package muni.eolida.sisifo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.model.enums.RolEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        return construirToken(userDetails, jwtExpiration);
    }

    public String generarRefreshToken(UsuarioModel userDetails) {
        return construirToken(userDetails, refreshExpiration);
    }

    private String construirToken(UsuarioModel userDetails, long expiration) {
		Map<String, Object> claims = new HashMap<>();
		Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
		if (roles.contains(new SimpleGrantedAuthority(RolEnum.CONTRIBUYENTE.name())))
			claims.put("CONTRIBUYENTE", true);
		if (roles.contains(new SimpleGrantedAuthority(RolEnum.EMPLEADO.name())))
			claims.put("EMPLEADO", true);
		if (roles.contains(new SimpleGrantedAuthority(RolEnum.CAPATAZ.name())))
			claims.put("CAPATAZ", true);
		if (roles.contains(new SimpleGrantedAuthority(RolEnum.JEFE.name())))
			claims.put("JEFE", true);

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getLlaveFirma(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean esValidoToken(String token, UserDetails userDetails) {
		try {
			final String username = extraerUsuario(token);
			return (username.equals(userDetails.getUsername())) && !esExpiradoToken(token);
		} catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
		} catch (ExpiredJwtException ex) {
			throw ex;
		}
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

	public List<GrantedAuthority> getRolesFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		List<GrantedAuthority> roles=new ArrayList<GrantedAuthority>();

		Boolean contribuyente = claims.get("CONTRIBUYENTE", Boolean.class);
		Boolean empleado = claims.get("EMPLEADO", Boolean.class);
		Boolean capataz = claims.get("CAPATAZ", Boolean.class);
		Boolean jefe = claims.get("JEFE", Boolean.class);

		if (contribuyente != null && contribuyente)
			roles.add(new SimpleGrantedAuthority(RolEnum.CONTRIBUYENTE.name()));
		if (empleado != null && empleado)
			roles.add(new SimpleGrantedAuthority(RolEnum.EMPLEADO.name()));
		if (capataz != null && capataz)
			roles.add(new SimpleGrantedAuthority(RolEnum.CAPATAZ.name()));
		if (jefe != null && jefe)
			roles.add(new SimpleGrantedAuthority(RolEnum.JEFE.name()));

		return roles;
	}

	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
}
