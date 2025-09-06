package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.UsuarioJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController 
{
    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    
    // MUESTRA EL INDEX CON TODOS LOS USUARIOS Y SUS DIRECCIONES (USUARIOINDEX)
    @GetMapping
    public ResponseEntity GetAll(){
        
        Result result;
        result = usuarioJPADAOImplementation.GetAll();

        return ResponseEntity.status(result.status).body(result);
    }
    
    // AGREGA UN USUARIO CON SU DIRECCION (USUARIOFORM)
    @PostMapping("add") // localhost:8080/usuario/add   
    public ResponseEntity UsuarioAdd(@RequestBody Usuario usuario){
        
        Result result;
        result = usuarioJPADAOImplementation.Add(usuario);

        return ResponseEntity.status(result.status).body(result);
    }
    
    // ELIMINA LA INFO Y DIRECCION DE UN USUARIO ESPECÍFICO
    @DeleteMapping("{IdUsuario}")
    public ResponseEntity Delete(@PathVariable("IdUsuario") int IdUsuario) {
        
        Result result;
        result = usuarioJPADAOImplementation.Delete(IdUsuario);

        return ResponseEntity.status(result.status).body(result);
    }
    
    // MUESTRA LA INFO DE UN USUARIO ESPECÍFICO (USUARIODETAIL)
    @GetMapping("action/{IdUsuario}")
    public ResponseEntity GetById(@PathVariable("IdUsuario") int IdUsuario) {
        
        Result result;
        result = usuarioJPADAOImplementation.GetById(IdUsuario);

        return ResponseEntity.status(result.status).body(result);
    }
    
    // ACTUALIZA UN USUARIO EN ESPECÍFICO
    @PutMapping("{IdUsuario}")
    public ResponseEntity UpdateUsuario(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Usuario usuario){
        
        Result result;
        usuario.setIdUsuario(IdUsuario);
        result = usuarioJPADAOImplementation.Update(usuario);

        return ResponseEntity.status(result.status).body(result);
    }
    
    // ACTUALIZA EL STATUS DEL USUARIO
    @PatchMapping("status/{IdUsuario}")
    public ResponseEntity Status(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Usuario usuario)
    {
        Result result;
        usuario.setIdUsuario(IdUsuario);
        result = usuarioJPADAOImplementation.UpdateStatus(usuario);
        
        return ResponseEntity.status(result.status).body(result);
    }
}
