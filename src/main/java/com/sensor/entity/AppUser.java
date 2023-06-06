package com.sensor.entity;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "app_user", uniqueConstraints = {
		@UniqueConstraint(columnNames = "user_name", name = "username_unique") })
public class AppUser extends BaseEntity<Long> implements UserDetails {

	private static final long serialVersionUID = 778681625954128816L;

	@Column(name = "user_name")
	@NotBlank(message = "{SENSOR1002}")
	private String username;

	@NotBlank(message = "{SENSOR1002}")
	private String password;

	@Transient
	private String token;

	public AppUser() {
		super();
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

}
