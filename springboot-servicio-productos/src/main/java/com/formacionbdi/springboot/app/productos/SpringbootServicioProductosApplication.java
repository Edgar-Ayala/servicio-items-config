package com.formacionbdi.springboot.app.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

// Habilita este servicio como cliente de Eureka
@EnableEurekaClient
// Anotación principal de Spring Boot que configura la aplicación
@SpringBootApplication
public class SpringbootServicioProductosApplication {
	// Método principal que inicia la aplicación Spring Boot
	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioProductosApplication.class, args);
	}

}
