package com.tsnc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name="login")
public class Login extends Base{
	
	
	public Login() {
	
	}
	
	public Login(String username, String password, int role, String token,
			Date date) {
		
		this.username = username;
		this.password = password;
		this.role = role;
		this.token = token;
		this.date = date;
	}
	
	@Column
	@Id
	private String username;
	@Column
	private String password;
	@Column
	private int role;
	@Column
	private String token;
	@Column
	private Date date;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	

}
