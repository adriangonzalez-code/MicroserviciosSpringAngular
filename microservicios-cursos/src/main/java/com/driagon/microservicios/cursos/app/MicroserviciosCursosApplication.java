package com.driagon.microservicios.cursos.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({//"com.driagon.commons.alumnos.app.models",
        "com.driagon.microservicios.cursos.app",
        "com.driagon.common.examenes.app.models"})
public class MicroserviciosCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviciosCursosApplication.class, args);
    }

}
