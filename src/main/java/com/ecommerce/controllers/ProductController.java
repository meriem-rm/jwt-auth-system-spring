package com.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("")

public class ProductController {
	
	@GetMapping("/hello") 
	public ResponseEntity<String> d() {
		try {
			return new ResponseEntity<String>( "hello world",HttpStatus.OK);
	
		} catch (Exception e) {
			throw new RuntimeException(e);
			// TODO: handle exception
		}
	}

}
