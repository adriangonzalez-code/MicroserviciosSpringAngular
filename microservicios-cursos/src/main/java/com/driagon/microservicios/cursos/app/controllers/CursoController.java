package com.driagon.microservicios.cursos.app.controllers;

import com.driagon.common.examenes.app.models.Examen;
import com.driagon.commons.alumnos.app.models.Alumno;
import com.driagon.microservicios.commons.app.controllers.CommonController;
import com.driagon.microservicios.cursos.app.models.Curso;
import com.driagon.microservicios.cursos.app.services.ICursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CursoController extends CommonController<Curso, ICursoService> {

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return super.validar(result);
        }

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
        if (curso != null) {
            List<Long> examenesIds = (List<Long>) this.service.obtenerExamenesIdConRespuestasAlumno(id);

            List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
                if (examenesIds.contains(examen.getId())) {
                    examen.setRespondido(true);
                }

                return examen;
            }).collect(Collectors.toList());

            curso.setExamenes(examenes);
        }

        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}/asignar-examenes")
    public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id) {
        Optional<Curso> o = this.service.findById(id);

        if (!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Curso dbCurso = o.get();
        examenes.forEach(e -> {
            dbCurso.addExamen(e);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

    @PutMapping("/{id}/eliminar-examen")
    public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id) {
        Optional<Curso> o = this.service.findById(id);

        if (!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Curso dbCurso = o.get();

        dbCurso.removeExamen(examen);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }
}