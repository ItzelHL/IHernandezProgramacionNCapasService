package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.JPA.Colonia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryColonia extends JpaRepository<Colonia, Integer>
{
    List<Colonia> findByMunicipio_IdMunicipio(int idMunicipio);
}
