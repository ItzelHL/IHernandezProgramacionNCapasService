package com.digis01.IHernandezProgramacionNCapas.Configuration;

import com.digis01.IHernandezProgramacionNCapas.Component.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
                            .requestMatchers("/auth/**", "/login", "/error", "/css/**", "/js/**", "/images/**")
                                    .permitAll()
                        
                            .requestMatchers(HttpMethod.DELETE, "/api/usuario/**", "/api/direccion/**")
                                    .hasRole("Administrador")
                            .requestMatchers(HttpMethod.POST, "/api/usuario/add", "/api/usuario/cargamasiva", 
                                    "/api/usuario/cargamasiva/procesar", "/api/direccion/add/**")
                                    .hasRole("Administrador")
                            .requestMatchers(HttpMethod.PUT, "/api/usuario/**", "/api/direccion/**")
                                    .hasRole("Administrador")
                            .requestMatchers(HttpMethod.PATCH, "/api/usuario/status/**")
                                    .hasRole("Administrador")
                        
                            .requestMatchers(HttpMethod.GET, "/api/usuario", "/api/usuario/**", "/api/usuario/action/**", 
                                    "/api/rol/**", "/api/pais/**", "/api/municipio/**", "/api/municipio/estado/**",
                                    "/api/estado/**", "/api/estado/pais/**", "/api/direccion/detail/**", 
                                    "/api/colonia/**","/api/colonia/municipio/**")
                                    .permitAll()
                        
                            .anyRequest().authenticated())
                
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }
}