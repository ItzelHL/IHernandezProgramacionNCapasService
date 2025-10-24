package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.Model.Result;
import com.digis01.IHernandezProgramacionNCapas.Model.Usuario;
import org.springframework.web.multipart.MultipartFile;

public interface IUsuarioJPADAO 
{
    Result GetAll();
    
    Result Add(Usuario usuario);
    
    Result Delete(int idUsuario);
    
    Result GetById(int idUsuario);
    
    Result Update(Usuario usuario);
    
    Result UpdateStatus(Usuario usuario);
    
    Result CargarArchivo(MultipartFile file);
    
    Result ProcesarArchivo(String rutaCifrada);
}
