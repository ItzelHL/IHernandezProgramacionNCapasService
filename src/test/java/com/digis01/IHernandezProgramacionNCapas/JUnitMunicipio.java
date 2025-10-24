package com.digis01.IHernandezProgramacionNCapas;

import com.digis01.IHernandezProgramacionNCapas.Model.Result;
import com.digis01.IHernandezProgramacionNCapas.RestController.MunicipioRestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class JUnitMunicipio 
{
    @Autowired
    private MunicipioRestController municipioRestController;
    
    @Test
    public void GetAll()
    {
        ResponseEntity responseEntity = municipioRestController.GetAll();
        Result result = (Result) responseEntity.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        Assertions.assertEquals(responseEntity.getBody(), result);
        Assertions.assertInstanceOf(ResponseEntity.class, responseEntity);
    }
    
    @Test
    public void GetByIdEstado()
    {
        ResponseEntity response = municipioRestController.GetById(3);
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        Assertions.assertEquals(response.getBody(), result);
        Assertions.assertInstanceOf(ResponseEntity.class, response);
    }
}
