package com.fusecanteen.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fusecanteen.domain.Food;

@Repository
public interface FoodRepository extends MongoRepository<Food, Long>{

	@Query("{name : ?0}")
	Food findByName(String foodName);
	
	@Query("{isPreparedToday : ?0}")
	List<Food> findFoodByIsPreparedToday(Boolean b);
	
	
}
