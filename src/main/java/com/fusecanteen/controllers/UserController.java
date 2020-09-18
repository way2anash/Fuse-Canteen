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

import com.fusecanteen.domain.FuseUser;
import com.fusecanteen.domain.MessageResponse;
import com.fusecanteen.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<List<FuseUser>> findAllUsers() {
		
		 List<FuseUser> user = userService.findAllUsers();
		 if(user.isEmpty()) {
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 }
		 return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping("/users/{userId}") 
	public ResponseEntity<FuseUser> findUserById(@PathVariable Long userId) {
		
		Optional<FuseUser> user = userService.findUserById(userId);
		if(user.isPresent()) {
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/users") 
	public ResponseEntity<?> saveUser(@RequestBody FuseUser user){
		
		FuseUser newUser = userService.saveUser(user);
		if(newUser !=null) {
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		}
		return  ResponseEntity.badRequest()
				.body(new MessageResponse("Error: User with id/email already exists!"));
		
	}
	
	@PutMapping("/users/{userId}") 
	public ResponseEntity<FuseUser> updateUser(@RequestBody FuseUser user, @PathVariable Long userId) {
		
		FuseUser newUser = userService.updateUser(user, userId);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/users")
	public ResponseEntity<?>  deleteUser(@PathVariable Long userId) {
		
		FuseUser user = userService.deleteUserById(userId);
		if(user !=null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return  ResponseEntity.badRequest()
				.body(new MessageResponse("Error: User with id doesn't exists!"));
	}
}






