package com.digis01.IHernandezProgramacionNCapas.DAO;

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
        } 
        catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
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
            result.object = usuarioJPA;
            
            result.correct = true;
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Override
    @Transactional
    public Result Add(Usuario usuario) 
    {
        Result result = new Result();
        
        try 
        {
            entityManager.persist(usuario);
            
            result.correct = true;
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
    public Result Delete(int idUsuario) 
    {
        Result result = new Result();
        
        try 
        {
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            entityManager.remove(usuario);
            result.correct = true;
            
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
            Usuario usuarioJPA = new Usuario();
            usuarioJPA.Direcciones = usuarioBD.Direcciones;
            entityManager.merge(usuarioJPA);
            
            result.correct = true;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }       
        return result;
    }
}
