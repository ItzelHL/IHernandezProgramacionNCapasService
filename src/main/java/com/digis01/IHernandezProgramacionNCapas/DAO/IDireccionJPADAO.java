package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;

public interface IDireccionJPADAO 
{
    public Result AddDireccion(int IdDireccion);
    
    public Result UpdateDireccion(Usuario usuario);
    
    public Result Delete(int IdDireccion);
}
