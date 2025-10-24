package com.digis01.IHernandezProgramacionNCapas;

import com.digis01.IHernandezProgramacionNCapas.Model.Colonia;
import com.digis01.IHernandezProgramacionNCapas.Model.Direccion;
import com.digis01.IHernandezProgramacionNCapas.Model.Result;
import com.digis01.IHernandezProgramacionNCapas.Model.Usuario;
import com.digis01.IHernandezProgramacionNCapas.RestController.DireccionRestController;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class JUnitDireccion 
{
    @Autowired
    private DireccionRestController direccionRestController;
    
    @Test
    public void GetById()
    {
        ResponseEntity response = direccionRestController.GetById(5);
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        Assertions.assertEquals(response.getBody(), result);
        Assertions.assertInstanceOf(ResponseEntity.class, response);
    }
    
    @Test
    public void Add()
    {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(4);
        usuario.Direcciones = new ArrayList<>();
        Direccion direccion = new Direccion();
        
        direccion.setCalle("Av. Insurgentes Sur");
        direccion.setNumeroExterior("890");
        direccion.setNumeroInterior("2");
        
        direccion.Colonia = new Colonia();
        direccion.Colonia.setIdColonia(6);
        usuario.Direcciones.add(direccion);
        
        ResponseEntity response = direccionRestController.AddDireccion(usuario.getIdUsuario(), direccion);
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        Assertions.assertEquals(response.getBody(), result);
        Assertions.assertInstanceOf(ResponseEntity.class, response);
    }
    
    @Test
    public void Update()
    {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(223);
        usuario.Direcciones = new ArrayList<>();
        Direccion direccion = new Direccion();
        
        direccion.setCalle("Prolongación Reforma");
        direccion.setNumeroExterior("No. 321");
        direccion.setNumeroInterior("6C");
        
        direccion.Colonia = new Colonia();
        direccion.Colonia.setIdColonia(3);
        usuario.Direcciones.add(direccion);
        
        ResponseEntity response = direccionRestController.UpdateDireccion(usuario.getIdUsuario(), 168, direccion);
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        Assertions.assertEquals(response.getBody(), result);
        Assertions.assertInstanceOf(ResponseEntity.class, response);
    }
    
    @Test
    public void Delete()
    {
        ResponseEntity response = direccionRestController.Delete(164);
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertEquals(response.getBody(), result);
        Assertions.assertInstanceOf(ResponseEntity.class, response);
    }
}