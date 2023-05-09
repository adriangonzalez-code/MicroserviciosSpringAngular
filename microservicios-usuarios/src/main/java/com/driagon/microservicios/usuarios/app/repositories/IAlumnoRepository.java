package com.driagon.microservicios.usuarios.app.repositories;

import com.driagon.microservicios.usuarios.app.models.Alumno;
import org.springframework.data.repository.CrudRepository;

public interface IAlumnoRepository extends CrudRepository<Alumno, Long> {
}