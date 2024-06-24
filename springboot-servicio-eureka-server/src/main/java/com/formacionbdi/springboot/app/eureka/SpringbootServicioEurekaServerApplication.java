package com.formacionbdi.springboot.app.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// Anotación para habilitar este servicio como servidor Eureka.
@EnableEurekaServer
// Anotación que combina @Configuration, @EnableAutoConfiguration y @ComponentScan.
// Configura la aplicación Spring Boot automáticamente basada en el contenido de la clase y sus dependencias.
@SpringBootApplication
public class SpringbootServicioEurekaServerApplication {
	// Método principal que inicia la aplicación Spring Boot.
	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioEurekaServerApplication.class, args);
	}

}
