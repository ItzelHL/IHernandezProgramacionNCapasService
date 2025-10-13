package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.Component.JwtUtil;
import com.digis01.IHernandezProgramacionNCapas.DAO.IRepositoryUsuario;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import java.util.Map;
import org.springframework.http.ResponseEntity;
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
    private final UsuarioRestController usuarioRestController;
    private final JwtUtil jwtUtil;
    
    public AuthRestController(IRepositoryUsuario iRepositoryUsuario, UsuarioRestController usuarioRestController, PasswordEncoder passwordEncoder, JwtUtil jwtUtil)
    {
        this.iRepositoryUsuario = iRepositoryUsuario;
        this.usuarioRestController = usuarioRestController;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody Usuario usuario)
    {   
        return usuarioRestController.UsuarioAdd(usuario);
//        return iRepositoryUsuario.save(usuario);
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Usuario usuario)
    {
        Usuario dbUser = iRepositoryUsuario.findByUsername(usuario.getUsername());
        
        if (dbUser != null && passwordEncoder.matches(usuario.getPassword(), dbUser.getPassword())) 
        {
            String token = jwtUtil.generateToken(dbUser.getUsername(), dbUser.Rol.getNombre());
            return ResponseEntity.ok(Map.of("token", token, 
                                                                             "username", dbUser.getUsername(),
                                                                             "rol", dbUser.Rol.getNombre(),
                                                                             "nombre", dbUser.getNombre()));
        }
        return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
    }
}