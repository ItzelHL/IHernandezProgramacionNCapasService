package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.Model.Direccion;
import com.digis01.IHernandezProgramacionNCapas.Model.Result;

public interface IDireccionJPADAO 
{
    public Result GetById(int IdDireccion);
            
    public Result AddDireccion(Direccion direccion);
    
    public Result Delete(int IdDireccion);
    
    public Result UpdateDireccion(Direccion direccion);
}
