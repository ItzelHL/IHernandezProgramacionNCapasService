package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.Model.Rol;
import com.digis01.IHernandezProgramacionNCapas.Model.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolJPADAOImplementation implements IRolJPADAO
{
    @Autowired
    EntityManager entityManager;

    @Override
    public Result GetAll()
    {
        Result result = new Result();
        
        try 
        {
            TypedQuery<Rol> queryRol = entityManager.createQuery("FROM Rol ORDER BY IdRol", Rol.class);
            result.object = queryRol.getResultList();
            
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
}