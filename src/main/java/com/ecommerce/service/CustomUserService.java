package com.ecommerce.service;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ecommerce.constant.SecurityConstant;
import com.ecommerce.models.User;
import com.ecommerce.models.UserPrinciple;
import com.ecommerce.respository.UserRepository;

@Service
public class CustomUserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println(email);
		User user = userRepository.findByEmail(email).get();
		System.out.println("-------- THIS METHOD WORKS ------------");
		System.out.println(user);
		 if (user != null) {
	    	  user.setLastLoginDate(LocalDateTime.now());
	    	   userRepository.save(user); 
	    	   return new UserPrinciple(user);

	}   
    throw new UsernameNotFoundException("NO USER FOUND BY email" + email);
	
	} 
	
	 public String getJWTfromRequest(HttpServletRequest request){
	        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
	        if (authHeader != null && authHeader.startsWith(SecurityConstant.TOKEN_PREFIX)) {
	            String token = authHeader.replace(SecurityConstant.TOKEN_PREFIX, "").trim();
	            return token;
	        } else {
	        	return null;
	        }
	       
	}
}
