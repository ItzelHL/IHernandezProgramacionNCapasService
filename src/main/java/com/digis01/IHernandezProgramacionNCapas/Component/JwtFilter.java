package com.digis01.IHernandezProgramacionNCapas.Component;

import com.digis01.IHernandezProgramacionNCapas.Model.Usuario;
import com.digis01.IHernandezProgramacionNCapas.Service.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtFilter extends GenericFilter 
{
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    public JwtFilter(JwtUtil jwtUtil, UsuarioService usuarioService) 
    {
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException 
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) 
        {
            String token = header.substring(7);
            try 
            {
                Jws<Claims> claims = jwtUtil.validateToken(token);

                if (!jwtUtil.checkTokenUsage(claims)) 
                {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Token excedió el número de usos permitidos\"}");
                    return;
                }

                Usuario usuario = (Usuario) usuarioService.getByUsername(claims.getBody().getSubject());

                if (usuario == null || usuario.getStatus() == 0) 
                {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Cuenta no verificada. Revisa tu correo para activarla.\"}");
                    return;
                }
                
                UserDetails userDetails = usuarioService.loadUserByUsername(claims.getBody().getSubject());
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, 
                                                                                         userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception ignored) 
            {           
            }
        }
        chain.doFilter(req, res);
    }
}