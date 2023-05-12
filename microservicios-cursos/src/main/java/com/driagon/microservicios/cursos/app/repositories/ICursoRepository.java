package com.driagon.microservicios.cursos.app.repositories;

import com.driagon.microservicios.cursos.app.models.Curso;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICursoRepository extends PagingAndSortingRepository<Curso, Long> {

    @Query("select c from Curso c join fetch c.alumnos a where a.id = ?1")
    public Curso findCursoByAlumnoId(Long id);
}