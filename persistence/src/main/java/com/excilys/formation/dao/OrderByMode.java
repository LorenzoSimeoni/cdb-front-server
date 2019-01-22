package com.excilys.formation.dao;

public enum OrderByMode {
	ASCENDING("ASC"), DESCENDING("DESC");

	private String mode;

	OrderByMode(String mode) {
		this.mode = mode;
	}

	public static OrderByMode myValueOf(String mode) {
		if (mode == null) {
			return OrderByMode.ASCENDING;
		}

		switch (mode) {
		case "asc":
			return OrderByMode.ASCENDING;
		case "desc":
			return OrderByMode.DESCENDING;
		default:
			return OrderByMode.ASCENDING;
		}
	}

	@Override
	public String toString() {
		return mode;
	}
}
