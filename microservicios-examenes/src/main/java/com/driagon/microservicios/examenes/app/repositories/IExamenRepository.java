package com.driagon.microservicios.examenes.app.repositories;

import com.driagon.common.examenes.app.models.Examen;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IExamenRepository extends PagingAndSortingRepository<Examen, Long> {

    @Query("SELECT e FROM Examen e WHERE e.nombre LIKE %?1%")
    public List<Examen> findByNombre(String term);
}