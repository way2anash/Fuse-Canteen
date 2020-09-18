package com.fusecanteen.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.fusecanteen.controllers.OrderController;
import com.fusecanteen.domain.Food;
import com.fusecanteen.domain.Order;
import com.fusecanteen.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private FoodService foodService;
	
	private final Logger LOG = LoggerFactory.getLogger(OrderService.class);
	
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public List<Order> findAllOrder() {
		
		LOG.info("Getting all orders.");
		return orderRepository.findAll();
	}
	
	public Optional<Order> findOrderById(Long orderId) {
		
		LOG.info("Getting Order with ID: {}.", orderId);
		return orderRepository.findById(orderId);
		
	}
	
	public Order saveOrder(Order order) {
		
		LOG.info("Saving user.");
		List<String> items = order.getItems();
		//Updating food frequency and Calculating total cost
		addingFoodFrequency(items);
		order.setCost(getTotalPrice(items));
		order.setCreatedDate(dateFormat.format(new Date()));
		return orderRepository.save(order);
	}
	
	public Order updateOrder(Order order, Long orderId) {
		LOG.info("Updating Order with ID: {}.", orderId);
		Optional<Order> tempOrder = orderRepository.findById(orderId);
		if(!tempOrder.isPresent()) {
			
			return saveOrder(order);
		}
		order.setId(orderId);
		//Managing food frequency 
		managingFoodFrequency( order,  tempOrder.get());
		//Calculating total cost
		order.setCost(getTotalPrice(order.getItems()));
		order.setCreatedDate(dateFormat.format(new Date()));
		return orderRepository.save(order);
	}
	
	public void deleteOrder(Long orderId) {
		LOG.info("Deleting Order with ID: {}.", orderId);
		orderRepository.deleteById(orderId);
	}
	
	public float getTotalPrice(List<String> items) {
		LOG.info("Calculating total price");
		float totalCost = 0;
		for(String foodName: items) {
			Food food = foodService.findFoodByName(foodName);
			totalCost = totalCost + food.getPrice();
		}
		return totalCost;
	}
	
	public void managingFoodFrequency(Order order, Order tempOrder) {
		LOG.info("Managing food frequency");
		List<String> oldItems = tempOrder.getItems();
		List<String> updatedItems = order.getItems();
		
		List<String> addFreqItems = filteringItemsForAddingFF(oldItems, updatedItems);
		addingFoodFrequency(addFreqItems);
		
		List<String>  rmvFreqItems = filteringItemsForRemovingFF(oldItems, updatedItems);
		removingFoodFrequency(rmvFreqItems);
	}
	
	public void addingFoodFrequency(List<String> items) {
		LOG.info("Adding food frequency");
		int foodFrequency;
		for(String foodName: items) {
			Food food = foodService.findFoodByName(foodName);
			foodFrequency = food.getFrequency() + 1;
			food.setFrequency(foodFrequency);
			foodService.saveFood(food);
	}

   }
	
	public List<String> filteringItemsForAddingFF(List<String> oldItems, List<String> updatedItems) {
		LOG.info("Filtering items for adding food frequency");
		List<String> filteredItem = new ArrayList<String> ();
		for(String item: updatedItems) {
			if(!oldItems.contains(item)) {
				filteredItem.add(item);
			}
		}
		
		return filteredItem;
	}
	
	public List<String> filteringItemsForRemovingFF(List<String> oldItems, List<String> updatedItems) {
		LOG.info("Filtering items for removing food frequency");
		List<String> filteredItem = new ArrayList<String> ();
		for(String item: oldItems) {
			if(!updatedItems.contains(item)) {
				filteredItem.add(item);
			}
		}
		
		return filteredItem;
	}
	
	public void removingFoodFrequency(List<String> rmvFreqItems) {
		
		LOG.info("Removing food frequency");
		int foodFrequency;
		for(String foodName: rmvFreqItems) {
			Food food = foodService.findFoodByName(foodName);
			foodFrequency = food.getFrequency() - 1;
			food.setFrequency(foodFrequency);
			foodService.saveFood(food);
	}
		
	}

	public List<Order> findAllOrderByUserId(Long userId) {
		LOG.info("Getting Order history with UserID: {}.", userId);
		return orderRepository.findByUserId(userId);
		
	}


}
