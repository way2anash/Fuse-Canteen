package com.fusecanteen.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fusecanteen.domain.FuseUser;

@Repository
public interface UserRepository extends MongoRepository<FuseUser, Long>{

	public FuseUser findByEmail(String email);
	
}
