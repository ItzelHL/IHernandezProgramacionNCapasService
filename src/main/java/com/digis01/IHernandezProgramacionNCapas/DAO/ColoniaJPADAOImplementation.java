package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Colonia;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaJPADAOImplementation implements IColoniaJPADAO
{
    @Autowired
    EntityManager entityManager;
    
    @Override
    public Result ColoniaByMunicipioGetAll(int IdMunicipio)
    {
        Result result = new Result();
        
        try 
        {
            TypedQuery<Colonia> queryColonia = entityManager.createQuery("FROM Colonia WHERE Municipio.IdMunicipio = :IdMunicipio ORDER BY IdColonia", Colonia.class);
            queryColonia.setParameter("IdMunicipio", IdMunicipio);
            result.object = queryColonia.getResultList();
            
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
