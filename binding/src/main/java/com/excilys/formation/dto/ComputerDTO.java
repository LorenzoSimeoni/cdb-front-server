package com.excilys.formation.dto;

import org.hibernate.validator.constraints.NotBlank;

import com.excilys.formation.model.Computer;


public class ComputerDTO {
	private long id;
	
	@NotBlank
	private String name;
	private String introduced;
	private String discontinued;
	private String companyName;
	private String companyId;
	
	public ComputerDTO(Computer computer) {
		this.id = computer.getId();
		this.name = computer.getName();
		if(computer.getIntroduced()!=null) {
			this.introduced = computer.getIntroduced().toString().substring(0, 10);
		} else {
			this.introduced = null;
		}
		if(computer.getDiscontinued()!=null) {
			this.discontinued = computer.getDiscontinued().toString().substring(0, 10);
		} else {
			this.discontinued = null;
		}
		if(computer.getCompany()!=null) {
			this.companyName = computer.getCompany().getName();
			this.companyId = Long.toString(computer.getCompany().getId());			
		} else {
			this.companyName = "";
			this.companyId = "";
		}
	}
	
	public ComputerDTO(String name, String introduced, String discontinued, String companyId) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}
	
	public ComputerDTO(long id, String name, String introduced, String discontinued, String companyId) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}
	
	public ComputerDTO() {
		this.id = 0;
		this.name = "";
		this.introduced = "";
		this.discontinued = "";
		this.companyName = "";
		this.companyId = "";
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}
	public String getCompanyName() {
		return companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", companyName=" + companyName + ", companyId="+ companyId + "]";
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

}
