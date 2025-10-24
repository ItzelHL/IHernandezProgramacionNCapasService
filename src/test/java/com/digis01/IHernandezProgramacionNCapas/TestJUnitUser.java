package com.digis01.IHernandezProgramacionNCapas;

import com.digis01.IHernandezProgramacionNCapas.Model.Colonia;
import com.digis01.IHernandezProgramacionNCapas.Model.Direccion;
import com.digis01.IHernandezProgramacionNCapas.Model.Result;
import com.digis01.IHernandezProgramacionNCapas.Model.Rol;
import com.digis01.IHernandezProgramacionNCapas.Model.Usuario;
import com.digis01.IHernandezProgramacionNCapas.RestController.UsuarioRestController;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class TestJUnitUser 
{
    @Autowired
    private UsuarioRestController usuarioRestController;
    
    @Test
    public void GetAll()
    {
        ResponseEntity response = usuarioRestController.getAll();
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        Assertions.assertInstanceOf(ResponseEntity.class, response);
    }
    
    @Test
    public void GetById()
    {
        ResponseEntity response = usuarioRestController.getById(3);
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        Assertions.assertNull(result.errorMessage);
        Assertions.assertInstanceOf(ResponseEntity.class, response);
    }
    
    @Test
    public void GetByUsername()
    {
        ResponseEntity response = usuarioRestController.getByUsername("NMur99");
        Result result = (Result) response.getBody();
        
        Assertions.assertNotNull(result.object);
        Assertions.assertTrue(result.correct);
        Assertions.assertNull(result.errorMessage);
    }
    
    @Test
    @Transactional
    public void UsuarioAdd()
    {
        Usuario usuario = new Usuario();
        usuario.setStatus(1);
        usuario.setUsername("ECue88");
        usuario.setNombre("Ernesto");
        usuario.setApellidoPaterno("Cuellar");
        usuario.setApellidoMaterno("Domínguez");
        usuario.setFechaNacimiento(new Date(1985, 10, 10));
        usuario.setSexo("M");
        usuario.setCurp("CUDE881010HDFRLS01");
        usuario.setEmail("ecue88@example.com");
        usuario.setPassword("ECue88*@");
        usuario.setTelefono("5558890012");
        usuario.setCelular("5513344556");
        
        usuario.Rol = new Rol();
        usuario.Rol.setIdRol(3);
        
        usuario.Direcciones = new ArrayList<>();
        Direccion direccion = new Direccion();
        direccion.setCalle("Av. Patria");
        direccion.setNumeroExterior("No. 45");
        direccion.setNumeroInterior("2B");
        
        direccion.Colonia = new Colonia();
        direccion.Colonia.setIdColonia(19);
        usuario.Direcciones.add(direccion);
        
        ResponseEntity response = usuarioRestController.usuarioAdd(usuario);
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        Assertions.assertInstanceOf(ResponseEntity.class, response);
    }
    
    @Test
    public void Delete()
    {
        ResponseEntity response = usuarioRestController.delete(388);
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertInstanceOf(ResponseEntity.class, response);
    }
    
    @Test
    public void Update()
    {
        Usuario usuario = new Usuario();
        usuario.setUsername("NMur99");
        usuario.setNombre("Natalia");
        usuario.setApellidoPaterno("Murillo");
        usuario.setApellidoMaterno("Paredes");
        usuario.setEmail("nmur99@example.com");
        usuario.setPassword("NMur99*@");
        usuario.setFechaNacimiento(new Date(1999, 8, 13));
        usuario.setSexo("F");
        usuario.setTelefono("5552234455");
        usuario.setCelular("5529981112");
        usuario.setCurp("MUPN990813MDFRRL06");
        usuario.Rol = new Rol();
        usuario.Rol.setIdRol(1);
        
        ResponseEntity response = usuarioRestController.updateUsuario(327, usuario);
        Result result = (Result) response.getBody();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        Assertions.assertInstanceOf(Result.class, result);
    }
}