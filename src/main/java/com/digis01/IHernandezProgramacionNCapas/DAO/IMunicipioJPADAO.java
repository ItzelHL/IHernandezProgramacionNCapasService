package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Result;

public interface IMunicipioJPADAO 
{
    Result MunicipioByEstadoGetAll(int idEstado);
}
