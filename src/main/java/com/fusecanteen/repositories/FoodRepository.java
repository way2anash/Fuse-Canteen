package com.fusecanteen.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fusecanteen.domain.Food;

@Repository
public interface FoodRepository extends MongoRepository<Food, Long>{

	public Food findByName(String foodName);
	
	public List<Food> findByIsPreparedToday(Boolean b);
	
	
}
