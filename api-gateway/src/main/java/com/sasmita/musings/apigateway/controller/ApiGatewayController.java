package com.sasmita.musings.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sasmita.musings.apigateway.ErrorResponse;

@RestController
public class ApiGatewayController {
	
	@RequestMapping(value="/fallback", method={RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<Object> fallBackforShoppingcart(){
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
				.body(buildErrorResponse(HttpStatus.BAD_GATEWAY.value(),"ShoppingCart Service is Down",1111));
	}
	
	private ErrorResponse buildErrorResponse(Integer status, String message, Integer errorCode) {
		ErrorResponse errorResponse = ErrorResponse.builder().status(status)
															 .message(message)
															 .errorCode(errorCode)
															 .build();
		return errorResponse;
	}
	

}
