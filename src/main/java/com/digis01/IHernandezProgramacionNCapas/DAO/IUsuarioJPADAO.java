package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;

public interface IUsuarioJPADAO 
{
    Result GetAll();
    
    Result Add(Usuario usuario);
    
    Result Delete(int idUsuario);
    
    Result GetById(int idUsuario);
    
    Result Update(Usuario usuario);
    
    Result UpdateStatus(Usuario usuario);
}
