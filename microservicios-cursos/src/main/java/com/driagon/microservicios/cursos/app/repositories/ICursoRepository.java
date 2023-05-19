package com.driagon.microservicios.cursos.app.repositories;

import com.driagon.microservicios.cursos.app.models.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICursoRepository extends PagingAndSortingRepository<Curso, Long> {

    @Query("select c from Curso c join fetch c.cursoAlumnos ca where ca.alumnoId = ?1")
    public Curso findCursoByAlumnoId(Long id);

    @Modifying
    @Query("delete from CursoAlumno ca where ca.alumnoId = ?1")
    public void eliminarCursoAlumnoPorId(Long id);
}