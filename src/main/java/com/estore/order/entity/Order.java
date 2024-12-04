package com.estore.order.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.estore.order.util.OrderStatus;

import lombok.Data;

@Data
@Document("order")
public class Order {

	@Id
	private Object id;
	private String orderId;
	private long productId;
	private String productName;
	private long price;
	
	private int quantity;
	private String status;
}
