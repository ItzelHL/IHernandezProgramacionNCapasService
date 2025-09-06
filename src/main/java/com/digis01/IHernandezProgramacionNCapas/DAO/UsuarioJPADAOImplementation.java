package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Direccion;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPADAO 
{
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() 
    {
        Result result = new Result();
        try 
        {
            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario ORDER BY IdUsuario", Usuario.class);
            result.object = queryUsuario.getResultList();
            
            result.correct = true;
            result.status = 200;
        } 
        catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return result;
    }
    
    @Override
    public Result GetById(int idUsuario)
    {
        Result result = new Result();
        
        try 
        {
            Usuario usuarioJPA = entityManager.find(Usuario.class, idUsuario);
            
            if (usuarioJPA != null) 
            {
                result.object = usuarioJPA;
                result.correct = true;
                result.status = 200;
            } else
            {
                result.errorMessage = "Usuario no encontrado, Id incorrecto";
                result.status = 400;
            }
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Transactional
    @Override
    public Result Add(Usuario usuario) 
    {
        Result result = new Result();
        
        try 
        {
            entityManager.persist(usuario);
            result.object = usuario;
            
            Direccion direccion = usuario.Direcciones.get(0);
            direccion.Usuario = usuario;
            direccion.Usuario.setIdUsuario(usuario.getIdUsuario());
            entityManager.persist(direccion);
            
            result.correct = true;
            result.status = 200;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        
        return result;
    }

    @Transactional
    @Override
    public Result Delete(int idUsuario) 
    {
        Result result = new Result();
        
        try 
        {
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            
            if (usuario != null) 
            {
                entityManager.remove(usuario);
                result.correct = true;
                result.status = 200;
            } else
            {
                result.errorMessage = "Usuario no encontrado, Id incorrecto";
                result.status = 400;
            }
           
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Transactional
    @Override
    public Result Update(Usuario usuario)
    {
        Result result = new Result();
        
        try 
        {
            Usuario usuarioBD = entityManager.find(Usuario.class, usuario.getIdUsuario());
            
            if(usuarioBD != null)
            {
                usuarioBD.setStatus(usuario.getStatus());
                usuarioBD.setImagen(usuario.getImagen());
                usuarioBD.setUsername(usuario.getUsername());
                usuarioBD.setNombre(usuario.getNombre());
                usuarioBD.setApellidoPaterno(usuario.getApellidoPaterno());
                usuarioBD.setApellidoMaterno(usuario.getApellidoMaterno());
                usuarioBD.setFechaNacimiento(usuario.getFechaNacimiento());
                usuarioBD.setSexo(usuario.getSexo());
                usuarioBD.setCurp(usuario.getCurp());
                usuarioBD.setEmail(usuario.getEmail());
                usuarioBD.setPassword(usuario.getPassword());
                usuarioBD.setTelefono(usuario.getTelefono());
                usuarioBD.setCelular(usuario.getCelular());
                usuarioBD.Rol = usuario.Rol;
                
                entityManager.merge(usuarioBD);
                result.correct = true;
                result.object = usuarioBD;
                result.status = 200;
            } else
            {
                result.errorMessage = "Usuario no encontrado, Id incorrecto";
                result.status = 400;
            }    
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }       
        return result;
    }
    
    @Transactional
    @Override
    public Result UpdateStatus(Usuario usuario)
    {
        Result result = new Result();
        
        try 
        {
            Usuario usuarioBD = entityManager.find(Usuario.class, usuario.getIdUsuario());
            
            if(usuarioBD != null)
            {
                usuarioBD.setStatus(usuario.getStatus());
                entityManager.merge(usuarioBD);
                
                result.object = usuarioBD;
                result.correct = true;
                result.status = 200;
            } else
            {
                result.errorMessage = "Usuario no encontrado, Id incorrecto";
                result.status = 400;
            }
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
}
