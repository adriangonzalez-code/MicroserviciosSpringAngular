package com.driagon.microservicios.respuestas.app.services;

import com.driagon.microservicios.respuestas.app.models.Respuesta;
import com.driagon.microservicios.respuestas.app.repositories.IRespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RespuestaServiceImpl implements IRespuestaService {

    @Autowired
    private IRespuestaRepository repository;

    @Override
    @Transactional
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
        return this.repository.saveAll(respuestas);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
        return this.repository.findRespuestaByAlumnoByExamen(alumnoId, examenId);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Long> findExamenesIdsConRespuestaByAlumno(Long alumnoId) {
        return this.repository.findExamenesIdsConRespuestaByAlumno(alumnoId);
    }
}