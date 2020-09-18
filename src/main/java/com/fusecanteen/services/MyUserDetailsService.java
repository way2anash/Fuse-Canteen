package com.fusecanteen.services;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fusecanteen.domain.FuseUser;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserService userService;

	private final Logger LOG = LoggerFactory.getLogger(FoodService.class);
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		LOG.info("Getting user with email: {}.", email);
		FuseUser user = userService.findUserByEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException(String.format("Email %s not found", email));
		}
		
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		UserDetails userDetails = (UserDetails)new User(user.getEmail(), 
				user.getPassword(), Arrays.asList(authority));
		
		return userDetails;	
		
	}

}
