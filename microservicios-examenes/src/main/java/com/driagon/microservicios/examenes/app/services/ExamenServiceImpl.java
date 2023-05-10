package com.driagon.microservicios.examenes.app.services;

import com.driagon.microservicios.commons.app.services.CommonServiceImpl;
import com.driagon.microservicios.examenes.app.models.Examen;
import com.driagon.microservicios.examenes.app.repositories.IExamenRepository;
import org.springframework.stereotype.Service;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, IExamenRepository> implements IExamenService {
}