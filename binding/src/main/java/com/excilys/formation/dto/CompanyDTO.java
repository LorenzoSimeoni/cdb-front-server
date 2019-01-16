package com.excilys.formation.dto;

import com.excilys.formation.model.Company;

public class CompanyDTO {
	private long id;
	private String name;
	
	public CompanyDTO() {}
	
	public CompanyDTO(Company company) {
		this.id = company.getId();
		this.name = company.getName();
	}
	
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
}
