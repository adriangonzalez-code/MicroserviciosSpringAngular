package com.driagon.microservicios.examenes.app.services;

import com.driagon.common.examenes.app.models.Asignatura;
import com.driagon.common.examenes.app.models.Examen;
import com.driagon.microservicios.commons.app.services.CommonServiceImpl;
import com.driagon.microservicios.examenes.app.repositories.IAsignaturaRepository;
import com.driagon.microservicios.examenes.app.repositories.IExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, IExamenRepository> implements IExamenService {

    @Autowired
    private IAsignaturaRepository asignaturaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Examen> findByNombre(String term) {
        return this.repository.findByNombre(term);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Asignatura> findAllAsignaturas() {
        return this.asignaturaRepository.findAll();
    }
}