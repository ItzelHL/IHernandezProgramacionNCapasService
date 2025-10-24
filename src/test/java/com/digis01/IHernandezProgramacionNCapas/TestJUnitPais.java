package com.digis01.IHernandezProgramacionNCapas;

import com.digis01.IHernandezProgramacionNCapas.Model.Result;
import com.digis01.IHernandezProgramacionNCapas.RestController.PaisRestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class TestJUnitPais 
{
    @Autowired
    private PaisRestController paisRestController;
    
    @Test
    public void GetAll()
    {
        ResponseEntity response = paisRestController.GetAll();
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        Assertions.assertEquals(response.getBody(), result);
        Assertions.assertSame(HttpStatusCode.valueOf(200), response.getStatusCode());
    }
}
