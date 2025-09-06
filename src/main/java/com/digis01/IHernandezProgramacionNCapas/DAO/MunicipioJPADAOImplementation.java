package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Municipio;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioJPADAOImplementation implements IMunicipioJPADAO
{
    @Autowired
    EntityManager entityManager;

    @Override
    public Result MunicipioByEstadoGetAll(int IdEstado)
    {
        Result result = new Result();
        
        try 
        {
            TypedQuery<Municipio> queryMuncipio = entityManager.createQuery("FROM Municipio WHERE Estado.IdEstado = :IdEstado ORDER BY IdMunicipio", Municipio.class);
            queryMuncipio.setParameter("IdEstado", IdEstado);
            result.object = queryMuncipio.getResultList();
                    
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
