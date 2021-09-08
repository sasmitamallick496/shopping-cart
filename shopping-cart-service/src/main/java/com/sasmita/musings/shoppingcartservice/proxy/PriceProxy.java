package com.sasmita.musings.shoppingcartservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sasmita.musings.shoppingcartservice.exception.PriceServiceNotAvailableException;
import com.sasmita.musings.shoppingcartservice.model.PriceInfo;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import static com.sasmita.musings.shoppingcartservice.util.ShoppingCartConstants.*;

@FeignClient(name = "price-service")
public interface PriceProxy {

	@GetMapping("/price-service/{productId}")
	@Bulkhead(name = "PriceService", fallbackMethod = "priceFallBack")
	@CircuitBreaker(name = "PriceService", fallbackMethod = "priceFallBack")
	public PriceInfo retrievePriceById(@PathVariable("productId") String productId) throws Exception;

	default PriceInfo priceFallBack(Throwable t) throws Exception {
		throw new PriceServiceNotAvailableException(PRICE_SERVICE_UNAVAILBALE_EXCEPTION_MSG);
	}

}
