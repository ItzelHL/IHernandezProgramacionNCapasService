package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.IRepositoryDireccion;
import com.digis01.IHernandezProgramacionNCapas.DAO.IRepositoryUsuario;
import com.digis01.IHernandezProgramacionNCapas.DAO.UsuarioJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.JPA.Direccion;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "REST Controller de Usuario", description = "Controlador con métodos CRUD para usuario.")
@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController 
{
    @Autowired
    private IRepositoryUsuario iRepositoryUsuario;
    @Autowired
    private IRepositoryDireccion iRepositoryDireccion;
    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    
    
//    ----------------------------------------------------------------------- REST -----------------------------------------------------------------------
//    @Operation(summary = "UsuarioIndex - GetAll", description = "Muestra el index con todos los usuarios y sus direcciones (UsuarioIndex)")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. Estos son los usuarios."),
//        @ApiResponse(responseCode = "400", description = "Bad Request. No se pudo obtener la información."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @GetMapping
//    public ResponseEntity GetAll(){
//        
//        Result result;
//        result = usuarioJPADAOImplementation.GetAll();
//
//        return ResponseEntity.status(result.status).body(result);
//    }
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. El usuario se agregó correctamente."),
//        @ApiResponse(responseCode = "400", description = "Bad Request. Verifique los datos ingresados."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Agregar usuario - UsuarioAdd", description = "Agrega un usuario incluyendo sus datos de dirección (UsuarioForm)")
//    @PostMapping("add") // localhost:8080/usuario/add   
//    public ResponseEntity UsuarioAdd(@RequestBody Usuario usuario){
//        
//        Result result;
//        result = usuarioJPADAOImplementation.Add(usuario);
//
//        return ResponseEntity.status(result.status).body(result);
//    }
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. El usuario se eliminó correctamente."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Eliminar usuario - UsuarioDelete", description = "Elimina un usuario y su dirección/direcciones")
//    @DeleteMapping("{IdUsuario}")
//    public ResponseEntity Delete(@PathVariable("IdUsuario") int IdUsuario) {
//        
//        Result result;
//        result = usuarioJPADAOImplementation.Delete(IdUsuario);
//
//        return ResponseEntity.status(result.status).body(result);
//    }
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. Esta es la información del usuario."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Detalle de usuario - UsuarioGetById", description = "Muestra del detalle (datos y direcciones) de un usuario específico (UsuarioDetail)")
//    @GetMapping("action/{IdUsuario}")
//    public ResponseEntity GetById(@PathVariable("IdUsuario") int IdUsuario) {
//        
//        Result result;
//        result = usuarioJPADAOImplementation.GetById(IdUsuario);
//
//        return ResponseEntity.status(result.status).body(result);
//    }
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. El usuario se modificó correctamente."),
//        @ApiResponse(responseCode = "400", description = "Bad Request. Verifique los datos ingresados."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Actualizar al usuario - UsuarioUpdate", description = "Actualiza únicamente la información del usuario")
//    @PutMapping("{IdUsuario}")
//    public ResponseEntity UpdateUsuario(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Usuario usuario){
//        
//        Result result;
//        usuario.setIdUsuario(IdUsuario);
//        result = usuarioJPADAOImplementation.Update(usuario);
//
//        return ResponseEntity.status(result.status).body(result);
//    }
    
//    @Operation(summary = "Status del usuario", description = "Actualiza el status del usuario sin hacer peticiones al cliente")
//    @PatchMapping("status/{IdUsuario}")
//    public ResponseEntity Status(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Usuario usuario)
//    {
//        Result result;
//        usuario.setIdUsuario(IdUsuario);
//        result = usuarioJPADAOImplementation.UpdateStatus(usuario);
//        
//        return ResponseEntity.status(result.status).body(result);
//    }
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK. El archivo se cargó correctamente."),
        @ApiResponse(responseCode = "400", description = "Bad Request. Verifique el archivo."),
        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
    @Operation(summary = "Cargar archivo", description = "Manda el archivo para la carga masiva")
    @PostMapping("cargamasiva")
    public ResponseEntity CargaMasiva(@RequestParam("archivo") MultipartFile file)
    {
        Result result;
        result = usuarioJPADAOImplementation.CargarArchivo(file);
        
        return ResponseEntity.status(result.status).body(result);
    }
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK. El archivo se procesó correctamente."),
        @ApiResponse(responseCode = "400", description = "Bad Request. Verifique los datos ingresados."),
        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
    @Operation(summary = "Procesar archivo", description = "Carga el archivo a la base de datos")
    @PostMapping("cargamasiva/procesar")
    public ResponseEntity CargaMasivaProcesar(@RequestParam("archivo") String nombreArchivo)
    {
        Result result;
        result = usuarioJPADAOImplementation.ProcesarArchivo(nombreArchivo);
        
        return ResponseEntity.status(result.status).body(result);
    }
    
    
    //    ----------------------------------------------------------------------- JPAREPOSITORY -----------------------------------------------------------------------
    @GetMapping
    public ResponseEntity GetAll()
    {
        Result result = new Result();
        try 
        {
            result.object = iRepositoryUsuario.findAll();
            result.correct = true;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("add")
    public ResponseEntity UsuarioAdd(@RequestBody Usuario usuario)
    {
        Result result = new Result();
        try 
        {
            Usuario usuarioDB = iRepositoryUsuario.save(usuario);
            Direccion direccion = usuario.Direcciones.get(0);
            if(direccion != null)
            {
                direccion.Usuario = usuarioDB;
                iRepositoryDireccion.save(direccion);
            }
            result.object = usuarioDB;
            result.correct = true;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("{IdUsuario}")
    public ResponseEntity Delete(@PathVariable("IdUsuario") int IdUsuario) 
    {
        Result result = new Result();
        
        try 
        {
            if(iRepositoryUsuario.existsById(IdUsuario)) 
            {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(IdUsuario);
                iRepositoryUsuario.delete(usuario);
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
    
    @GetMapping("action/{IdUsuario}")
    public ResponseEntity GetById(@PathVariable("IdUsuario") int IdUsuario)
    {
        Result result = new Result();
        try 
        {
            if(iRepositoryUsuario.existsById(IdUsuario)) 
            {
                result.object = iRepositoryUsuario.findById(IdUsuario);
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
    
    @PutMapping("{IdUsuario}")
    public ResponseEntity UpdateUsuario(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Usuario usuario)
    {
        Result result = new Result();
        try 
        {
            if(iRepositoryUsuario.existsById(IdUsuario)) 
            {
                usuario.setIdUsuario(IdUsuario);
                result.object = iRepositoryUsuario.save(usuario);
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
    
    @PatchMapping("status/{IdUsuario}")
    public ResponseEntity Status(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Usuario usuario)
    {
        Result result = new Result();
        try 
        {
            Optional<Usuario> usuarios = iRepositoryUsuario.findById(IdUsuario);
            if (usuarios.isPresent()) 
            {
                Usuario usuarioDB = usuarios.get();
                
                usuarioDB.setStatus(usuario.getStatus());
                result.object = iRepositoryUsuario.save(usuarioDB);
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
}
