package com.ecommerce.dto;

public class RefreshTokenResponse {
	private String accessToken;
	private String refreshToken;
	
	public RefreshTokenResponse() {}
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
	
}
