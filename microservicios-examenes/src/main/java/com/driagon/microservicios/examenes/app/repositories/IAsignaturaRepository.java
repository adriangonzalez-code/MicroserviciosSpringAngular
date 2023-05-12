package com.driagon.microservicios.examenes.app.repositories;

import com.driagon.common.examenes.app.models.Asignatura;
import org.springframework.data.repository.CrudRepository;

public interface IAsignaturaRepository extends CrudRepository<Asignatura, Long> {
}