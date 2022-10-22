package com.ecommerce.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class RegisterDto {
	private String name;
	private String phone;
	private String country;
	private String willaya ;
	private String address ;
	private String gender;
	private String password;
	private String email; 
	private String userName;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getWillaya() {
		return willaya;
	}
	public void setWillaya(String willaya) {
		this.willaya = willaya;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public RegisterDto(String name, String phone, String country, String willaya, String address, String gender,
			String password, String email, String userName, Date birthDate) {
		super();
		this.name = name;
		this.phone = phone;
		this.country = country;
		this.willaya = willaya;
		this.address = address;
		this.gender = gender;
		this.password = password;
		this.email = email;
		this.userName = userName;
		this.birthDate = birthDate;
	} 
	
	public RegisterDto() {}
}
