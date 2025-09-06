package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Direccion;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;

public interface IDireccionJPADAO 
{
    public Result GetById(int IdDireccion);
            
    public Result AddDireccion(Direccion direccion);
    
    public Result Delete(int IdDireccion);
    
    public Result UpdateDireccion(Direccion direccion);
}
