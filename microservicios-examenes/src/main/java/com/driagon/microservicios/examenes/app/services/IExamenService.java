package com.driagon.microservicios.examenes.app.services;

import com.driagon.common.examenes.app.models.Asignatura;
import com.driagon.common.examenes.app.models.Examen;
import com.driagon.microservicios.commons.app.services.ICommonService;

import java.util.List;

public interface IExamenService extends ICommonService<Examen> {

    public List<Examen> findByNombre(String term);

    Iterable<Asignatura> findAllAsignaturas();
}