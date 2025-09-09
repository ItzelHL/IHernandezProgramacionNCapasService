package com.digis01.IHernandezProgramacionNCapas.RestController;

import com.digis01.IHernandezProgramacionNCapas.DAO.UsuarioJPADAOImplementation;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "REST Controller de Usuario", description = "Controlador con métodos CRUD para usuario.")
@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController 
{
    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    
    @Operation(summary = "UsuarioIndex - GetAll", description = "Muestra el index con todos los usuarios y sus direcciones (UsuarioIndex)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK. Estos son los usuarios."),
        @ApiResponse(responseCode = "400", description = "Bad Request. No se pudo obtener la información."),
        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
    @GetMapping
    public ResponseEntity GetAll(){
        
        Result result;
        result = usuarioJPADAOImplementation.GetAll();

        return ResponseEntity.status(result.status).body(result);
    }
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK. El usuario se agregó correctamente."),
        @ApiResponse(responseCode = "400", description = "Bad Request. Verifique los datos ingresados."),
        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
    @Operation(summary = "Agregar usuario - UsuarioAdd", description = "Agrega un usuario incluyendo sus datos de dirección (UsuarioForm)")
    @PostMapping("add") // localhost:8080/usuario/add   
    public ResponseEntity UsuarioAdd(@RequestBody Usuario usuario){
        
        Result result;
        result = usuarioJPADAOImplementation.Add(usuario);

        return ResponseEntity.status(result.status).body(result);
    }
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK. El usuario se eliminó correctamente."),
        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
    @Operation(summary = "Eliminar usuario - UsuarioDelete", description = "Elimina un usuario y su dirección/direcciones")
    @DeleteMapping("{IdUsuario}")
    public ResponseEntity Delete(@PathVariable("IdUsuario") int IdUsuario) {
        
        Result result;
        result = usuarioJPADAOImplementation.Delete(IdUsuario);

        return ResponseEntity.status(result.status).body(result);
    }
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK. Esta es la información del usuario."),
        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
    @Operation(summary = "Detalle de usuario - UsuarioGetById", description = "Muestra del detalle (datos y direcciones) de un usuario específico (UsuarioDetail)")
    @GetMapping("action/{IdUsuario}")
    public ResponseEntity GetById(@PathVariable("IdUsuario") int IdUsuario) {
        
        Result result;
        result = usuarioJPADAOImplementation.GetById(IdUsuario);

        return ResponseEntity.status(result.status).body(result);
    }
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK. El usuario se modificó correctamente."),
        @ApiResponse(responseCode = "400", description = "Bad Request. Verifique los datos ingresados."),
        @ApiResponse(responseCode = "500", description = "Error inesperado del sistema.")})
    @Operation(summary = "Actualizar al usuario - UsuarioUpdate", description = "Actualiza únicamente la información del usuario")
    @PutMapping("{IdUsuario}")
    public ResponseEntity UpdateUsuario(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Usuario usuario){
        
        Result result;
        usuario.setIdUsuario(IdUsuario);
        result = usuarioJPADAOImplementation.Update(usuario);

        return ResponseEntity.status(result.status).body(result);
    }
    
    @Operation(summary = "Status del usuario", description = "Actualiza el status del usuario sin hacer peticiones al cliente")
    @PatchMapping("status/{IdUsuario}")
    public ResponseEntity Status(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Usuario usuario)
    {
        Result result;
        usuario.setIdUsuario(IdUsuario);
        result = usuarioJPADAOImplementation.UpdateStatus(usuario);
        
        return ResponseEntity.status(result.status).body(result);
    }
}
