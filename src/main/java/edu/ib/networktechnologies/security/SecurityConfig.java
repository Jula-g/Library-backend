package edu.ib.networktechnologies.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTTokenFilter jwtTokenFilter;


    @Autowired
    public SecurityConfig(JWTTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                                .requestMatchers("api/auth/register").permitAll()
                                .requestMatchers("api/auth/login").permitAll()
                                .requestMatchers("api/user/**").hasRole("ADMIN")
                                .requestMatchers("api/book/**").authenticated()
                                .requestMatchers("api/**").authenticated()
                                .requestMatchers("api/loan/**").authenticated()
                                .requestMatchers("v3/**").permitAll()
                                .requestMatchers("swagger-ui/**").permitAll()
                                .requestMatchers("swagger-ui").permitAll()
                                .anyRequest().authenticated()
                )
                .build();
    }
}
