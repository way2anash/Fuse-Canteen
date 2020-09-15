package com.fusecanteen.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fusecanteen.domain.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {

	@Query("{userId : ?0}")
	List<Order> findOrderByUserId(Long userId);
}
