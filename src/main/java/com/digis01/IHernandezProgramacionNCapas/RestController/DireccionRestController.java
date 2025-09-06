package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.DireccionJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.JPA.Direccion;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/direccion")
public class DireccionRestController 
{
    @Autowired
    private DireccionJPADAOImplementation direccionJPADAOImplementation;
    
    // OBTIENE TODO SOBRE UNA DIRECCION EN ESPECIFICO
    @GetMapping("detail/{IdDireccion}")
    public ResponseEntity GetById(@PathVariable("IdDireccion") int IdDireccion)
    {
        Result result;
        result = direccionJPADAOImplementation.GetById(IdDireccion);

        return ResponseEntity.status(result.status).body(result);
    }
    
    // AGREGA UNA DIRECCIÓN A UN USUARIO
    @PostMapping("{IdUsuario}/add")
    public ResponseEntity AddDireccion(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Direccion direccion)
    {
        Result result;
        direccion.Usuario = new Usuario();
        direccion.Usuario.setIdUsuario(IdUsuario);
        result = direccionJPADAOImplementation.AddDireccion(direccion);
        
        return  ResponseEntity.status(result.status).body(result);
    }
    
    // ACTUALIZA UNA DIRECCIÓN DE UN USUARIO
    @PutMapping("{IdUsuario}/direccion/{IdDireccion}")
    public ResponseEntity UpdateDireccion(@PathVariable("IdUsuario") int IdUsuario, @PathVariable("IdDireccion") int IdDireccion,
                                                                        @RequestBody Direccion direccion){
        
        Result result;
        direccion.setIdDireccion(IdDireccion);
        result = direccionJPADAOImplementation.UpdateDireccion(direccion);

        return ResponseEntity.status(result.status).body(result);
    }
    
    // ELIMINA UNA DIRECCION
    @DeleteMapping("{IdDireccion}")
    public ResponseEntity Delete(@PathVariable("IdDireccion") int IdDireccion) {
        
        Result result;
        result = direccionJPADAOImplementation.Delete(IdDireccion);

        return ResponseEntity.status(result.status).body(result);
    }
}
