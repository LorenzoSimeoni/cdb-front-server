package com.excilys.formation.controller;

public enum ViewName {
	DASHBOARD("dashboard"), 
	EDITCOMPUTER("editComputer"), 
	ADDCOMPUTER("addComputer"),
	NOTFOUND("404"),
	FORBIDDEN("403"),
	INTERNALSERVERERROR("500");

	private String property;

	ViewName(String property) {
		this.property = property;
	}

	@Override
	public String toString() {
		return property;
	}
}
