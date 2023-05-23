package com.driagon.microservicios.respuestas.app.services;

import com.driagon.microservicios.respuestas.app.adapters.IExamenFeignClient;
import com.driagon.microservicios.respuestas.app.models.Respuesta;
import com.driagon.microservicios.respuestas.app.repositories.IRespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespuestaServiceImpl implements IRespuestaService {

    @Autowired
    private IRespuestaRepository repository;

    @Autowired
    private IExamenFeignClient client;

    @Override
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
        return this.repository.saveAll(respuestas);
    }

    @Override
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
        List<Respuesta> respuestas = (List<Respuesta>) this.repository.findRespuestaByAlumnoByExamen(alumnoId, examenId);

        return respuestas;
    }

    @Override
    public Iterable<Long> findExamenesIdsConRespuestaByAlumno(Long alumnoId) {
        List<Respuesta> respuestasAlumno = (List<Respuesta>) this.repository.findExamenesIdsConRespuestasByAlumno(alumnoId);
        List<Long> examenIds = respuestasAlumno.stream().map(r -> r.getPregunta().getExamen().getId()).distinct().collect(Collectors.toList());

        return examenIds;
    }

    @Override
    public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
        return this.repository.findByAlumnoId(alumnoId);
    }
}