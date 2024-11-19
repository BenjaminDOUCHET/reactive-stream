package com.example.classic.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Document(collection = "inventory")
public class Item {
	@Id
	private String id;

	private String brand;
	private String sellerRef;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
}

