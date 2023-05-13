package com.driagon.microservicios.cursos.app.services;

import com.driagon.microservicios.commons.app.services.CommonServiceImpl;
import com.driagon.microservicios.cursos.app.adapters.IRespuestaFeignClient;
import com.driagon.microservicios.cursos.app.models.Curso;
import com.driagon.microservicios.cursos.app.repositories.ICursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, ICursoRepository> implements ICursoService {

    @Autowired
    private IRespuestaFeignClient client;

    @Override
    @Transactional(readOnly = true)
    public Curso findCursoByAlumnoId(Long id) {
        return this.repository.findCursoByAlumnoId(id);
    }

    @Override
    public Iterable<Long> obtenerExamenesIdConRespuestasAlumno(Long alumnoId) {
        return this.client.obtenerExamenesIdConRespuestasAlumno(alumnoId);
    }
}