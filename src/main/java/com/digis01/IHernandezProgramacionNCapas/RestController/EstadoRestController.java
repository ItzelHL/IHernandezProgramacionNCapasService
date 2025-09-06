package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.EstadoJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import org.hibernate.query.results.Builders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/estado")
public class EstadoRestController 
{
    @Autowired
    EstadoJPADAOImplementation estadoJPADAOImplementation;
    
    @GetMapping("pais/{IdPais}")
    public ResponseEntity GetAll(@PathVariable("IdPais") int IdPais)
    {
        Result result;
        result = estadoJPADAOImplementation.EstadoByPaisGetAll(IdPais);
        
        return ResponseEntity.status(result.status).body(result);
    }
}
