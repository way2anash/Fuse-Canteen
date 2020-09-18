package com.fusecanteen.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusecanteen.domain.Food;
import com.fusecanteen.repositories.FoodRepository;

@Service
public class FoodService {

	@Autowired
	private FoodRepository foodRepository;

	public Food saveFood(Food food) {

		return foodRepository.save(food);
	}

	public List<Food> findAllFood() {

		List<Food> food = foodRepository.findAll();
		return food;
	}

	public Optional<Food> findFoodById(Long foodId) {

		Optional<Food> food = foodRepository.findById(foodId);
		return food;
	}

	public Food findFoodByName(String foodName) {

		Food food = foodRepository.findByName(foodName);
		return food;
	}

	public List<Food> findFoodByPopularity() {

		List<Food> food = foodRepository.findAll();
		List<Food> popularFood = food.stream()
				.filter(f -> f.getIsPreparedToday()==true)
				.sorted(Comparator.comparingInt(Food::getFrequency).reversed())
				.collect(Collectors.toList());
		return popularFood;
	}

	public Food updateFood(Food food, Long foodId) {
		Optional<Food> tempFood = foodRepository.findById(foodId);
		if (tempFood.isPresent()) {
			
			food.setId(foodId);
			return foodRepository.save(food);
		}
		return saveFood(food);
	}

	public void deleteFoodById(Long foodId) {

		foodRepository.deleteById(foodId);

	}

	//Methods for Prepared Today Food list
	public List<Food> findAllPreparedTodayFood() {
		
		return foodRepository.findByIsPreparedToday(true);
	}

	public List<Food> addItemToPreparedToday(List<String> items) {
		
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
		
		Optional<Food> food = foodRepository.findById(foodId);
		food.get().setIsPreparedToday(false);
		return foodRepository.save(food.get());
	}


}
