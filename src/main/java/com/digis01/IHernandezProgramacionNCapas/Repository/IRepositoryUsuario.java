package com.digis01.IHernandezProgramacionNCapas.Repository;

import com.digis01.IHernandezProgramacionNCapas.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryUsuario extends JpaRepository<Usuario, Integer> 
{
    Usuario findByUsername(String username);
    Usuario findByEmail(String email);
    Usuario findByToken(String token);
}