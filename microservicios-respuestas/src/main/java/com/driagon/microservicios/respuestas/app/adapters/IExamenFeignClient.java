package com.driagon.microservicios.respuestas.app.adapters;

import com.driagon.common.examenes.app.models.Examen;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservicio-examenes")
public interface IExamenFeignClient {

    @GetMapping("/{id}")
    public Examen obtenerExamenPorId(@PathVariable Long id);

    @GetMapping("/respondidos-por-pregunta")
    public List<Long> obtenerExamenesIdsPorPreguntasIdsRespondidas(@RequestParam List<Long> preguntaIds);
}