package edu.ib.networktechnologies.services;

import edu.ib.networktechnologies.commonTypes.UserRole;
import edu.ib.networktechnologies.entities.Auth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private long tokenLifetime = 1000 * 60 * 24;

    @Value("${token.signing.key}")
    private String jwtSigningKey;

    public String generateToken(Auth userDetail) {
        return generateToken(new HashMap<>(), userDetail);
    }

    public boolean isTokenValid(String token) {
       try {
           return !isTokenExpired(token);
       }
         catch (Exception e) {
              return false;
         }
    }

    public UserRole extractRole(String token) {
        String roleString = extractClaim(token, (claims) -> claims.get("role", String.class));
        return UserRole.valueOf(roleString);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return  extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);

    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
    }

    public long getUserIdFromToken(String token) {
        String jwt = token.replace("Bearer ", "");
        Claims claims = extractAllClaims(jwt);
        return claims.get("userId", Long.class);
    }

    public String getUserRoleFromToken(String token) {
        String jwt = token.replace("Bearer ", "");
        Claims claims = extractAllClaims(jwt);
        return claims.get("role", String.class);
    }

    private String generateToken(Map<String, Object> extraClaims, Auth userDetail) {
        extraClaims.put("role", userDetail.getRole());
        extraClaims.put("userId", userDetail.getUser().getUserId());
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetail.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLifetime))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
