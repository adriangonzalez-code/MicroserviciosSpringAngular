package com.driagon.microservicios.respuestas.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.driagon.microservicios.respuestas.app",
        //"com.driagon.commons.alumnos.app.models",
        "com.driagon.common.examenes.app.models"})
public class MicroserviciosRespuestasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviciosRespuestasApplication.class, args);
    }

}