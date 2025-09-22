package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.IRepositoryPais;
import com.digis01.IHernandezProgramacionNCapas.DAO.PaisJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "REST Controller de País", description = "Controlador con métodos para País.")
@RestController
@RequestMapping("api/pais")
public class PaisRestController 
{
    @Autowired
    private IRepositoryPais iRepositoryPais;
    @Autowired
    private PaisJPADAOImplementation paisJPADAOImplementation;
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. Estos son los países."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "DDL - País GetAll", description = "Carga los países para los dropdown list.")
//    @GetMapping
//    public ResponseEntity GetAll(){
//        
//        Result result;
//        result = paisJPADAOImplementation.GetAll();
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
            result.object = iRepositoryPais.findAll();
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
