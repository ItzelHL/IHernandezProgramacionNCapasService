package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Direccion;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionJPADAOImplementation implements IDireccionJPADAO
{
    @Autowired
        private EntityManager entityManager;
    
    @Override
    public Result GetById(int IdDireccion) 
    {
        Result result = new Result();
        
        try 
        {
            Direccion direccion = entityManager.find(Direccion.class, IdDireccion);
            
            if (direccion != null) 
            {
                result.object = direccion;
                result.correct = true;
                result.status = 200;
            } else
            {
                result.errorMessage = "Direccion no encontrada, Id incorrecto";
                result.status = 400;
            }
            
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
    public Result AddDireccion(Direccion direccion)
    {
        Result result = new Result();
        
        try 
        {
            entityManager.persist(direccion);
            result.object = direccion;
            
            result.correct = true;
            result.status = 200;
            
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
    public Result UpdateDireccion(Direccion direccion) 
    {
        Result result = new Result();
        
        try 
        {
            Direccion direccionBD = entityManager.find(Direccion.class, direccion.getIdDireccion());
            
            if (direccionBD != null) 
            {
                direccionBD.setCalle(direccion.getCalle());
                direccionBD.setNumeroExterior(direccion.getNumeroExterior());
                direccionBD.setNumeroInterior(direccion.getNumeroInterior());                
                direccionBD.Colonia = direccion.Colonia;
                
                entityManager.merge(direccionBD);
                result.correct = true;
                result.object = direccionBD;
                result.status = 200;
            } else
            {
                result.errorMessage = "Direccion no encontrada, Id incorrecto";
                result.status = 400;
            }
            
            
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
    public Result Delete(int IdDireccion) 
    {
        Result result = new Result();
        
        try 
        {
            Direccion direccion = entityManager.find(Direccion.class, IdDireccion);
            
            if (direccion != null) 
            {
                entityManager.remove(direccion);
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
