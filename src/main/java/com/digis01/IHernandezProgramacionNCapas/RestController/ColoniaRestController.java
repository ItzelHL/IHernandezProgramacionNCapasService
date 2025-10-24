package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.ColoniaJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.Repository.IRepositoryColonia;
import com.digis01.IHernandezProgramacionNCapas.Model.Colonia;
import com.digis01.IHernandezProgramacionNCapas.Model.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "REST Controller de Colonia", description = "Controlador con métodos para Colonia.")
@RestController
@RequestMapping("api/colonia")
public class ColoniaRestController 
{
    @Autowired
    private IRepositoryColonia iRepositoryColonia;
    @Autowired
    ColoniaJPADAOImplementation coloniaJPADAOImplementation;
    
    //    ----------------------------------------------------------------------- JPAREPOSITORY -----------------------------------------------------------------------
    @GetMapping()
    public ResponseEntity GetAll()
    {
        Result result = new Result();
        try 
        {
            result.object = iRepositoryColonia.findAll();
            result.correct = true;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/municipio/{IdMunicipio}")
    public ResponseEntity GetById(@PathVariable("IdMunicipio") int idMunicipio)
    {
        Result result = new Result();
        try 
        {
            List<Colonia> colonias = iRepositoryColonia.findByMunicipio_IdMunicipio(idMunicipio);
            result.correct = true;
            result.object = colonias;

        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return ResponseEntity.ok(result);
    }
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. Estas son las colonias."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Colonia GetAll", description = "Trae todas las colonias.")
//    @GetMapping()
//    public ResponseEntity GetAll()
//    {
//        Result result;
//        result = coloniaJPADAOImplementation.GetAll();
//        
//        return ResponseEntity.status(result.status).body(result);
//    }
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. Estas son las colonias según el municipio seleccionado."),
//        @ApiResponse(responseCode = "400", description = "Bad Request. Verifique los datos ingresados."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Colonia by IdMunicipio", description = "Trae todas las colonias según el municipio seleccionado")
//    @GetMapping("/municipio/{IdMunicipio}")
//    public ResponseEntity GetById(@PathVariable("IdMunicipio") int IdMunicipio)
//    {
//        Result result;
//        result = coloniaJPADAOImplementation.ColoniaGetByMunicipio(IdMunicipio);
//        
//        return ResponseEntity.status(result.status).body(result);
//    }
}