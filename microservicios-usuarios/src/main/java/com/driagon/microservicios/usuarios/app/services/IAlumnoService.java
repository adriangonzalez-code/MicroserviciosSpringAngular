package com.driagon.microservicios.usuarios.app.services;

import com.driagon.microservicios.usuarios.app.models.Alumno;

import java.util.Optional;

public interface IAlumnoService {

    public Iterable<Alumno> findAll();

    public Optional<Alumno> findById(Long id);

    public Alumno save(Alumno alumno);

    public void deleteById(Long id);
}