package com.driagon.microservicios.commons.app.controllers;

import com.driagon.microservicios.commons.app.services.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

public class CommonController<E, S extends ICommonService<E>> {

    @Autowired
    protected S service;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok().body(this.service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {

        Optional<E> o = this.service.findById(id);

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(o.get());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody E entity) {
        E entityDb = this.service.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        this.service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}