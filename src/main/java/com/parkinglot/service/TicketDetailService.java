package com.parkinglot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.parkinglot.Application;
import com.parkinglot.exception.ApplicationException;
import com.parkinglot.model.ParkingDetail;
import com.parkinglot.model.Ticket;
import com.parkinglot.model.Vehicle;

public class TicketDetailService {
	static Logger log = Logger.getLogger(Application.class.getName());
	private static TicketDetailService ticketDetailService;
	private static Map<Integer, Ticket> ticketMap;
	private ParkinglotService parkinglotService;

	
	TicketDetailService(ParkinglotService parkinglotService) {
		this.parkinglotService = parkinglotService;
		ticketMap = new HashMap<Integer, Ticket>();
	}

	
	public static TicketDetailService createInstance(int numberOfSlot) {
		if (numberOfSlot < 1) {
			log.warning("Number of slots cannot be less than 1");
		}
		if (ticketDetailService == null) {
			ParkinglotService parkingLot = ParkinglotService.getInstance(numberOfSlot);
			ticketDetailService = new TicketDetailService(parkingLot);
		}
		return ticketDetailService;
	}

	
	public static TicketDetailService getInstance() {
		if (ticketDetailService == null) {
			throw new IllegalStateException("Parking Lot is not initialized");
		}
		return ticketDetailService;
	}

	

	public int issueParkingTicket(Vehicle vehicle) {
		if (vehicle == null) {
			throw new IllegalArgumentException("Vehicle cannot be null");
		}

		boolean check = true;

		for (Ticket ticket : ticketMap.values()) {
			if (vehicle.registrationNumber().equals(ticket.vehicle.registrationNumber())) {
				check = false;
			}
		}
		if (check) {
			int assignedSlotNumber = parkinglotService.fillAvailableSlot();
			Ticket ticket = new Ticket(assignedSlotNumber, vehicle);

			ticketMap.put(assignedSlotNumber, ticket);
			return assignedSlotNumber;
		} else {
			throw new ApplicationException("Same Registration Number Vehicles are not allowed");
		}

	}

	
	public void exitVehicle(int slotNumber) {
		if (ticketMap.containsKey(slotNumber)) {
			int price = parkinglotService.calculatePrice();
			System.out.println("Price is "+ price);
			parkinglotService.emptySlot(slotNumber);
			ticketMap.remove(slotNumber);
			return;
		} else {
			throw new ApplicationException("No vehicle found at given slot. Incorrect input");
		}
	}
	
	public int getSlotNumberFromRegistrationNumber(String registrationNumber) {
		if (registrationNumber == null) {
			throw new IllegalArgumentException("registrationNumber cannot be null");
		}
		for (Ticket ticket : ticketMap.values()) {
			if (registrationNumber.equals(ticket.vehicle.registrationNumber())) {
				return ticket.slotNumber;
			}
		}

		throw new ApplicationException("Not found");
	}

	public List<ParkingDetail> getStatus() {
		List<ParkingDetail> parkingDetailList = new ArrayList<ParkingDetail>();
		for (Ticket ticket : ticketMap.values()) {
			parkingDetailList.add(
					new ParkingDetail(ticket.slotNumber, ticket.vehicle.registrationNumber(), ticket.vehicle.model()));
		}
		return parkingDetailList;
	}

}
