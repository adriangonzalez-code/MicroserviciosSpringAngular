package com.driagon.microservicios.usuarios.app.controllers;

import com.driagon.commons.alumnos.app.models.Alumno;
import com.driagon.microservicios.commons.app.controllers.CommonController;
import com.driagon.microservicios.usuarios.app.services.IAlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AlumnoController extends CommonController<Alumno, IAlumnoService> {

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Alumno alumno, @PathVariable Long id) {

        Optional<Alumno> o = this.service.findById(id);

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Alumno alumnoDb = o.get();
        alumnoDb.setNombre(alumno.getNombre());
        alumnoDb.setApellido(alumno.getApellido());
        alumnoDb.setEmail(alumno.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(alumnoDb));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable String term) {
        return ResponseEntity.ok(this.service.findByNombreOrApellido(term));
    }
}