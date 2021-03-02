package com.parkinglot.model;

public class ParkingDetail {
	private int slotNumber;
	private String registrationNumber;
	private String model;

	public ParkingDetail() {
	}

	public ParkingDetail(int slotNumber, String registrationNumber, String model) {
		super();
		this.slotNumber = slotNumber;
		this.registrationNumber = registrationNumber;
		this.model = model;
	}

	public int getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(int slotNumber) {
		this.slotNumber = slotNumber;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
    public String toString(){
        return slotNumber + "           " + registrationNumber + "      " + model;
    }

}
