package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Result;

public interface IEstadoJPADAO 
{
    Result GetAll();
    
    Result EstadoGetByPais(int IdPais);
}
