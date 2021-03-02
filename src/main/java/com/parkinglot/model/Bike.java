package com.parkinglot.model;

public class Bike implements Vehicle {
	private String registrationNumber;
	private String model;

	public Bike(String registrationNumber, String model) {

		if (registrationNumber == null || model == null) {
			throw new IllegalArgumentException("Missing Data regarding Registration Number  && Model");
		} else {
			this.registrationNumber = registrationNumber;
			this.model = model;
		}
	}

	public String registrationNumber() {
		return this.registrationNumber;
	}

	public String model() {
		return this.model;
	}

}
