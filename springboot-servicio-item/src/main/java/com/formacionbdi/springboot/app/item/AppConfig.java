package com.formacionbdi.springboot.app.item;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppConfig {

	// Configuración de RestTemplate como cliente HTTP con balanceo de carga
	@Bean("clienteRest")
	@LoadBalanced
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}

	// Configuración personalizada para Resilience4J CircuitBreakerFactory
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(){
		// Configuración del Circuit Breaker
		return factory -> factory.configureDefault(id -> {
			return new Resilience4JConfigBuilder(id)
					// Tamaño de la ventana deslizante para estadísticas
					.circuitBreakerConfig(CircuitBreakerConfig.custom()
							// Umbral de tasa de fallos para abrir el circuito
							.slidingWindowSize(10)
							// Duración de espera en estado abierto antes de intentar de nuevo
							.failureRateThreshold(50)
							// Número permitido de llamadas en estado semiabierto
							.waitDurationInOpenState(Duration.ofSeconds(10L))
							// Umbral de tasa de llamadas lentas
							.permittedNumberOfCallsInHalfOpenState(5)
							// Umbral de duración de llamadas lentas
							.slowCallRateThreshold(50)
							// Configuración del Time Limiter para establecer límites de tiempo
							.slowCallDurationThreshold(Duration.ofSeconds(2L))
							.build())
					.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3L)).build())
					.build();
		});
	}
}
