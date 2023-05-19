package com.driagon.microservicios.cursos.app.services;

import com.driagon.commons.alumnos.app.models.Alumno;
import com.driagon.microservicios.commons.app.services.CommonServiceImpl;
import com.driagon.microservicios.cursos.app.adapters.IAlumnoFeignClient;
import com.driagon.microservicios.cursos.app.adapters.IRespuestaFeignClient;
import com.driagon.microservicios.cursos.app.models.Curso;
import com.driagon.microservicios.cursos.app.repositories.ICursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, ICursoRepository> implements ICursoService {

    @Autowired
    private IRespuestaFeignClient client;

    @Autowired
    private IAlumnoFeignClient clientAlumno;

    @Override
    @Transactional(readOnly = true)
    public Curso findCursoByAlumnoId(Long id) {
        return this.repository.findCursoByAlumnoId(id);
    }

    @Override
    public Iterable<Long> obtenerExamenesIdConRespuestasAlumno(Long alumnoId) {
        return this.client.obtenerExamenesIdConRespuestasAlumno(alumnoId);
    }

    @Override
    public Iterable<Alumno> obtenerAlumnoPorCurso(List<Long> ids) {
        return this.clientAlumno.obtenerAlumnoPorCurso(ids);
    }

    @Override
    @Transactional
    public void eliminarCursoAlumnoPorId(Long id) {
        this.repository.eliminarCursoAlumnoPorId(id);
    }
}