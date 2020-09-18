package com.fusecanteen.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusecanteen.domain.Food;
import com.fusecanteen.repositories.FoodRepository;

@Service
public class FoodService {

	@Autowired
	private FoodRepository foodRepository;
	
	private final Logger LOG = LoggerFactory.getLogger(FoodService.class);

	public Food saveFood(Food food) {

		LOG.info("Saving food.");
		return foodRepository.save(food);
	}

	public List<Food> findAllFood() {

		LOG.info("Getting all foods.");
		List<Food> food = foodRepository.findAll();
		return food;
	}

	public Optional<Food> findFoodById(Long foodId) {

		LOG.info("Getting food with ID: {}.", foodId);
		Optional<Food> food = foodRepository.findById(foodId);
		return food;
	}

	public Food findFoodByName(String foodName) {

		LOG.info("Getting food with Food Name: {}.", foodName);
		Food food = foodRepository.findByName(foodName);
		return food;
	}

	public List<Food> findFoodByPopularity() {

		LOG.info("Getting all foods by popularity");
		List<Food> food = foodRepository.findAll();
		List<Food> popularFood = food.stream()
				.filter(f -> f.getIsPreparedToday()==true)
				.sorted(Comparator.comparingInt(Food::getFrequency).reversed())
				.collect(Collectors.toList());
		return popularFood;
	}

	public Food updateFood(Food food, Long foodId) {
		
		LOG.info("Updating food with ID: {}.", foodId);
		Optional<Food> tempFood = foodRepository.findById(foodId);
		if (tempFood.isPresent()) {
			
			food.setId(foodId);
			return foodRepository.save(food);
		}
		return saveFood(food);
	}

	public void deleteFoodById(Long foodId) {

		LOG.info("Deleting food with ID: {}.", foodId);
		foodRepository.deleteById(foodId);

	}

	//Methods for Prepared Today Food list
	public List<Food> findAllPreparedTodayFood() {
		
		LOG.info("Finding all prepared foods today.");
		return foodRepository.findByIsPreparedToday(true);
	}

	public List<Food> addItemToPreparedToday(List<String> items) {
		
		LOG.info("Adding food to prepared foods today.");
		List<Food> addedFood = new ArrayList<Food> ();
		
		for(String item: items) {
			
			Food food = findFoodByName(item);
			food.setIsPreparedToday(true);
			foodRepository.save(food);
			
			addedFood.add(food);
		}
		return addedFood;
	}

	public Food removeItemFromPreparedFoodToday(Long foodId) {
		
		LOG.info("Removing food from prepared foods today with ID: {}.", foodId);
		Optional<Food> food = foodRepository.findById(foodId);
		food.get().setIsPreparedToday(false);
		return foodRepository.save(food.get());
	}
	
	public void removeAllItemFromPreparedToday() {
		
		LOG.info("Removing All food items from prepared foods today");
		List<Food> food = foodRepository.findAll();
		List<Food> updateFood = new ArrayList<>();
				for(Food f: food) {
					f.setIsPreparedToday(false);
					updateFood.add(f);
				}
				foodRepository.saveAll(updateFood);
	}



}
