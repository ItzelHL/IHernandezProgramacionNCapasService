package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.DireccionJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.DAO.IRepositoryDireccion;
import com.digis01.IHernandezProgramacionNCapas.JPA.Direccion;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "REST Controller de Dirección", description = "Controlador con métodos CRUD para Dirección.")
@RestController
@RequestMapping("api/direccion")
public class DireccionRestController 
{
    @Autowired
    private IRepositoryDireccion iRepositoryDireccion;
    @Autowired
    private DireccionJPADAOImplementation direccionJPADAOImplementation;
    
         //    ----------------------------------------------------------------------- JPAREPOSITORY -----------------------------------------------------------------------
    @GetMapping("detail/{IdDireccion}")
    public ResponseEntity GetById(@PathVariable("IdDireccion") int IdDireccion)
    {
        Result result = new Result();
        try 
        {
            if(iRepositoryDireccion.existsById(IdDireccion))
            {
                result.object = iRepositoryDireccion.findById(IdDireccion);
                result.correct = true;
            }
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("add/{IdUsuario}")
    public ResponseEntity AddDireccion(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Direccion direccion)
    {
        Result result = new Result();
        try 
        {
            direccion.Usuario = new Usuario();
            direccion.Usuario.setIdUsuario(IdUsuario);
            result.object = iRepositoryDireccion.save(direccion);
            result.correct = true; 
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return  ResponseEntity.ok(result);
    }
    
    @PutMapping("{IdUsuario}/direccion/{IdDireccion}")
    public ResponseEntity UpdateDireccion(@PathVariable("IdUsuario") int IdUsuario, @PathVariable("IdDireccion") int IdDireccion,
                                                                        @RequestBody Direccion direccion)
    {
        Result result = new Result();
        try 
        {
            if(iRepositoryDireccion.existsById(IdDireccion))
            {
                Usuario usuario = new Usuario();
                direccion.Usuario = usuario;
                direccion.Usuario.setIdUsuario(IdUsuario);
                direccion.setIdDireccion(IdDireccion);
                result.object = iRepositoryDireccion.save(direccion);
                result.correct = true;
            }
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("{IdDireccion}")
    public ResponseEntity Delete(@PathVariable("IdDireccion") int IdDireccion) 
    {
        Result result = new Result();
        try 
        {
            if (iRepositoryDireccion.existsById(IdDireccion)) 
            {
                Direccion direccion = new Direccion();
                direccion.setIdDireccion(IdDireccion);
                iRepositoryDireccion.delete(direccion);
                result.correct = true;
            }
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return ResponseEntity.ok(result);
    }
    
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. Estos son los datos de la dirección."),
//        @ApiResponse(responseCode = "400", description = "Bad Request. Verifique los valores ingresados."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Direccion GetById", description = "Obtiene el país, estado, municipio, colonia, la calle, número exterior y el número interior")
//    @GetMapping("detail/{IdDireccion}")
//    public ResponseEntity GetById(@PathVariable("IdDireccion") int IdDireccion)
//    {
//        Result result;
//        result = direccionJPADAOImplementation.GetById(IdDireccion);
//
//        return ResponseEntity.status(result.status).body(result);
//    }
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. La dirección se agregó correctamente."),
//        @ApiResponse(responseCode = "400", description = "Bad Request. Verifique los datos ingresados."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Agregar dirección - DireccionAdd", description = "Agrega una dirección a un usuario")
//    @PostMapping("add/{IdUsuario}")
//    public ResponseEntity AddDireccion(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Direccion direccion)
//    {
//        Result result;
//        direccion.Usuario = new Usuario();
//        direccion.Usuario.setIdUsuario(IdUsuario);
//        result = direccionJPADAOImplementation.AddDireccion(direccion);
//        
//        return  ResponseEntity.status(result.status).body(result);
//    }

//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. La dirección se modificó correctamente."),
//        @ApiResponse(responseCode = "400", description = "Bad Request. Verifique los datos ingresados."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Actualizar la dirección de un usuario - DireccionUpdate", description = "Actualiza los datos de un usuario desde un formulario editable")
//    @PutMapping("{IdUsuario}/direccion/{IdDireccion}")
//    public ResponseEntity UpdateDireccion(@PathVariable("IdUsuario") int IdUsuario, @PathVariable("IdDireccion") int IdDireccion,
//                                                                        @RequestBody Direccion direccion){
//        
//        Result result;
//        direccion.setIdDireccion(IdDireccion);
//        result = direccionJPADAOImplementation.UpdateDireccion(direccion);
//
//        return ResponseEntity.status(result.status).body(result);
//    }
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. La dirección se eliminó correctamente."),
//        @ApiResponse(responseCode = "400", description = "Bad Request. Verifique los datos ingresados."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Eliminar dirección - DireccionDelete", description = "Elimina la dirección de un usuario")
//    @DeleteMapping("{IdDireccion}")
//    public ResponseEntity Delete(@PathVariable("IdDireccion") int IdDireccion) {
//        
//        Result result;
//        result = direccionJPADAOImplementation.Delete(IdDireccion);
//
//        return ResponseEntity.status(result.status).body(result);
//    }
}