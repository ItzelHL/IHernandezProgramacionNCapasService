package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Result;

public interface IEstadoJPADAO 
{
    Result EstadoByPaisGetAll(int IdPais);
}
