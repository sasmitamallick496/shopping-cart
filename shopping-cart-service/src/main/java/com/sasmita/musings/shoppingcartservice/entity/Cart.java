package com.sasmita.musings.shoppingcartservice.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cart {

	@Id
	@GeneratedValue
	private UUID cartId;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "cartitem_fCartid", referencedColumnName = "cartId")
	private List<Item> items;
	
	@Transient
	private BigDecimal orderTotal;
	
	private String status;

	public Cart() {
		super();
	}

	public Cart(UUID cartId, List<Item> items, BigDecimal orderTotal, String status) {
		super();
		this.cartId = cartId;
		this.items = items;
		this.orderTotal = orderTotal;
		this.status = status;
	}
	
	public void addItem(Item item) {
		getItems().add(item);
	}
	
	public void removeItem(Item item) {
		getItems().remove(item);
	}
	
	public BigDecimal getOrderTotal() {
		return this.getItems().stream()
				  .map(item -> item.getAmount())
				  .reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
