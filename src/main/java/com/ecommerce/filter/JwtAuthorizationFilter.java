package com.ecommerce.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.constant.SecurityConstant;
import com.ecommerce.service.CustomUserService;
import com.ecommerce.utility.JwtTokenProvider;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private  JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserService customUserService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
        	response.setStatus(OK.value());
        } else {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith(SecurityConstant.TOKEN_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            } 
            String token = authHeader.replace(SecurityConstant.TOKEN_PREFIX, "").trim();
            // String token = this.customUserService.getJWTfromRequest(request);
             String email = jwtTokenProvider.getSubject(token);
             if (jwtTokenProvider.isTokenValid(email, token)) {
             	UserDetails userDetails = customUserService.loadUserByUsername(email);
             	UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
	                        userDetails, null, userDetails.getAuthorities()
	                );
	                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	               
                 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
             } else {
                 SecurityContextHolder.clearContext();
             }
        }
       
        filterChain.doFilter(request, response);
    }
    
    
    private String getJWTfromRequest(HttpServletRequest request){
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            String token = authHeader.replace(SecurityConstant.TOKEN_PREFIX, "").trim();
            return token;
        } else {
        	return null;
        }
       
}
	
}
