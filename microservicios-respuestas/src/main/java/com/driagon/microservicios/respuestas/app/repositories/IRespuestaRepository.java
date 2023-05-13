package com.driagon.microservicios.respuestas.app.repositories;

import com.driagon.microservicios.respuestas.app.models.Respuesta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IRespuestaRepository extends CrudRepository<Respuesta, Long> {

    @Query("SELECT r FROM Respuesta r join fetch r.alumno a join fetch r.pregunta p join fetch p.examen e WHERE a.id = ?1 AND e.id = ?2")
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);

    @Query("SELECT e.id FROM Respuesta r join r.alumno a join r.pregunta p join p.examen e WHERE a.id = ?1 GROUP BY e.id")
    public Iterable<Long> findExamenesIdsConRespuestaByAlumno(Long alumnoId);
}