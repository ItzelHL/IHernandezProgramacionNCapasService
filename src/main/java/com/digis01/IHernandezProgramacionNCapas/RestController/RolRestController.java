package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.RolJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rol")
public class RolRestController 
{
    @Autowired
    private RolJPADAOImplementation rolJPADAOImplementation;
    
    // TRAE TODOS LOS ROLES
    @GetMapping
    public ResponseEntity GetAll(){
        
        Result result;
        result = rolJPADAOImplementation.GetAll();

        return ResponseEntity.status(result.status).body(result);
    }
}
