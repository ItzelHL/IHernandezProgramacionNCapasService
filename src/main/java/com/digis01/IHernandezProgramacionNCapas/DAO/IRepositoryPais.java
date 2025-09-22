package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryPais extends JpaRepository<Pais, Integer>
{
    
}
