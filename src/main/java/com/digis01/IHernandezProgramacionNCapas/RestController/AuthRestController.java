package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.Component.JwtUtil;
import com.digis01.IHernandezProgramacionNCapas.Repository.IRepositoryUsuario;
import com.digis01.IHernandezProgramacionNCapas.Model.Usuario;
import com.digis01.IHernandezProgramacionNCapas.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("auth")
public class AuthRestController 
{
    private final IRepositoryUsuario iRepositoryUsuario;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRestController usuarioRestController;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    
    public AuthRestController(IRepositoryUsuario iRepositoryUsuario, UsuarioRestController usuarioRestController, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, EmailService emailService)
    {
        this.iRepositoryUsuario = iRepositoryUsuario;
        this.usuarioRestController = usuarioRestController;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }
    
    @PostMapping("signup")
    public ResponseEntity signup(@RequestBody Usuario usuario)
    {   
        return usuarioRestController.usuarioAdd(usuario);
//        return iRepositoryUsuario.save(usuario);
    }
    
    @PostMapping("login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Usuario cred) 
    {
        Usuario dbUser = iRepositoryUsuario.findByUsername(cred.getUsername());
     
        
        if (dbUser == null) 
        {
            return ResponseEntity.status(401).body(Map.of("error", "Usuario no encontrado"));
        }

        if (!passwordEncoder.matches(cred.getPassword(), dbUser.getPassword())) 
        {
            return ResponseEntity.status(401).body(Map.of("error", "Contraseña incorrecta"));
        }

        System.out.println("DEBUG LOGIN → " + dbUser.getUsername() + " | Status: " + dbUser.getStatus());

        if (dbUser.getStatus() == 1) 
        {
            String token = jwtUtil.generateToken(dbUser.getUsername(), dbUser.Rol.getNombre());
            return ResponseEntity.ok(Map.of(
                    "requiresVerification", false,
                    "token", token,
                    "username", dbUser.getUsername(),
                    "rol", dbUser.Rol.getNombre(),
                    "nombre", dbUser.getNombre()
            ));
        }

        if (dbUser.getToken() != null && dbUser.getTokenExpiry() != null
                && dbUser.getTokenExpiry().after(new Date())) 
        {
            return ResponseEntity.ok(Map.of("requiresVerification", true,
                                                                             "username", dbUser.getUsername(),
                                                                             "message", "Ya existe un correo de verificación pendiente. Revisa tu bandeja."));
        }

        String verifyToken = UUID.randomUUID().toString();
        dbUser.setToken(verifyToken);
        dbUser.setTokenExpiry(new Date(System.currentTimeMillis() + 2 * 60 * 1000)); // 2 min
        iRepositoryUsuario.saveAndFlush(dbUser);

        String verifyUrl = "http://localhost:8080/auth/verify?username=" + dbUser.getUsername() + "&token=" + verifyToken;
        try 
        {
            emailService.correoVerificacion(dbUser.getEmail(), dbUser.getNombre(), verifyUrl);
        } catch (Exception e) 
        {
            return ResponseEntity.status(500).body(Map.of("error", "No se pudo enviar el correo de verificación"));
        }

        return ResponseEntity.ok(Map.of("requiresVerification", true,
                                                                         "username", dbUser.getUsername(),
                                                                         "message", "Se envió un correo de verificación. Revisa tu bandeja e ingresa al enlace."));
    }
    
    @GetMapping("verify")
    public void verify(@RequestParam String username, @RequestParam String token, HttpServletResponse response) throws IOException
    {
        Usuario usuario = iRepositoryUsuario.findByUsername(username);
        if (usuario == null || usuario.getToken() == null || !(usuario.getToken().equals(token))) 
        {
            response.sendError(400, "Token inválido");
        }
        if (usuario.getTokenExpiry() == null || usuario.getTokenExpiry().before(new Date())) 
        {
            response.sendError(410, "Token expirado");
        }
        
        usuario.setStatus(1);
        usuario.setToken(null);
        usuario.setTokenExpiry(null);
        iRepositoryUsuario.save(usuario);
        
        response.sendRedirect("http://localhost:8081/login?verified=true");
    }
    
    @GetMapping("verificationstatus")
    public ResponseEntity<Map<String, Object>> verificarStatus(@RequestParam String username)
    {
        Usuario usuario = iRepositoryUsuario.findByUsername(username);
        if (usuario == null) 
        {
            return ResponseEntity.ok(Map.of("verified", false, "esists", false));
        }
        
        boolean expired = (usuario.getToken() != null && usuario.getTokenExpiry().before(new Date()));
        return ResponseEntity.ok(Map.of("verified", usuario.getStatus() == 1,
                                                                        "exists", true,
                                                                        "tokenPending", usuario.getToken() == null,
                                                                        "expired", expired));
    }
    
    @PostMapping("resendverification")
    public ResponseEntity<Map<String, Object>> reenviar(@RequestParam String username)
    {
        Usuario usuario = iRepositoryUsuario.findByUsername(username);
        if (usuario == null) 
        {
            return ResponseEntity.status(400).body(Map.of("error", "Usuario no encontrado"));
        }
        if (usuario.getStatus() == 1) 
        {
            return ResponseEntity.ok(Map.of("message", "La cuenta ya está verificada", "verified", true));
        }
        
        String token = UUID.randomUUID().toString();
        usuario.setToken(token);
        usuario.setTokenExpiry(new Date(System.currentTimeMillis() + 2 * 60 * 1000));
        iRepositoryUsuario.save(usuario);
        
        String verifyUrl = "http://localhost:8080/auth/verify?username=" + usuario.getUsername() + "&token=" + token;
        try 
        {
            emailService.correoVerificacion(usuario.getEmail(), usuario.getNombre(), verifyUrl);
        } catch (MessagingException ex) 
        {
            return ResponseEntity.status(500).body(Map.of("error", "No se pudo enviar el correo"));
        }
        return ResponseEntity.ok(Map.of("message", "Correo reenviado", "ok", true));
    }
    
    @PostMapping("forgotpassword")
    public ResponseEntity<Map<String, Object>> forgotPassword(@RequestBody Map<String, String> payload)
    {
        String identificador = payload.get("identifier");
        Usuario usuario = iRepositoryUsuario.findByUsername(identificador);
        if (usuario == null) 
        {
           usuario = iRepositoryUsuario.findByEmail(identificador);
        }
        if (usuario == null) 
        {
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }
        
        String resetToken = UUID.randomUUID().toString();
        usuario.setToken(resetToken);
        usuario.setTokenExpiry(new Date(System.currentTimeMillis() + 10 * 60 * 1000)); // token de 10 minutos
        iRepositoryUsuario.saveAndFlush(usuario);
        
        String resetUrl = "http://localhost:8080/auth/resetpassword?token=" + resetToken;
        try 
        {
            emailService.recuperacionContraseña(usuario.getEmail(), usuario.getUsername(), resetUrl);
        } catch (Exception ex) 
        {
            return ResponseEntity.status(500).body(Map.of("error", "Error al enviar el correo"));
        }
        return ResponseEntity.ok(Map.of("message", "Se envió un correo con instrucciones para reestablecer la contraseña"));
    }
    
    @GetMapping("resetpassword")
    public ModelAndView resetPasswordForm(@RequestParam String token) 
    {
        ModelAndView modelAndView = new ModelAndView("ResetPassword");
        Usuario usuario = iRepositoryUsuario.findByToken(token);
        
        if (usuario == null || usuario.getTokenExpiry() == null || usuario.getTokenExpiry().before(new Date())) 
        {
            modelAndView.addObject("error", "El enlace es inválido o ha expirado. Solicita uno nuevo.");
            modelAndView.setViewName("ResetPasswordError");
            return modelAndView;
        }
        
        modelAndView.addObject("token", token);
        return modelAndView;
    }
    
    @PostMapping("setnewpassword")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody Map<String, String> payload)
    {
        String token = payload.get("token");
        String newPassword = payload.get("newPassword");
        
        Usuario usuario = iRepositoryUsuario.findByToken(token);
        if (usuario == null) 
        {
            return ResponseEntity.status(400).body(Map.of("error", "Token inválido"));
        }
        
        if (usuario.getTokenExpiry() == null || usuario.getTokenExpiry().before(new Date())) 
        {
            return ResponseEntity.status(400).body(Map.of("error", "El enlace ha expirado. Solicita uno nuevo"));
        }
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        usuario.setPassword(encoder.encode(newPassword));
        usuario.setToken(null);
        usuario.setTokenExpiry(null);
        iRepositoryUsuario.saveAndFlush(usuario);
        
        return ResponseEntity.ok(Map.of("message", "Contraseña restablecida correctamente."));
    }
}