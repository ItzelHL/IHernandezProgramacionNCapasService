package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.EstadoJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.IRepositoryEstado;
import com.digis01.IHernandezProgramacionNCapas.JPA.Estado;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "REST Controller de Estado", description = "Controlador con métodos para Estado.")
@RestController
@RequestMapping("api/estado")
public class EstadoRestController 
{
    @Autowired
    IRepositoryEstado iRepositoryEstado;
    @Autowired
    EstadoJPADAOImplementation estadoJPADAOImplementation;
    
    //    ----------------------------------------------------------------------- JPAREPOSITORY -----------------------------------------------------------------------

    @GetMapping()
    public ResponseEntity GetAll()
    {
        Result result = new Result();
        try 
        {
            result.object = iRepositoryEstado.findAll();
            result.correct = true;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("pais/{IdPais}")
    public ResponseEntity GetById(@PathVariable("IdPais") int idPais)
    {
        Result result = new Result();
        try 
        {
            List<Estado> estados = iRepositoryEstado.findByPais_IdPais(idPais);
            result.correct = true;
            result.object = estados;

        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return ResponseEntity.ok(result);
    }
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. Estos son los estados"),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Estado GetAll",  description = "Trae todos los estados,")
//    @GetMapping()
//    public ResponseEntity GetAll()
//    {
//        Result result;
//        result = estadoJPADAOImplementation.GetAll();
//        return ResponseEntity.status(result.status).body(result);
//    }
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. Esta es la información sobre el estado"),
//        @ApiResponse(responseCode = "400", description = "Bad Request. Verifique los valores ingresados."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Estado by IdPais",  description = "Trae todos los estados según el país seleccionado.")
//    @GetMapping("pais/{IdPais}")
//    public ResponseEntity GetById(@PathVariable("IdPais") int IdPais)
//    {
//        Result result;
//        result = estadoJPADAOImplementation.EstadoGetByPais(IdPais);
//        
//        return ResponseEntity.status(result.status).body(result);
//    } 
}