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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.fusecanteen.domain.Food;
import com.fusecanteen.domain.Order;
import com.fusecanteen.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private FoodService foodService;
	
	
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public List<Order> findAllOrder() {
		
		return orderRepository.findAll();
	}
	
	public Optional<Order> findOrderById(Long orderId) {
		
		return orderRepository.findById(orderId);
		
	}
	
	public Order saveOrder(Order order) {
		
		List<String> items = order.getItems();
		//Updating food frequency and Calculating total cost
		addingFoodFrequency(items);
		order.setCost(getTotalPrice(items));
		order.setCreatedDate(dateFormat.format(new Date()));
		return orderRepository.save(order);
	}
	
	public Order updateOrder(Order order, Long orderId) {
		
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
		
		orderRepository.deleteById(orderId);
	}
	
	public float getTotalPrice(List<String> items) {
		float totalCost = 0;
		for(String foodName: items) {
			Food food = foodService.findFoodByName(foodName);
			totalCost = totalCost + food.getPrice();
		}
		return totalCost;
	}
	
	public void managingFoodFrequency(Order order, Order tempOrder) {
	
		List<String> oldItems = tempOrder.getItems();
		List<String> updatedItems = order.getItems();
		
		List<String> addFreqItems = filteringItemsForAddingFF(oldItems, updatedItems);
		addingFoodFrequency(addFreqItems);
		
		List<String>  rmvFreqItems = filteringItemsForRemovingFF(oldItems, updatedItems);
		removingFoodFrequency(rmvFreqItems);
	}
	
	public void addingFoodFrequency(List<String> items) {
		
		int foodFrequency;
		for(String foodName: items) {
			Food food = foodService.findFoodByName(foodName);
			foodFrequency = food.getFrequency() + 1;
			food.setFrequency(foodFrequency);
			foodService.saveFood(food);
	}

   }
	
	public List<String> filteringItemsForAddingFF(List<String> oldItems, List<String> updatedItems) {
		
		List<String> filteredItem = new ArrayList<String> ();
		for(String item: updatedItems) {
			if(!oldItems.contains(item)) {
				filteredItem.add(item);
			}
		}
		
		return filteredItem;
	}
	
	public List<String> filteringItemsForRemovingFF(List<String> oldItems, List<String> updatedItems) {
		List<String> filteredItem = new ArrayList<String> ();
		for(String item: oldItems) {
			if(!updatedItems.contains(item)) {
				filteredItem.add(item);
			}
		}
		
		return filteredItem;
	}
	
	public void removingFoodFrequency(List<String> rmvFreqItems) {
		
		int foodFrequency;
		for(String foodName: rmvFreqItems) {
			Food food = foodService.findFoodByName(foodName);
			foodFrequency = food.getFrequency() - 1;
			food.setFrequency(foodFrequency);
			foodService.saveFood(food);
	}
		
	}

	public List<Order> findAllOrderByUserId(Long userId) {
		
		return orderRepository.findByUserId(userId);
		
	}


}