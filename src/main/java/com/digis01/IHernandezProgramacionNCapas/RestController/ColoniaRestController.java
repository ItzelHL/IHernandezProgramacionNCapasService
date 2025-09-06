package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.ColoniaJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/colonia")
public class ColoniaRestController 
{
    @Autowired
    ColoniaJPADAOImplementation coloniaJPADAOImplementation;
    
    @GetMapping("/municipio/{IdMunicipio}")
    public ResponseEntity GetAll(@PathVariable("IdMunicipio") int IdMunicipio)
    {
        Result result;
        result = coloniaJPADAOImplementation.ColoniaByMunicipioGetAll(IdMunicipio);
        
        return ResponseEntity.status(result.status).body(result);
    }
}
