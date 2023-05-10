package com.driagon.microservicios.commons.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class CommonServiceImpl<E, R extends CrudRepository<E, Long>> implements ICommonService<E> {

    @Autowired
    protected R repository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<E> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<E> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    @Transactional
    public E save(E entity) {
        return this.repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }
}