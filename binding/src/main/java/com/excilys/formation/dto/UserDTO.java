package com.excilys.formation.dto;

import org.hibernate.validator.constraints.NotBlank;

public class UserDTO {
	
	private long id;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String roleId;
	private String roleName;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String mdp) {
		this.password = mdp;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
