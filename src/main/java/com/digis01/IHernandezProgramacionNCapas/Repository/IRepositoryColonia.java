package com.digis01.IHernandezProgramacionNCapas.Repository;

import com.digis01.IHernandezProgramacionNCapas.Model.Colonia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryColonia extends JpaRepository<Colonia, Integer>
{
    List<Colonia> findByMunicipio_IdMunicipio(int idMunicipio);
}
