package com.driagon.microservicios.cursos.app.controllers;

import com.driagon.commons.alumnos.app.models.Alumno;
import com.driagon.microservicios.commons.app.controllers.CommonController;
import com.driagon.microservicios.cursos.app.models.Curso;
import com.driagon.microservicios.cursos.app.services.ICursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CursoController extends CommonController<Curso, ICursoService> {

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id) {
        Optional<Curso> o = this.service.findById(id);

        if (!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Curso dbCurso = o.get();
        dbCurso.setNombre(curso.getNombre());

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

    @PutMapping("/{id}/asignar-alumnos")
    public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
        Optional<Curso> o = this.service.findById(id);

        if (!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Curso dbCurso = o.get();
        alumnos.forEach(a -> {
            dbCurso.addAlumno(a);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

    @PutMapping("/{id}/eliminar-alumno")
    public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id) {
        Optional<Curso> o = this.service.findById(id);

        if (!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Curso dbCurso = o.get();

        dbCurso.removeAlumno(alumno);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<?> buscarPorAlumno(@PathVariable Long id) {

        Curso curso = this.service.findCursoByAlumnoId(id);
        return ResponseEntity.ok(curso);
    }
}