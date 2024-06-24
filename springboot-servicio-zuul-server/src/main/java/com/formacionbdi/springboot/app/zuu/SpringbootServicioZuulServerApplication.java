package com.formacionbdi.springboot.app.zuu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

//Anotacion para habilitar este servicio como cliente de Eureka para el registro en el servidor Eureka.
@EnableEurekaClient
//Anotacion para habilitar Zuul como proxy en esta aplicacion.
@EnableZuulProxy
//Anotacion que combina @Configuration, @EnableAutoConfiguration y @ComponentScan.
//Configura la aplicacion Spring Boot automaticamente basada en el contenido de la clase y sus dependencias.
@SpringBootApplication
public class SpringbootServicioZuulServerApplication {
	//Metodo principal que inicia la aplicacion Spring Boot.
	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioZuulServerApplication.class, args);
	}

}
