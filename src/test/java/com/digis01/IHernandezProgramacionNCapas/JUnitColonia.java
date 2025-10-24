package com.digis01.IHernandezProgramacionNCapas;

import com.digis01.IHernandezProgramacionNCapas.Model.Result;
import com.digis01.IHernandezProgramacionNCapas.RestController.ColoniaRestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class JUnitColonia 
{
    @Autowired
    private ColoniaRestController coloniaRestController;
    
    @Test
    public void GetAll()
    {
        ResponseEntity response = coloniaRestController.GetAll();
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        Assertions.assertEquals(response.getBody(), result);
        Assertions.assertInstanceOf(ResponseEntity.class, response);
    }
    
    @Test
    public void GetByIdMunicipio()
    {
        ResponseEntity response = coloniaRestController.GetById(1);
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        Assertions.assertEquals(response.getBody(), result);
        Assertions.assertInstanceOf(ResponseEntity.class, response);
    }
}
