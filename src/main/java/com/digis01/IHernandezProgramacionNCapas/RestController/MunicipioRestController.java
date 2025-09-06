package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.MunicipioJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/municipio")
public class MunicipioRestController 
{
    @Autowired
    private MunicipioJPADAOImplementation municipioJPADAOImplementation;
    
    @GetMapping("/estado/{IdEstado}")
    public ResponseEntity GetAll(@PathVariable("IdEstado") int IdEstado)
    {
        Result result;
        result = municipioJPADAOImplementation.MunicipioByEstadoGetAll(IdEstado);
        
        return ResponseEntity.status(result.status).body(result);
    }
}
