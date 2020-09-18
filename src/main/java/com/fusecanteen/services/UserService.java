package com.fusecanteen.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusecanteen.domain.FuseUser;
import com.fusecanteen.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	private final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	public FuseUser findUserByEmail(String email) {
		
		LOG.info("Getting user with email: {}.", email);
		return userRepository.findByEmail(email);
	}

	public List<FuseUser> findAllUsers() {
		
		LOG.info("Getting all users.");
		return userRepository.findAll();
	}
	
	public Optional<FuseUser> findUserById(Long userId) {
		
		LOG.info("Getting user with ID: {}.", userId);
		return userRepository.findById(userId);
	}
	

	public FuseUser saveUser(FuseUser user) {
		
		LOG.info("Saving user.");
		return userRepository.save(user);
	}

	public FuseUser updateUser(FuseUser user, Long userId) {
		
		LOG.info("Updating user with ID: {}.", userId);
		Optional<FuseUser> oldUser = userRepository.findById(userId);
		
		if(oldUser== null) {
			userRepository.save(user);
			return user;
		}
		
		user.setId(userId);
		return userRepository.save(user);
	}

	public void deleteUserById(Long userId) {
		
		LOG.info("Deleting user with ID: {}.", userId);
		userRepository.deleteById(userId);
	}
	
}
