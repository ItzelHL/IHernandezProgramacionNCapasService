package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Pais;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaisJPADAOImplementation implements IPaisJPADAO
{
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result GetAllPais() 
    {
        Result result = new Result();
        
        try 
        {
            TypedQuery<Pais> queryPais = entityManager.createQuery("FROM Pais ORDER BY IdPais", Pais.class);
            result.object = queryPais.getResultList();
            
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
