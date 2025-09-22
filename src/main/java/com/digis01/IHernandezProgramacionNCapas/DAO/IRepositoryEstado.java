package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Estado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryEstado extends JpaRepository<Estado, Integer>
{
    List<Estado> findByPais_IdPais(int idPais);
}
