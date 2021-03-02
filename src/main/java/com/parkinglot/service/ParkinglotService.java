package com.parkinglot.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import com.parkinglot.Application;
import com.parkinglot.exception.ApplicationException;
import com.parkinglot.model.Slot;

public class ParkinglotService {
	Logger log = Logger.getLogger(Application.class.getName());
	private static ParkinglotService parkinglotService;
	private Map<Integer, Slot> slotMap;
	static int count = 0;

	public static int CountCalledCommands() {
		count++;
		return count;

	}

	protected ParkinglotService(int slotNumber) {
		slotMap = new HashMap<Integer, Slot>();
		for (int i = 1; i <= slotNumber; i++) {
			slotMap.put(i, new Slot(i));
		}
	}

	static ParkinglotService getInstance(int slotNumber) {
		 if(parkinglotService ==  null) {
			 parkinglotService = new ParkinglotService(slotNumber);
		}
		return parkinglotService;
	}

	int fillAvailableSlot() throws ApplicationException {
		int nextAvailableSlotNumber = -1;
		for (int i = 1; i <= slotMap.size(); i++) {
			Slot s = slotMap.get(i);
			if (s.status) {
				nextAvailableSlotNumber = s.slotNumber;
				s.status = false;
				break;
			}
		}
		if (nextAvailableSlotNumber != -1) {
			return nextAvailableSlotNumber;
		} else {
			throw new ApplicationException("Sorry, parking lot is full");
		}

	}
	
	int calculatePrice() {
		
		System.out.println("Enter the time taken");
		Scanner input = new Scanner(System.in);
		int time= input.nextInt();
//		LocalTime time2=LocalTime.now();
//		long time = time2 - time1; 
		if(time<0)
			throw new ApplicationException("Enter a valid time");
		else if(time > 0 & time < 4)
			return 20;
		else if(time>=4 & time<12)
			return 50;
		else if(time>=12 & time<24)
			return 100;
	    else if(time>=24)
	    	return 300;
	    else
	    	throw new ApplicationException("Enter the time");
		
	}

	void emptySlot(int slotNumber) {
		if (slotMap.containsKey(slotNumber)) {
			if (slotMap.get(slotNumber).status) {
				throw new IllegalStateException("The slot is already empty");
			} else {
				slotMap.get(slotNumber).status = true;
			}
		} else {
			throw new IllegalStateException("The slot number is invalid");
		}
	}
}
