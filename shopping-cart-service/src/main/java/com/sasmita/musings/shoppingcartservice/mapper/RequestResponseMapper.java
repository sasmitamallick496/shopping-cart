package com.sasmita.musings.shoppingcartservice.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.sasmita.musings.shoppingcartservice.entity.Cart;
import com.sasmita.musings.shoppingcartservice.model.ShoppingCartRequest;
import com.sasmita.musings.shoppingcartservice.model.ShoppingCartResponse;

@Component
@Mapper(componentModel = "spring")
public interface RequestResponseMapper {
	Cart requestToCart(ShoppingCartRequest request);
	ShoppingCartResponse cartToResponse(Cart cart);
}
