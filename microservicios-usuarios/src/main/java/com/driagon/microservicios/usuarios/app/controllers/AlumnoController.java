package com.driagon.microservicios.usuarios.app.controllers;

import com.driagon.microservicios.usuarios.app.models.Alumno;
import com.driagon.microservicios.usuarios.app.services.IAlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    @Autowired
    private IAlumnoService service;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok().body(this.service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {

        Optional<Alumno> o = this.service.findById(id);

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(o.get());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Alumno alumno) {
        Alumno alumnoDb = this.service.save(alumno);

        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoDb);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        this.service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}