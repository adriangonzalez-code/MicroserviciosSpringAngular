package com.driagon.microservicios.usuarios.app.repositories;

import com.driagon.commons.alumnos.app.models.Alumno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IAlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {

    @Query("select a from Alumno a where upper(a.nombre) like upper(concat('%', ?1 ,'%')) or upper(a.apellido) like upper(concat('%', ?1 ,'%'))")
    public List<Alumno> findByNombreOrApellido(String term);

    public Iterable<Alumno> findAllByOrderByIdAsc();

    public Page<Alumno> findAllByOrderByIdAsc(Pageable pageable);
}