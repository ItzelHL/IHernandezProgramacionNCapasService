package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.UsuarioJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarioapi")
public class UsuarioRestController 
{
    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    
    @GetMapping
    public ResponseEntity GetAll()
    {
        Result result;
        try 
        {
            result = usuarioJPADAOImplementation.GetAll();
            result.correct = true;
            return ResponseEntity.status(200).body(result);
            
        } catch (Exception ex) 
        {
            result = new Result();
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            return ResponseEntity.status(500).body(result);
        }
    }
    
    @GetMapping("/action/{IdUsuario}")
    public ResponseEntity GetById(@PathVariable("IdUsuario") int IdUsuario) 
    {
        Result result;
        try 
        {
            result = usuarioJPADAOImplementation.GetById(IdUsuario);
            result.correct = true;
            return ResponseEntity.status(200).body(result);
        } catch (Exception ex) 
        {
            result = new Result();
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            return ResponseEntity.status(500).body(result);
        }
    }
}
