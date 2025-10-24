package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.Model.Estado;
import com.digis01.IHernandezProgramacionNCapas.Model.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoJPADAOImplementation implements IEstadoJPADAO
{
    @Autowired
    EntityManager entityManager;

    @Override
    public Result GetAll() 
    {
        Result result = new Result();
        try 
        {
            TypedQuery<Estado> queryEstado = entityManager.createQuery("FROM Estado ORDER BY IdEstado", Estado.class);
            result.object = queryEstado.getResultList();
            
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
    public Result EstadoGetByPais(int IdPais)
    {
        Result result = new Result();
        
        try 
        {
            TypedQuery<Estado> queryEstado = entityManager.createQuery("FROM Estado WHERE Pais.IdPais = :IdPais ORDER BY IdEstado", Estado.class);
            queryEstado.setParameter("IdPais", IdPais);
            result.object = queryEstado.getResultList();
            
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
}
