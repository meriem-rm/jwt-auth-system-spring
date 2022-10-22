package com.ecommerce.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
@Table(name = "users")
public class User  implements Serializable  {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(nullable=false, updatable=false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id; 
	private String name;
	private String phone;
	private String country;
	private String willaya ;
	private String address ;
	private String gender;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private String email; 
	private String userName;
	private boolean isActive; 
    private boolean isNotLocked;
    private boolean verified;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date")
    private Date birthDate;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime joinDate;
    private LocalDateTime lastLoginDate;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
	
	public User() {}
	
	public User(Long id, String name, String phone, String country, String willaya, String address, String gender,
			String password, String email, String userName, boolean isActive, boolean isNotLocked, boolean verified,
			Date birthDate, LocalDateTime joinDate, LocalDateTime lastLoginDate, Set<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.country = country;
		this.willaya = willaya;
		this.address = address;
		this.gender = gender;
		this.password = password;
		this.email = email;
		this.userName = userName;
		this.isActive = isActive;
		this.isNotLocked = isNotLocked;
		this.verified = verified;
		this.birthDate = birthDate;
		this.joinDate = joinDate;
		this.lastLoginDate = lastLoginDate;
		this.roles = roles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isNotLocked() {
		return isNotLocked;
	}
	public void setNotLocked(boolean isNotLocked) {
		this.isNotLocked = isNotLocked;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public LocalDateTime getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(LocalDateTime joinDate) {
		this.joinDate = joinDate;
	}
	public LocalDateTime getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(LocalDateTime lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	


}
