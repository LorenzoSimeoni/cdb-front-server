package com.excilys.formation.dao;

public enum OrderByCompany {
	ID("id"), 
	NAME("name");

	private String property;

	OrderByCompany(String property) {
		this.property = property;
	}

	public static OrderByCompany myValueOf(String order) {
	    if (order == null) {
	        return OrderByCompany.ID;
	      }
	    
	      switch (order) {
	      case "name" :
	        return OrderByCompany.NAME;
	      default:
	        return OrderByCompany.ID;
	  }
	}

	@Override
	public String toString() {
		return property;
	}
	
}
