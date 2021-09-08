package com.sasmita.musings.paymentservice.entity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class PaymentInfo {
	
	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;
	
	private UUID orderId;
	private String cardNumber;
	private BigDecimal amount;
	private String status;
	public PaymentInfo() {
		super();
	}
	
	
	

}
