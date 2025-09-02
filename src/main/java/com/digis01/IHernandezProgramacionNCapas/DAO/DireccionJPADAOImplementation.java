package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Direccion;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionJPADAOImplementation implements IDireccionJPADAO
{
    @Autowired
        private EntityManager entityManager;
    
    @Transactional
    @Override
    public Result AddDireccion(int IdDireccion)
    {
        Result result = new Result();
        
        try 
        {
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
    @Transactional
    @Override
    public Result UpdateDireccion(Usuario usuario) 
    {
        Result result = new Result();
        
        try 
        {
            Direccion direccionJPA = new Direccion();
            entityManager.merge(direccionJPA);
            
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
    public Result Delete(int IdDireccion) 
    {
        Result result = new Result();
        
        try 
        {
            Direccion direccionJPA = entityManager.find(Direccion.class, IdDireccion);
            entityManager.remove(direccionJPA);
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
