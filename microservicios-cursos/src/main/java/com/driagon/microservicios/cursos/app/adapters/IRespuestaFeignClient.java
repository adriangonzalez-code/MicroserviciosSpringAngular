package com.driagon.microservicios.cursos.app.adapters;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-respuestas")
public interface IRespuestaFeignClient {

    @GetMapping("/alumno/{alumnoId}/examenes-respondidos")
    public Iterable<Long> obtenerExamenesIdConRespuestasAlumno(@PathVariable Long alumnoId);
}