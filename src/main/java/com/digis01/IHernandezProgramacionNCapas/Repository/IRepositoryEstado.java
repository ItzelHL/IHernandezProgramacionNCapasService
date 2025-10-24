package com.digis01.IHernandezProgramacionNCapas.Repository;

import com.digis01.IHernandezProgramacionNCapas.Model.Estado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryEstado extends JpaRepository<Estado, Integer>
{
    List<Estado> findByPais_IdPais(int idPais);
}
