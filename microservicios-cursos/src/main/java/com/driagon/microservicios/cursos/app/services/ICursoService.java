package com.driagon.microservicios.cursos.app.services;

import com.driagon.microservicios.commons.app.services.ICommonService;
import com.driagon.microservicios.cursos.app.models.Curso;

public interface ICursoService extends ICommonService<Curso> {

    public Curso findCursoByAlumnoId(Long id);
}