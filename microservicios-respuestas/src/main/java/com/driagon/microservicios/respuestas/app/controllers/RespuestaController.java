package com.driagon.microservicios.respuestas.app.controllers;

import com.driagon.microservicios.respuestas.app.models.Respuesta;
import com.driagon.microservicios.respuestas.app.services.IRespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RespuestaController {

    @Autowired
    private IRespuestaService service;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Iterable<Respuesta> respuestas) {
        Iterable<Respuesta> respuestasDb = this.service.saveAll(respuestas);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestasDb);
    }

    @GetMapping("/alumno/{alumnoId}/examen/{examenId}")
    public ResponseEntity<?> obtenerRespuestaPorAlumnoPorExamen(@PathVariable Long alumnoId, @PathVariable Long examenId) {
        Iterable<Respuesta> respuestas = this.service.findRespuestaByAlumnoByExamen(alumnoId, examenId);
        return ResponseEntity.ok(respuestas);
    }

    @GetMapping("/alumno/{alumnoId}/examenes-respondidos")
    public ResponseEntity<?> obtenerExamenesIdConRespuestasAlumno(@PathVariable Long alumnoId) {
        Iterable<Long> examenIds = this.service.findExamenesIdsConRespuestaByAlumno(alumnoId);
        return ResponseEntity.ok(examenIds);
    }
}