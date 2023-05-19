package com.driagon.microservicios.cursos.app.controllers;

import com.driagon.common.examenes.app.models.Examen;
import com.driagon.commons.alumnos.app.models.Alumno;
import com.driagon.microservicios.commons.app.controllers.CommonController;
import com.driagon.microservicios.cursos.app.models.Curso;
import com.driagon.microservicios.cursos.app.models.CursoAlumno;
import com.driagon.microservicios.cursos.app.services.ICursoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CursoController extends CommonController<Curso, ICursoService> {

    @Value("${config.balanceador.test}")
    private String balanceadorTest;

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
            CursoAlumno cursoAlumno = new CursoAlumno();
            cursoAlumno.setAlumnoId(a.getId());
            cursoAlumno.setCurso(dbCurso);
            dbCurso.addCursoAlumno(cursoAlumno);
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
        CursoAlumno cursoAlumno = new CursoAlumno();
        cursoAlumno.setAlumnoId(alumno.getId());

        dbCurso.removeCursoAlumno(cursoAlumno);

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

    @GetMapping("/balanceador-test")
    public ResponseEntity<?> balanceadorTest() {

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("balanceador", balanceadorTest);
        response.put("cursos", this.service.findAll());
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<?> listar() {
        List<Curso> cursos = ((List<Curso>) this.service.findAll()).stream().map(c -> {
            c.getCursoAlumnos().forEach(ca -> {
                Alumno alumno = new Alumno();
                alumno.setId(ca.getAlumnoId());
                c.addAlumno(alumno);
            });

            return c;
        }).collect(Collectors.toList());

        return ResponseEntity.ok().body(cursos);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {

        Optional<Curso> o = this.service.findById(id);

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso curso = o.get();

        if (!curso.getCursoAlumnos().isEmpty()) {
            List<Long> ids = curso.getCursoAlumnos().stream().map(CursoAlumno::getAlumnoId).collect(Collectors.toList());

            List<Alumno> alumnos = (List<Alumno>)this.service.obtenerAlumnoPorCurso(ids);
            curso.setAlumnos(alumnos);
        }

        return ResponseEntity.ok().body(curso);
    }

    @Override
    @GetMapping("/pagina")
    public ResponseEntity<?> listar(Pageable pageable) {

        Page<Curso> cursos = this.service.findAll(pageable).map(curso -> {
            curso.getCursoAlumnos().forEach(ca -> {
                Alumno alumno = new Alumno();
                alumno.setId(ca.getAlumnoId());
                curso.addAlumno(alumno);
            });

            return curso;
        });

        return ResponseEntity.ok().body(cursos);
    }

    @DeleteMapping("/eliminar-alumno/{id}")
    public ResponseEntity<?> eliminarCursoAlumnoPorId(@PathVariable Long id) {
        this.service.eliminarCursoAlumnoPorId(id);
        return ResponseEntity.noContent().build();
    }
}