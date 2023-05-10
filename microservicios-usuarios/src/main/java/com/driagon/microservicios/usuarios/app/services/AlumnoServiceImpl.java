package com.driagon.microservicios.usuarios.app.services;

import com.driagon.commons.alumnos.app.models.Alumno;
import com.driagon.microservicios.commons.app.services.CommonServiceImpl;
import com.driagon.microservicios.usuarios.app.repositories.IAlumnoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, IAlumnoRepository> implements IAlumnoService {


    @Override
    @Transactional(readOnly = true)
    public List<Alumno> findByNombreOrApellido(String term) {
        return this.repository.findByNombreOrApellido(term);
    }
}