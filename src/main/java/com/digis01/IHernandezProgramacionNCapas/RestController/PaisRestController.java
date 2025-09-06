package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.PaisJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pais")
public class PaisRestController 
{
    @Autowired
    private PaisJPADAOImplementation paisJPADAOImplementation;
    
    // MUESTRA EL INDEX CON TODOS LOS USUARIOS Y SUS DIRECCIONES (USUARIOINDEX)
    @GetMapping
    public ResponseEntity GetAll(){
        
        Result result;
        result = paisJPADAOImplementation.GetAll();

        return ResponseEntity.status(result.status).body(result);
    }
}
