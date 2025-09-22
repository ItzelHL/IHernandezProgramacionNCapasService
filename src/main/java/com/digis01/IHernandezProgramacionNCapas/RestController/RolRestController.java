package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.IRepositoryRol;
import com.digis01.IHernandezProgramacionNCapas.DAO.RolJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "REST Controller de Rol", description = "Controlador con métodos para Rol.")
@RestController
@RequestMapping("api/rol")
public class RolRestController 
{
    @Autowired
    private IRepositoryRol iRepositoryRol;
    @Autowired
    private RolJPADAOImplementation rolJPADAOImplementation;
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. Estos son los roles."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Rol GetAll", description = "Trae todos los roles")
//    @GetMapping
//    public ResponseEntity GetAll(){
//        
//        Result result;
//        result = rolJPADAOImplementation.GetAll();
//
//        return ResponseEntity.status(result.status).body(result);
//    }
    
        //    ----------------------------------------------------------------------- JPAREPOSITORY -----------------------------------------------------------------------
    @GetMapping
    public ResponseEntity GetAll()
    {
        Result result = new Result();
        try 
        {
            result.object = iRepositoryRol.findAll();
            result.correct = true;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return ResponseEntity.ok(result);
    }
}
