package com.excilys.formation.dao;

public enum OrderByComputer {
	ID("id"), 
	NAME("name"), 
	INTRODUCED("introduced"), 
	DISCONTINUED("discontinued"),
	COMPANY("company_id");

	private String property;

	OrderByComputer(String property) {
		this.property = property;
	}
	
	public static OrderByComputer myValueOf(String order) {
	    if (order == null) {
	        return OrderByComputer.ID;
	      }
	    
	      switch (order) {
	      case "name" :
	        return OrderByComputer.NAME;
	      case "introduced" :
	        return OrderByComputer.INTRODUCED;
	      case "discontinued" :
	        return OrderByComputer.DISCONTINUED;
	      case "company" :
	        return OrderByComputer.COMPANY;
	      default:
	        return OrderByComputer.ID;
	  }
	}

	@Override
	public String toString() {
		return property;
	}
}
