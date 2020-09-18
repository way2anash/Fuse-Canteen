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

import com.fusecanteen.domain.Food;
import com.fusecanteen.services.FoodService;

@RestController
public class FoodController {

	@Autowired
	private FoodService foodService;

	@GetMapping("/foods")
	public ResponseEntity<List<Food>> getAllFoodItems() {
		List<Food> food = foodService.findAllFood();

		if (food.isEmpty()) {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(food, HttpStatus.OK);
	}

	@GetMapping("/foods/{foodId}")
	public ResponseEntity<Food> getFoodItemById(@PathVariable Long foodId) {

		Optional<Food> food = foodService.findFoodById(foodId);
		if (food.isPresent()) {
			return new ResponseEntity<>(food.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/foods")
	public ResponseEntity<Food> createFoodItem(@RequestBody Food food) {

		try {
			Food newFood = foodService.saveFood(food);
			return new ResponseEntity<>(newFood, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/foods/popularFood")
	public ResponseEntity<List<Food>> getFoodByPopularity() {

		List<Food> food = foodService.findFoodByPopularity();
		if (food.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(food, HttpStatus.OK);
	}

	@PutMapping("/foods/{foodId}")
	public ResponseEntity<Food> updateFoodItem(@RequestBody Food food, @PathVariable Long foodId) {

		Food updatedFood = foodService.updateFood(food, foodId);
		return new ResponseEntity<>(updatedFood, HttpStatus.CREATED);
	}

	@DeleteMapping("/foods/{foodId}")
	public ResponseEntity<HttpStatus> deleteFoodById(@PathVariable Long foodId) {

		try {

			foodService.deleteFoodById(foodId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Methods for Prepared Today Food list
	@GetMapping("/preparedToday")
	public ResponseEntity<List<Food>> findAllPreparedToday() {

		List<Food> food = foodService.findAllPreparedTodayFood();

		if (food.isEmpty()) {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(food, HttpStatus.OK);
	}

	@PostMapping("/preparedToday")
	public ResponseEntity<List<Food>> addItemToPreparedToday(@RequestBody List<String> items) {

		List<Food> food = foodService.addItemToPreparedToday(items);
		return new ResponseEntity<>(food, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/preparedToday/{foodId}")
	public ResponseEntity<HttpStatus> removeItemFromPreparedFoodToday(@PathVariable Long foodId) {
		try {

			foodService.removeItemFromPreparedFoodToday(foodId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/preparedToday")
	public ResponseEntity<HttpStatus> removeAllItemFromPreparedToday() {

		try {

			foodService.removeAllItemFromPreparedToday();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
