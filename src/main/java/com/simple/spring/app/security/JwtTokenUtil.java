package com.simple.spring.app.security;

import com.simple.spring.app.exception.AuthorizationException;
import com.simple.spring.app.service.JwtUserDetailsService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username);
    }

    public Boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            throw new AuthorizationException("JWT token is expired!");
        } catch (SignatureException | MalformedJwtException e) {
            throw new AuthorizationException("JWT token is invalid!");
        } catch (RuntimeException e) {
            throw new AuthorizationException(e.getMessage());
        }
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDate(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null) {
            throw new AuthorizationException("Header 'Authorization' is required");
        }
        if (!bearerToken.startsWith("Bearer ")) {
            throw new AuthorizationException("'Bearer' is required at the starting of the token");
        }

        return bearerToken.substring(7);
    }
}