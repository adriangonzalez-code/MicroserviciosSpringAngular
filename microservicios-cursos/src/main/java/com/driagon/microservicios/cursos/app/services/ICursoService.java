package com.driagon.microservicios.cursos.app.services;

import com.driagon.commons.alumnos.app.models.Alumno;
import com.driagon.microservicios.commons.app.services.ICommonService;
import com.driagon.microservicios.cursos.app.models.Curso;

import java.util.List;

public interface ICursoService extends ICommonService<Curso> {

    public Curso findCursoByAlumnoId(Long id);

    public Iterable<Long> obtenerExamenesIdConRespuestasAlumno(Long alumnoId);

    public Iterable<Alumno> obtenerAlumnoPorCurso(List<Long> ids);

    public void eliminarCursoAlumnoPorId(Long id);
}