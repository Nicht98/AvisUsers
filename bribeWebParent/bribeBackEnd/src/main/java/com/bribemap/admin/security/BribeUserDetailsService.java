package com.bribemap.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bribemap.admin.user.UserRepository;
import com.bribemap.common.entity.User;

public class BribeUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user =userRepo.getUserByEmail(email);
		if(user != null) {
			return new BribeUserDetails(user);
		}
		
		throw new UsernameNotFoundException("could not find user email : "+email);
	}

}
