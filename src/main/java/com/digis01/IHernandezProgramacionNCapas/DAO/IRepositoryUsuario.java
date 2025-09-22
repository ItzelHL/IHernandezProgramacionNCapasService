package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryUsuario extends JpaRepository<Usuario, Integer> 
{
    
}
