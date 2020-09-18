package com.fusecanteen.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fusecanteen.domain.MessageResponse;
import com.fusecanteen.domain.Order;
import com.fusecanteen.services.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("/orders")
	public ResponseEntity<List<Order>> findAllOrders() {

		List<Order> order = orderService.findAllOrder();
		if(order.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<Order> findOrdersById(@PathVariable Long orderId) {

		Optional<Order> order = orderService.findOrderById(orderId);
		if(order.isPresent()) {
			return new ResponseEntity<>(order.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/orders")
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {

			Order savedOrder = orderService.saveOrder(order);
			return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
		
	}	
	
	@PutMapping("/orders/{orderId}")
	public ResponseEntity<Order> UpdateOrder(@RequestBody Order order, @PathVariable Long orderId) {

		Order updatedOrder = orderService.updateOrder(order, orderId);
		
		return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
	}
	
	@DeleteMapping("/orders/{orderId}")
	public ResponseEntity<?> DeleteOrder(@PathVariable Long orderId) {

		
			Order order = orderService.deleteOrder(orderId);
			if(order !=null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return  ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Order Id is doesnt exists!"));
	}
	
	@GetMapping("/ordersHistory/{userId}")
	public ResponseEntity<List<Order>> findAllOrdersByUserId (@PathVariable Long userId) {
		
		List<Order> order = orderService.findAllOrderByUserId(userId);
		if(order.isEmpty()) {
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		}
		
		return new ResponseEntity<>(order, HttpStatus.OK);
		
	}
	

}





