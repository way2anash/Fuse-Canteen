package com.fusecanteen.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusecanteen.domain.FuseUser;
import com.fusecanteen.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public FuseUser findUserByEmail(String email) {
		
		return userRepository.findByUserEmail(email);
	}

	public List<FuseUser> findAllUsers() {
		
		return userRepository.findAll();
	}
	
	public Optional<FuseUser> findUserById(Long userId) {
		
		return userRepository.findById(userId);
	}
	

	public FuseUser saveUser(FuseUser user) {
		
		return userRepository.save(user);
	}

	public FuseUser updateUser(FuseUser user, Long userId) {
		Optional<FuseUser> oldUser = userRepository.findById(userId);
		
		if(oldUser== null) {
			userRepository.save(user);
			return user;
		}
		
		user.setId(userId);
		return userRepository.save(user);
	}

	public void deleteUserById(Long userId) {
		
		userRepository.deleteById(userId);
	}
	
}
