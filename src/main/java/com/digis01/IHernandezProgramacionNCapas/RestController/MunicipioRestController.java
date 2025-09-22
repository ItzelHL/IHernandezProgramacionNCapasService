package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.IRepositoryMunicipio;
import com.digis01.IHernandezProgramacionNCapas.DAO.MunicipioJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.JPA.Estado;
import com.digis01.IHernandezProgramacionNCapas.JPA.Municipio;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.apache.poi.ss.formula.functions.Irr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "REST Controller de Municipio", description = "Controlador con métodos para Municipio.")
@RestController
@RequestMapping("api/municipio")
public class MunicipioRestController 
{
    @Autowired
    private IRepositoryMunicipio iRepositoryMunicipio;
    @Autowired
    private MunicipioJPADAOImplementation municipioJPADAOImplementation;
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. Estos son los municipios."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Municipio GetAll", description = "Trae todos los municipios.")
//    @GetMapping()
//    public ResponseEntity GetAll()
//    {
//        Result result;
//        result = municipioJPADAOImplementation.GetAll();
//        
//        return ResponseEntity.status(result.status).body(result);
//    }
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. Esta es la información del municipio"),
//        @ApiResponse(responseCode = "400", description = "Bad Request. Verifique los valores ingresados."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Municipio by IdEstado", description = "Trae todos los municipios según el estado seleccionado.")
//    @GetMapping("/estado/{IdEstado}")
//    public ResponseEntity GetById(@PathVariable("IdEstado") int IdEstado)
//    {
//        Result result;
//        result = municipioJPADAOImplementation.MunicipioGetByEstado(IdEstado);
//        
//        return ResponseEntity.status(result.status).body(result);
//    }
    
//    ----------------------------------------------------------------------- JPAREPOSITORY -----------------------------------------------------------------------
    @GetMapping()
    public ResponseEntity GetAll()
    {
        Result result = new Result();
        try 
        {
            result.object = iRepositoryMunicipio.findAll();
            result.correct = true;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("estado/{IdEstado}")
    public ResponseEntity GetById(@PathVariable("IdEstado") int idEstado)
    {
        Result result = new Result();
        try 
        {
            List<Municipio> municipios = iRepositoryMunicipio.findByEstado_IdEstado(idEstado);
            result.correct = true;
            result.object = municipios;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return ResponseEntity.ok(result);
    }
}
