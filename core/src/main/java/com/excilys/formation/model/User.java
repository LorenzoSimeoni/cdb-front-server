package com.excilys.formation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	public User() {}
	
	public User(long id, String name, String mdp, Role role) {
		super();
		this.id = id;
		this.name = name;
		this.mdp = mdp;
		this.role = role;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="loginName", nullable = false)
	private String name;
	
	@Column(name="passwordHash")
	private String mdp;
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return mdp;
	}

	public void setPassword(String mdp) {
		this.mdp = mdp;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
