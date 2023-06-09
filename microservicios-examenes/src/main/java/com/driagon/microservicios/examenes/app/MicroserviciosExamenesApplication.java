package com.driagon.microservicios.examenes.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.driagon.microservicios.examenes.app",
        "com.driagon.common.examenes.app.models"})
public class MicroserviciosExamenesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviciosExamenesApplication.class, args);
    }
}