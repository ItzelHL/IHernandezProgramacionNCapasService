package com.digis01.IHernandezProgramacionNCapas.Repository;

import com.digis01.IHernandezProgramacionNCapas.Model.Municipio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryMunicipio extends JpaRepository<Municipio, Integer>
{
    List<Municipio> findByEstado_IdEstado(int idEstado);
}
