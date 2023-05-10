package com.driagon.microservicios.examenes.app.repositories;

import com.driagon.microservicios.examenes.app.models.Examen;
import org.springframework.data.repository.CrudRepository;

public interface IExamenRepository extends CrudRepository<Examen, Long> {
}