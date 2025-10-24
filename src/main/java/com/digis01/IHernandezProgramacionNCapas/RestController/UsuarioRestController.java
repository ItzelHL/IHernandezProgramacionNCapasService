package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.Repository.IRepositoryDireccion;
import com.digis01.IHernandezProgramacionNCapas.Repository.IRepositoryUsuario;
import com.digis01.IHernandezProgramacionNCapas.DAO.UsuarioJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.Model.Direccion;
import com.digis01.IHernandezProgramacionNCapas.Model.Result;
import com.digis01.IHernandezProgramacionNCapas.Model.Usuario;
import com.digis01.IHernandezProgramacionNCapas.Service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
public class UsuarioRestController 
{
    @Autowired
    private IRepositoryUsuario iRepositoryUsuario;
    @Autowired
    private IRepositoryDireccion iRepositoryDireccion;
    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    @Autowired
    private EmailService emailService;
    
    //    ----------------------------------------------------------------------- JPAREPOSITORY -----------------------------------------------------------------------
    @GetMapping
    public ResponseEntity getAll()
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
    public ResponseEntity usuarioAdd(@RequestBody Usuario usuario)
    {
        Result result = new Result();
        try 
        {
            String password = usuario.getPassword();
            
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            usuario.setPassword(encoder.encode(usuario.getPassword()));
            usuario.setStatus(0);
            
            String token = UUID.randomUUID().toString();
            usuario.setToken(token);          
            Usuario usuarioDB = iRepositoryUsuario.save(usuario);
            
            Direccion direccion = usuario.Direcciones.get(0);
            if(direccion != null)
            {
                direccion.Usuario = usuarioDB;
                iRepositoryDireccion.save(direccion);
            }
            result.object = usuarioDB;
            
            emailService.correoVerificacion(usuarioDB.getEmail(), usuarioDB.getNombre(), usuarioDB.getUsername(), password, token);
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
    public ResponseEntity delete(@PathVariable("IdUsuario") int IdUsuario) 
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
    public ResponseEntity getById(@PathVariable("IdUsuario") int IdUsuario)
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
    public ResponseEntity updateUsuario(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Usuario usuario)
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
    
    @PatchMapping("status/{idUsuario}")
    public ResponseEntity status(@PathVariable("idUsuario") int idUsuario, @RequestBody Usuario usuario)
    {
        Result result = new Result();
        try 
        {
            Optional<Usuario> usuarios = iRepositoryUsuario.findById(idUsuario);
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
    
    @GetMapping("username/{username}")
    public ResponseEntity getByUsername(@PathVariable("username") String username)
    {
        Result result = new Result();
        try 
        {
            Usuario usuario = iRepositoryUsuario.findByUsername(username);
            if (usuario != null) 
            {
                result.object = usuario;
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
    
    @GetMapping("verify")
   public ResponseEntity verificarCorreo(@RequestParam String token)
   {
       Result result = new Result();
       
       try 
       {
           Usuario usuario = iRepositoryUsuario.findByToken(token);
           if (usuario == null) 
           {
               result.correct = false;
               result.errorMessage = "Token inválido o expirado";
           }
           
           usuario.setStatus(1);
           usuario.setToken(null);
           iRepositoryUsuario.save(usuario);
           result.correct = true;
           result.errorMessage = "Cuenta verificada correctamente. Ya puedes iniciar sesión";
           
       } catch (Exception ex) 
       {
           result.correct = false;
           result.errorMessage = ex.getLocalizedMessage();
           result.ex = ex;
       }   
       return ResponseEntity.ok(result);
   }
    
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
    public ResponseEntity CargaMasivaProcesar(@RequestParam("rutaCifrada") String rutaCifrada)
    {
        Result result;
        result = usuarioJPADAOImplementation.ProcesarArchivo(rutaCifrada);
        
        return ResponseEntity.status(result.status).body(result);
    }
    
    
//    ----------------------------------------------------------------------- REST -----------------------------------------------------------------------
//    @Operation(summary = "UsuarioIndex - getAll", description = "Muestra el index con todos los usuarios y sus direcciones (UsuarioIndex)")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. Estos son los usuarios."),
//        @ApiResponse(responseCode = "400", description = "Bad Request. No se pudo obtener la información."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @GetMapping
//    public ResponseEntity getAll(){
//        
//        Result result;
//        result = usuarioJPADAOImplementation.getAll();
//
//        return ResponseEntity.status(result.status).body(result);
//    }
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. El usuario se agregó correctamente."),
//        @ApiResponse(responseCode = "400", description = "Bad Request. Verifique los datos ingresados."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Agregar usuario - usuarioAdd", description = "Agrega un usuario incluyendo sus datos de dirección (UsuarioForm)")
//    @PostMapping("add") // localhost:8080/usuario/add   
//    public ResponseEntity usuarioAdd(@RequestBody Usuario usuario){
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
//    public ResponseEntity delete(@PathVariable("IdUsuario") int IdUsuario) {
//        
//        Result result;
//        result = usuarioJPADAOImplementation.delete(IdUsuario);
//
//        return ResponseEntity.status(result.status).body(result);
//    }
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. Esta es la información del usuario."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Detalle de usuario - UsuarioGetById", description = "Muestra del detalle (datos y direcciones) de un usuario específico (UsuarioDetail)")
//    @GetMapping("action/{IdUsuario}")
//    public ResponseEntity getById(@PathVariable("IdUsuario") int IdUsuario) {
//        
//        Result result;
//        result = usuarioJPADAOImplementation.getById(IdUsuario);
//
//        return ResponseEntity.status(result.status).body(result);
//    }
    
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK. El usuario se modificó correctamente."),
//        @ApiResponse(responseCode = "400", description = "Bad Request. Verifique los datos ingresados."),
//        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
//    @Operation(summary = "Actualizar al usuario - UsuarioUpdate", description = "Actualiza únicamente la información del usuario")
//    @PutMapping("{IdUsuario}")
//    public ResponseEntity updateUsuario(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Usuario usuario){
//        
//        Result result;
//        usuario.setIdUsuario(IdUsuario);
//        result = usuarioJPADAOImplementation.Update(usuario);
//
//        return ResponseEntity.status(result.status).body(result);
//    }
    
//    @Operation(summary = "status del usuario", description = "Actualiza el status del usuario sin hacer peticiones al cliente")
//    @PatchMapping("status/{IdUsuario}")
//    public ResponseEntity status(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Usuario usuario)
//    {
//        Result result;
//        usuario.setIdUsuario(IdUsuario);
//        result = usuarioJPADAOImplementation.UpdateStatus(usuario);
//        
//        return ResponseEntity.status(result.status).body(result);
//    }
}