package com.parkinglot.model;

public class Ticket {

	public int slotNumber;
	public Vehicle vehicle;
	public int time;

	public Ticket(int slotNumber, Vehicle vehicle) {
		this.slotNumber = slotNumber;
		this.vehicle = vehicle;
	}

	public Ticket(int slotNumber, Vehicle vehicle, int time) {
		this.slotNumber = slotNumber;
		this.vehicle = vehicle;
		this.time = time;
	}

	public int getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(int slotNumber) {
		this.slotNumber = slotNumber;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
}
