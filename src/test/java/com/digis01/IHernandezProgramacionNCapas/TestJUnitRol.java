package com.digis01.IHernandezProgramacionNCapas;

import com.digis01.IHernandezProgramacionNCapas.Model.Result;
import com.digis01.IHernandezProgramacionNCapas.RestController.RolRestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class TestJUnitRol 
{
    @Autowired
    private RolRestController rolRestController;
    
    @Test
    public void GetAll()
    {
        ResponseEntity response = rolRestController.GetAll();
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNull(result.errorMessage);
        Assertions.assertEquals(response.getBody(), result);
    }
}
