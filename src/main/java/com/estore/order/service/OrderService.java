package com.estore.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.estore.order.entity.Order;
import com.estore.order.repository.OrderRepository;
import com.estore.order.util.OrderStatus;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;

@Service
public class OrderService {

	
	@Autowired
	OrderRepository repo;
	
	@Autowired
	MongoTemplate template;
	
	public List<Order> findAll(){
		
		List<Order> orders = repo.findAll();
		return orders;
	}
	
	public Order createOrder(Order order) {
		
		order.setId(getRandomNumber());
		order.setStatus(OrderStatus.ACCEPTED.name());
		Order order2 = repo.insert(order);
		return order2;
	}
	
	public Order updateOrder2(Order order) {
		
		Order order2 = repo.save(order);
		return order2;
	}
	
	public Order updateOrder(Order order) {
		Query query = new Query();
		query.addCriteria(Criteria.where("orderId").is(order.getOrderId()));
		
		Update update = new Update();
		update.set("status", order.getStatus());
		update.set("productId", order.getProductId());
		update.set("productName", order.getProductName());
		update.set("productName", order.getProductName());
		update.set("price", order.getPrice());
		update.set("quantity", order.getQuantity());
		
		template.updateFirst(query, update, Order.class);
		return order;
	}
	
	public long  updateOrders(List<Order> orders) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("status").is("ACCEPTED"));
		
		
		Update update = new Update();
		orders.stream().forEach(order -> {
			update.set("status", order.getStatus());
		});
		
		UpdateResult updateMulti = template.updateMulti(query, update, Order.class);
		System.out.println("Records modified  :" + updateMulti.getModifiedCount());
		
		return updateMulti.getModifiedCount();
	}
	
	public long  updateOrdersWithDifferentValues(List<Order> orders) {
		
		List<Pair<Query, Update>> updateList = new ArrayList<Pair<Query,Update>>();

		orders.stream().forEach(order -> {
			Query query = new Query();
			query.addCriteria(Criteria.where("orderId").is(order.getOrderId()));
			
			Update update = new Update();
			update.set("status", order.getStatus());
			update.set("productId", order.getProductId());
			update.set("productName", order.getProductName());
			update.set("productName", order.getProductName());
			update.set("price", order.getPrice());
			update.set("quantity", order.getQuantity());
			
			Pair<Query, Update> pair = Pair.of(query, update);
			updateList.add(pair);
		});
		
		
		BulkOperations bulkOps = template.bulkOps(BulkMode.UNORDERED, Order.class, "order-detail");
		bulkOps.updateMulti(updateList );
		BulkWriteResult result = bulkOps.execute();
		
		return result.getModifiedCount();
	}
	
	public void deleteOrder(long id) {
		repo.deleteByOrderId(id);
	}
	
	private long getRandomNumber() {
		long min = 1000000000L;
		long max = 9999999999L;

		Random random = new Random();
		return random.nextLong() % (max - min) + max;

	}
}
