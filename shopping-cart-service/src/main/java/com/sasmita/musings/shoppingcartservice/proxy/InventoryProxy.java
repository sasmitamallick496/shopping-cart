package com.sasmita.musings.shoppingcartservice.proxy;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sasmita.musings.shoppingcartservice.exception.InventoryServiceNotAvailableException;
import com.sasmita.musings.shoppingcartservice.model.InventoryInfo;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import static com.sasmita.musings.shoppingcartservice.util.ShoppingCartConstants.*;

@FeignClient(name = "inventory-service")
public interface InventoryProxy {

	@GetMapping("/inventory-service/{productId}")
	@Bulkhead(name = "InventoryService", fallbackMethod = "bulkHeadFallBack")
	@CircuitBreaker(name = "InventoryService", fallbackMethod = "circuitBreakerFallBack")
	public InventoryInfo retrieveInventoryById(@PathVariable("productId") String productId) throws Exception;

	@PostMapping("/inventory-service/reserve/{orderId}/{productId}/{quantity}")
	public Boolean reserveInventory(@PathVariable("orderId") UUID orderId, @PathVariable("productId") String productId,
			@PathVariable("quantity") int quantity);

	@PostMapping("/inventory-service/unreserve/{orderId}/{productId}/{quantity}")
	public Boolean unReserveInventory(@PathVariable("orderId") UUID orderId,
			@PathVariable("productId") String productId, @PathVariable("quantity") int quantity);

	default InventoryInfo bulkHeadFallBack(Throwable t) throws Exception {
		throw new InventoryServiceNotAvailableException(INVENTORY_SERVICE_UNAVAILBALE_EXCEPTION_MSG);
	}

	default InventoryInfo circuitBreakerFallBack(Throwable t) {
		InventoryInfo inventoryInfo = new InventoryInfo();
		inventoryInfo.setAvailable(true);
		return inventoryInfo;
	}

}
