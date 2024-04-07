package edu.ib.networktechnologies.security;

import edu.ib.networktechnologies.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Autowired
    public JWTTokenFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String jwt;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            jwt = authHeader.substring(7);
            final String username = jwtService.extractUsername(jwt);
            final String role = jwtService.extractRole(jwt).toString();

            if (username != null && !username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtService.isTokenValid(jwt)) {
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null,
                            List.of(new SimpleGrantedAuthority(role)));
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(authentication);
                    SecurityContextHolder.setContext(securityContext);
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            filterChain.doFilter(request, response);
        }


//        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String token = authorizationHeader.split(" ")[1];
//
//            Claims claims = Jwts.parser().setSigningKey(key)
//                    .build().parseSignedClaims(token).getPayload();
//
//            String id = (String) claims.get("id");
//            String role = (String) claims.get("role");
//
//            if (id != null && role != null) {
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(id, null,
//                        List.of(new SimpleGrantedAuthority(role)));
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//
//            Authentication authentication = new UsernamePasswordAuthenticationToken(id, null,
//                    List.of(new SimpleGrantedAuthority(role)));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        } else {
//            SecurityContextHolder.getContext().setAuthentication(null);
//        }
//
//        filterChain.doFilter(request, response);
    }
}
