package com.driagon.microservicios.examenes.app.controllers;

import com.driagon.microservicios.commons.app.controllers.CommonController;
import com.driagon.microservicios.examenes.app.models.Examen;
import com.driagon.microservicios.examenes.app.services.IExamenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ExamenController extends CommonController<Examen, IExamenService> {

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Examen examen, @PathVariable long id) {

        Optional<Examen> o = this.service.findById(id);

        if (!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Examen examenDb = o.get();
        examenDb.setNombre(examen.getNombre());

        examenDb.getPreguntas()
                .stream()
                .filter(pdb -> !examen.getPreguntas().contains(pdb))
                .forEach(examenDb::removePregunta);

        examenDb.setPreguntas(examen.getPreguntas());

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(examenDb));
    }
}