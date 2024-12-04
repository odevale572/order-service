package com.estore.order.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.order.entity.Order;
import com.estore.order.service.OrderService;

@RestController
@RequestMapping(path = "/order")
//@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

	@Autowired
	OrderService service;
	
	@GetMapping("/status")
	public String test() {
		return "order service app started";
	}
	
	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody Order order){
		
		return new ResponseEntity<Order>(service.createOrder(order), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Order> updateOrder(@RequestBody Order order){
		return new ResponseEntity<Order>(service.updateOrder(order), HttpStatus.OK);
	}
	

	@PutMapping("/updateAll")
	public ResponseEntity<Long> updateOrders(@RequestBody List<Order> orders){
		return new ResponseEntity<Long>(service.updateOrdersWithDifferentValues(orders), HttpStatus.OK);
	}
	
	
	@GetMapping
	public ResponseEntity<List<Order>> findAllOrders(){
		
		List<Order> list = new ArrayList<Order>();
		list = service.findAll();
		return new ResponseEntity<List<Order>>(list, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteOrder(@PathVariable("id") long id) {
		service.deleteOrder(id);
		return new ResponseEntity<String>("Order Deleted", HttpStatus.OK);
	}
}
