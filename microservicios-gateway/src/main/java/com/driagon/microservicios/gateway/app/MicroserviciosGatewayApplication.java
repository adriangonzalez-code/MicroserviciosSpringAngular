package com.driagon.microservicios.gateway.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MicroserviciosGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosGatewayApplication.class, args);
	}

}
