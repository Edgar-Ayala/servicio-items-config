package com.formacionbdi.springboot.app.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient // Habilita este servicio como cliente de Eureka
@EnableFeignClients // Habilita el uso de Feign para clientes HTTP
@SpringBootApplication // Anotación principal de Spring Boot que habilita la configuración automática y el escaneo de componentes
public class SpringbootServicioItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioItemApplication.class, args);// Método principal que inicia la aplicación Spring Boot
	}
	}


