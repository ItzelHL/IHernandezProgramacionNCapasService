package com.digis01.IHernandezProgramacionNCapas.Configuration;

import com.digis01.IHernandezProgramacionNCapas.Component.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig 
{
    private final JwtFilter jwtFilter;
    
    public SecurityConfig(JwtFilter jwtFilter)
    {
        this.jwtFilter = jwtFilter;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/auth/**").permitAll()
                            .requestMatchers("/api/publico").permitAll()
                            .requestMatchers("/api/private").authenticated()
                            .requestMatchers("/api/admin").hasRole("Administrador")
                            .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }
}