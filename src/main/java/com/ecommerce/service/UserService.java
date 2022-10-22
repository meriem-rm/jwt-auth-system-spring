package com.ecommerce.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ecommerce.constant.SecurityConstant;
import com.ecommerce.dto.LoginResponseDto;
import com.ecommerce.dto.RefreshTokenResponse;
import com.ecommerce.dto.RegisterDto;
import com.ecommerce.dto.UserDto;
import com.ecommerce.exception.UsernameExistsException;
import com.ecommerce.models.Role;
import com.ecommerce.models.User;
import com.ecommerce.models.UserPrinciple;
import com.ecommerce.respository.RoleRepository;
import com.ecommerce.respository.UserRepository;
import com.ecommerce.utility.JwtTokenProvider;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class UserService {
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Autowired 
	private UserRepository userR;
	@Autowired    
	private PasswordEncoder passwordEncoder; 
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
	private  AuthenticationManager authenticationManager;
	@Autowired
	private  JwtTokenProvider tokenProvider;
	@Autowired
    private  JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserService customUserService;
	
	
    public ResponseEntity<LoginResponseDto> login(@RequestBody UserDto loginDto){
    	try {
		authenticate(loginDto.getEmail(), loginDto.getPassword());
        User user = userR.findByEmail(loginDto.getEmail()).get();
        
        UserDetails userDetails = new UserPrinciple(user);
        //SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(userDetails);
        String refreshToken = tokenProvider.generateRefreshToken(userDetails);
        System.out.println(token);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setUser(user);
        loginResponseDto.setAccessToken(token);
        loginResponseDto.setRefreshToken(refreshToken);
        return ResponseEntity.ok().body(loginResponseDto);
		} catch (Exception e) {
			LOGGER.error("loged in failled", e.getMessage());

			// TODO: handle exception
			throw new RuntimeException(e);
		}
    	//return ResponseEntity.notFound().build().getBody();
    	
    }
    private  void authenticate(String userName, String password) throws AuthenticationException{
        UsernamePasswordAuthenticationToken authenticationToken = 
        		new UsernamePasswordAuthenticationToken(userName, password);
       authenticationManager.authenticate(authenticationToken);
       }
	public ResponseEntity<?> register(RegisterDto dto) {
		// check if email and username already exists
		 validateNewUsernameAndEmail(dto.getUserName(), dto.getEmail());
		User user = new User();
		 String encodedPass = passwordEncoder.encode(dto.getPassword());
		 user.setName(dto.getName());
		 user.setPhone(dto.getPhone());
		 user.setCountry(dto.getCountry());
		 user.setWillaya(dto.getWillaya());
		 user.setAddress(dto.getAddress());
		 user.setGender(dto.getGender());
		 user.setPassword(encodedPass);
		 user.setEmail(dto.getEmail());
		 user.setUserName(dto.getUserName());
		 user.setActive(true);
	     user.setNotLocked(true);
	     user.setVerified(false);
	     user.setLastLoginDate(null);
	     // convert date to string
	    // Date date = dto.getBirthDate();
	    // SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy");
	    // String stringDate = DateFor.format(date);
//	     SimpleDateFormat  DateFor = new SimpleDateFormat("dd MMMM yyyy");
//	     String stringDate = DateFor.format(date);
//	     System.out.println(stringDate);
	     user.setBirthDate(dto.getBirthDate());
	     user.setJoinDate(LocalDateTime.now());
	     Role roles = roleRepository.findByName("ROLE_USER").get();
	     Role roleManager = roleRepository.findByName("ROLE_MANAGER").get();
	     user.setRoles(Collections.singleton(roles));
	     try {
	    	 userR.save(user);
	    	 return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("register failled", e.getMessage());
			throw new RuntimeException(e);
		}
	     
	} 
	private void throwEmailExistsException(String email) {
        throw new UsernameExistsException(String.format("USERNAME_ALREADY_EXISTS", email));
    
	}

    private void throwUsernameExistsException(String username) {
        throw new UsernameExistsException(String.format("EMAIL ALREADY EXISTS", username));
    }
	 private void validateNewUsernameAndEmail(String username, String email) {
		 if (userR.existsByUserName(username))
	            throwUsernameExistsException(username);
			 LOGGER.error("register failled, username already exists", username);
	        if (userR.existsByEmail(email))
	        	LOGGER.error("register failled, email already exists", email);
	            throwEmailExistsException(email);
	 }

	public RefreshTokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException {
        String token = this.customUserService.getJWTfromRequest(request);

		//String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		 RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
	        //if(authorizationHeader != null && authorizationHeader.startsWith(SecurityConstant.TOKEN_PREFIX)) {
	            try {
	            	// String token = authorizationHeader.replace(SecurityConstant.TOKEN_PREFIX, "").trim();
	            	 String email = jwtTokenProvider.getSubject(token);
	                 if (jwtTokenProvider.isTokenValid(email, token)) {
	                 	UserDetails userDetails = customUserService.loadUserByUsername(email);
	                 	 String access_token = tokenProvider.generateToken(userDetails);
	                 	
	                 	refreshTokenResponse.setAccessToken(access_token);
	                 	refreshTokenResponse.setRefreshToken(token);
	                 	  }
	                 return refreshTokenResponse;

	            }catch(Exception exception) {
	            	 response.setHeader("error", exception.getMessage());
	                 response.setStatus(403);
	                 //response.sendError(FORBIDDEN.value());
	                 Map<String, String> error = new HashMap<>();
	                 error.put("error_message", exception.getMessage());
	                 response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
	                 new ObjectMapper().writeValue(response.getOutputStream(), error);
	            } 
//	            } else {
//	            	 throw new RuntimeException("Refresh token is missing");
//	            }
	            throw new RuntimeException("Refresh token is missing");
		//return null;
	}
	 
}
