package com.driagon.microservicios.respuestas.app.services;

import com.driagon.microservicios.respuestas.app.models.Respuesta;

public interface IRespuestaService {

    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);

    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);

    public Iterable<Long> findExamenesIdsConRespuestaByAlumno(Long alumnoId);
}