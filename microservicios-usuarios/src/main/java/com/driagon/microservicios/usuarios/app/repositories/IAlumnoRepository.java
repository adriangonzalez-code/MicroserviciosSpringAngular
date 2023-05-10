package com.driagon.microservicios.usuarios.app.repositories;

import com.driagon.commons.alumnos.app.models.Alumno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IAlumnoRepository extends CrudRepository<Alumno, Long> {

    @Query("select a from Alumno a where a.nombre like %?1% or a.apellido like %?1%")
    public List<Alumno> findByNombreOrApellido(String term);
}