package com.ecommerce.dto;

import com.ecommerce.models.User;

public class LoginResponseDto {
	private User user;
	private String accessToken;
	private String refreshToken;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public LoginResponseDto(User user, String accessToken, String refreshToken) {
		super();
		this.user = user;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	
	public LoginResponseDto() {}

}
