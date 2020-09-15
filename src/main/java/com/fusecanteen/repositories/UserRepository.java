package com.fusecanteen.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fusecanteen.domain.FuseUser;

@Repository
public interface UserRepository extends MongoRepository<FuseUser, Long>{

	@Query("{email : ?0}")
	FuseUser findByUserEmail(String email);
	
}
