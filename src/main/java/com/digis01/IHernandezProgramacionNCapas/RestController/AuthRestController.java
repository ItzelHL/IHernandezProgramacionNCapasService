package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.Component.JwtUtil;
import com.digis01.IHernandezProgramacionNCapas.DAO.IRepositoryUsuario;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthRestController 
{
    private final IRepositoryUsuario iRepositoryUsuario;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public AuthRestController(IRepositoryUsuario iRepositoryUsuario, PasswordEncoder passwordEncoder, JwtUtil jwtUtil)
    {
        this.iRepositoryUsuario = iRepositoryUsuario;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/signup")
    public Usuario signup(@RequestBody Usuario usuario)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        
        return iRepositoryUsuario.save(usuario);
    }
    
    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario)
    {
        Usuario dbUser = iRepositoryUsuario.findByUsername(usuario.getUsername());
        if (passwordEncoder.matches(usuario.getPassword(), dbUser.getPassword())) 
        {
            return jwtUtil.generateToken(dbUser.getUsername(), dbUser.Rol.getNombre());
        }
        throw new RuntimeException("Credenciales invalidas");
    }
}