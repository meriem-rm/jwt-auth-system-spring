package com.ecommerce.filter;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import com.ecommerce.constant.SecurityConstant;
import com.ecommerce.domain.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class JwtAuthonticationEntryPoint extends Http403ForbiddenEntryPoint {
    private  ObjectMapper  objectMapper; 
    
    public JwtAuthonticationEntryPoint(ObjectMapper  objectMapper) {
  	  this.objectMapper = objectMapper;
    } 
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, 
  		  AuthenticationException exception) throws IOException {
  	  var httpResponse = new HttpResponse();
  	  httpResponse.setHttpStatus(HttpStatus.UNAUTHORIZED); 
  	  httpResponse.setMessage(SecurityConstant.FORBIDDEN_MESSAGE);
  	  httpResponse.setReason(UNAUTHORIZED.getReasonPhrase().toUpperCase());
  	  httpResponse.setHttpStatusCode(UNAUTHORIZED.value());
  	 // String JsonString = objectMapper.writeValueAsString(httpResponse);
  	  
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
       // response.sendError(403, JsonString);
  	 OutputStream outputStream = response.getOutputStream(); 
  	 objectMapper.writeValue(outputStream, httpResponse);
    }
    }
    
