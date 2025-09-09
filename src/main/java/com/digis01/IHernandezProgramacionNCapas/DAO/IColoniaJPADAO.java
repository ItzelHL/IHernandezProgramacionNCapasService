package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Result;

public interface IColoniaJPADAO 
{
    Result GetAll();
    
    Result ColoniaGetByMunicipio(int idMunicipio);
}
