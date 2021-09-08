package com.sasmita.musings.apigateway;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;

@Configuration
public class ApiGatewayConfiguration {

	/*
	 * @Bean public Customizer<Resilience4JCircuitBreakerFactory>
	 * defaultCustomizer() { return factory -> factory.configureDefault(id -> new
	 * Resilience4JConfigBuilder(id)
	 * .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.
	 * ofSeconds(4)).build())
	 * .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()) .build()); }
	 */

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("shopping-cart-service", p -> p.path("/cart/**").uri("lb://shopping-cart-service"))
				.route("inventory-service", p -> p.path("/inventory-service/**").uri("lb://inventory-service"))
				.route("price-service", p -> p.path("/price-service/**").uri("lb://price-service"))
				.route("payment-service", p -> p.path("/payment-service/**").uri("lb://payment-service")).build();
	}

	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.custom().slidingWindowSize(5)
						.permittedNumberOfCallsInHalfOpenState(5).failureRateThreshold(50.0F)
						.waitDurationInOpenState(Duration.ofMillis(50))
						.slowCallDurationThreshold(Duration.ofMillis(5000)).slowCallRateThreshold(50.0F).build())
				.build());
	}

}
