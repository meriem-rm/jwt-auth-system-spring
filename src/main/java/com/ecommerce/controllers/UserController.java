package com.ecommerce.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.LoginResponseDto;
import com.ecommerce.dto.RefreshTokenResponse;
import com.ecommerce.dto.RegisterDto;
import com.ecommerce.dto.UserDto;
import com.ecommerce.models.User;
import com.ecommerce.service.UserService;


@RestController
@RequestMapping("auth")
public class UserController {
	@Autowired
	UserService userService;
	
//	@GetMapping("hello") 
//	public String d() {
//		return "hello world";
//	}
	@PostMapping("login")
	 public ResponseEntity<LoginResponseDto> register(@RequestBody UserDto user) {
		return this.userService.login(user);
	}

	@PostMapping("register")
	 public ResponseEntity<?> register(@RequestBody RegisterDto user) {
		return this.userService.register(user);
	}
	
	@GetMapping("/tokenRefresh")
    public RefreshTokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String authorizationHeader = request.getHeader(AUTHORIZATION);
//        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            try {
//                String refresh_token = authorizationHeader.substring("Bearer ".length());
     return this.userService.refreshToken(request, response);
	}
}
