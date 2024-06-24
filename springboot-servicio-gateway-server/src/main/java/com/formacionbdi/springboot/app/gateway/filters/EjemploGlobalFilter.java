package com.formacionbdi.springboot.app.gateway.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
// import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class EjemploGlobalFilter implements GlobalFilter, Ordered{

	private final Logger logger = LoggerFactory.getLogger(EjemploGlobalFilter.class);

	// Método que implementa la lógica del filtro global
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("ejecutando filtro pre");
		// Modificar la solicitud añadiendo un encabezado 'token'
		exchange.getRequest().mutate().headers(h -> h.add("token", "123456"));

		// Llamar al siguiente filtro en la cadena y después ejecutar el código post-procesamiento
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			logger.info("ejecutando filtro post");

			// Verificar si existe el encabezado 'token' en la solicitud y copiarlo a la respuesta
			Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(valor -> {
				exchange.getResponse().getHeaders().add("token", valor);
			});
			// Agregar una cookie 'color' a la respuesta
			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "rojo").build());
			// Ejemplo adicional: configurar el tipo de contenido de la respuesta
			// exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
		}));
	}
	// Método que devuelve el orden de ejecución del filtro
	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 1; // Orden de prioridad del filtro
	}

}
