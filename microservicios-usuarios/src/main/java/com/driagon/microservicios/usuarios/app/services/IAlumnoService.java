package com.driagon.microservicios.usuarios.app.services;

import com.driagon.commons.alumnos.app.models.Alumno;
import com.driagon.microservicios.commons.app.services.ICommonService;

import java.util.List;

public interface IAlumnoService extends ICommonService<Alumno> {

    public List<Alumno> findByNombreOrApellido(String term);
}