package com.driagon.microservicios.usuarios.app.services;

import com.driagon.commons.alumnos.app.models.Alumno;
import com.driagon.microservicios.commons.app.services.CommonServiceImpl;
import com.driagon.microservicios.usuarios.app.adapters.ICursoFeignClient;
import com.driagon.microservicios.usuarios.app.repositories.IAlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, IAlumnoRepository> implements IAlumnoService {

    @Autowired
    private ICursoFeignClient client;

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> findByNombreOrApellido(String term) {
        return this.repository.findByNombreOrApellido(term);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Alumno> findAllById(Iterable<Long> ids) {
        return this.repository.findAllById(ids);
    }

    @Override
    public void eliminarCursoAlumnoPorId(Long id) {
        this.client.eliminarCursoAlumnoPorId(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        super.deleteById(id);
        this.eliminarCursoAlumnoPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Alumno> findAll() {
        return this.repository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Alumno> findAll(Pageable pageable) {
        return this.repository.findAllByOrderByIdAsc(pageable);
    }
}