package com.digis01.IHernandezProgramacionNCapas;

import com.digis01.IHernandezProgramacionNCapas.Model.Result;
import com.digis01.IHernandezProgramacionNCapas.RestController.EstadoRestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class JUnitEstado 
{
    @Autowired
    private EstadoRestController estadoRestController;
    
    @Test
    public void GetAll()
    {
        ResponseEntity response = estadoRestController.GetAll();
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNull(result.errorMessage);
        Assertions.assertNotNull(result.object);
    }
    
    @Test
    public void GetByIdPais()
    {
        ResponseEntity response = estadoRestController.GetById(2);
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNull(result.errorMessage);
        Assertions.assertNotNull(result.object);
    }
}
