package com.formacionbdi.springboot.app.gateway.filters.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
// import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class EjemploGatewayFilterFactory extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion>{

	private final Logger logger = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);

	// Constructor de la clase que llama al constructor de la superclase AbstractGatewayFilterFactory
	public EjemploGatewayFilterFactory() {
		super(Configuracion.class);
	}

	// Método que aplica el filtro de la fábrica
	@Override
	public GatewayFilter apply(Configuracion config) {
		return (exchange, chain) -> {
			// Lógica a ejecutar antes de pasar la solicitud al siguiente filtro o destino final
			logger.info("ejecutando pre gateway filter factory: " + config.mensaje);
			// Llamar al siguiente filtro en la cadena y después ejecutar el código post-procesamiento
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				// Lógica a ejecutar después de que la solicitud haya sido manejada
				Optional.ofNullable(config.cookieValor).ifPresent(cookie -> {
					// Añadir una cookie a la respuesta si config.cookieValor no es nulo
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, cookie).build());
				});
				// Registro de información adicional después de procesar la solicitud
				logger.info("ejecutando post gateway filter factory: " + config.mensaje);
				
			}));
		};
	}

	// Método que devuelve el nombre del filtro de la fábrica
	@Override
	public String name() {
		return "EjemploCookie";
	}

	// Clase estática que define la configuración esperada por la fábrica
	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("mensaje", "cookieNombre", "cookieValor");
	}

	// Getters y setters para los campos de configuración
	public static class Configuracion {

		private String mensaje;
		private String cookieValor;
		private String cookieNombre;
		public String getMensaje() {
			return mensaje;
		}
		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}
		public String getCookieValor() {
			return cookieValor;
		}
		public void setCookieValor(String cookieValor) {
			this.cookieValor = cookieValor;
		}
		public String getCookieNombre() {
			return cookieNombre;
		}
		public void setCookieNombre(String cookieNombre) {
			this.cookieNombre = cookieNombre;
		}
		
		
	}

}
