package com.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ecommerce.constant.SecurityConstant;
import com.ecommerce.filter.JwtAccessDeniedFilter;
import com.ecommerce.filter.JwtAuthonticationEntryPoint;
import com.ecommerce.filter.JwtAuthorizationFilter;
import com.ecommerce.service.CustomUserService;

@Configuration
@EnableWebSecurity 
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private JwtAccessDeniedFilter jwtAccessDeniedHandler;
	@Autowired
    private CustomUserService userDetailsService;
	@Autowired
	 private JwtAuthonticationEntryPoint jwtAuthenticationEntryPoint;
	
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
	    return new BCryptPasswordEncoder();
	} 
	
	@Bean
    public JwtAuthorizationFilter jwtAuthenticationFilter(){
        return  new JwtAuthorizationFilter();
    } 
	 @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService)
	                .passwordEncoder(passwordEncoder());
	    }  
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	                .csrf().disable()
	                
	                
	                .sessionManagement()
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                .and()
	                .authorizeRequests()
	                .antMatchers(HttpMethod.GET, "/auth/**").permitAll()
	                .antMatchers("/auth/**").permitAll()
//	                .antMatchers("/v2/api-docs/**").permitAll()
//	                .antMatchers("/swagger-ui/**").permitAll()
//	                .antMatchers("/swagger-resources/**").permitAll()
//	                .antMatchers("/swagger-ui.html").permitAll()
//	                .antMatchers("/webjars/**").permitAll()
	                .anyRequest()
	                .authenticated()
	                .and()
	                .exceptionHandling()
	                .accessDeniedHandler(jwtAccessDeniedHandler)
	                .authenticationEntryPoint(jwtAuthenticationEntryPoint);
	        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	    }
	 @Override
	    @Bean
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
	 
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/auth/**")
	                        .allowedOrigins(SecurityConstant.Allow_ORIGIN)
	                        .exposedHeaders(SecurityConstant.JWT_TOKEN_HEADER);
	                registry.addMapping("/**").allowedOrigins(SecurityConstant.Allow_ORIGIN);
	            }
	        };
	    }
	 
}
