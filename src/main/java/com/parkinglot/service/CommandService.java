package com.parkinglot.service;

import java.util.List;
import java.util.logging.Logger;

import com.parkinglot.Application;
import com.parkinglot.exception.ApplicationException;
import com.parkinglot.model.Bike;
import com.parkinglot.model.ParkingDetail;

public class CommandService {
	Logger log = Logger.getLogger(Application.class.getName());
	private static CommandService commandService;
	//private static LocalTime time1=LocalTime.now();

	private CommandService() {

	}

	public static CommandService getInstance() {
		if (commandService == null) {
			commandService = new CommandService();

		}
		return commandService;
	}

	private enum CommandLine {
		CREATE_PARKING_LOT, PARK, LEAVE, STATUS, SLOT_NUMBER_FOR_REGISTRATION
	}

	private interface Command {
		public void validate();

		public String execute();
	}

	private CommandLine getCommandLine(String command) {
		CommandLine commandLine = null;

		if (command == null) {
			log.info("Not a valid Input");
		} else {
			String[] commandArray = command.split(" ");
			if ("".equals(commandArray[0])) {
				log.info("Empty Command");
			} else {
				try {
					commandLine = CommandLine.valueOf(commandArray[0]);
				} catch (Exception e) {
					log.info("Command not Available");
				}
			}
		}
		return commandLine;
	}

	public boolean execute(String executeString) {

		CommandLine commandLine = getCommandLine(executeString);

		if (commandLine == null) {
			return false;
		}
		String[] commandStringArray = executeString.split(" ");
		Command command;

		switch (commandLine) {
		case CREATE_PARKING_LOT:
			command = new CreateParkingLot(commandStringArray);
			break;
		case PARK:
			command = new Park(commandStringArray);
			break;
		case LEAVE:
			command = new Leave(commandStringArray);
			break;
		case STATUS:
			command = new StatusCommand(commandStringArray);
			break;

		case SLOT_NUMBER_FOR_REGISTRATION:
			command = new SlotNumber(commandStringArray);
			break;
		default:
			System.out.println("Unknown Command");
			return false;
		}

		try {
			command.validate();
		} catch (IllegalArgumentException e) {
			log.info("Please provide a valid argument");
			return false;
		}

		String output = "";
		try {
			output = command.execute();
		} catch (ApplicationException e) {
			log.info(e.getMessage());
		} catch (Exception e) {
			log.info("Unknown System Issue");
			e.printStackTrace();
			return false;
		}
		System.out.println(output);
		return true;
	}

	private class CreateParkingLot implements CommandService.Command {
		private String[] commandStringArray;

		CreateParkingLot(String[] s) {
			commandStringArray = s;
		}

		public void validate() {

			if (ParkinglotService.CountCalledCommands() > 1) {
				throw new ApplicationException("Parking Lot once created cannot be Override");
			}
			if (commandStringArray.length != 2) {
				throw new IllegalArgumentException("create_parking_lot command should have exactly 1 argument");
			}
		}

		public String execute() {
			int numberOfSlots = Integer.parseInt(commandStringArray[1]);
			TicketDetailService.createInstance(numberOfSlots);
			ParkinglotService.CountCalledCommands();
			return "Created a parking lot with " + commandStringArray[1] + " slots";
		}
	}

	private class Park implements Command {
		private String[] commandStringArray;

		Park(String[] s) {
			commandStringArray = s;
		}

		public void validate() {

			if (commandStringArray.length != 3) {
				throw new IllegalArgumentException("park command should have exactly 2 arguments");
			}
		}

		public String execute() {
			TicketDetailService ticketService = TicketDetailService.getInstance();
			int allocatedSlotNumber = ticketService
					.issueParkingTicket(new Bike(commandStringArray[1], commandStringArray[2]));
			return "Allocated slot number: " + allocatedSlotNumber;
		}
	}

	private class Leave implements Command {
		private String[] commandStringArray;

		Leave(String[] s) {
			commandStringArray = s;
		}

		public void validate() {
			if (commandStringArray.length != 2) {
				throw new IllegalArgumentException("leave command should have exactly 1 argument");
			}
		}

		public String execute() {
			TicketDetailService ticketService = TicketDetailService.getInstance();
			ticketService.exitVehicle(Integer.parseInt(commandStringArray[1]));
			return "Slot number " + commandStringArray[1] + " is free";
		}
	}

	private class StatusCommand implements Command {
		private String[] commandStringArray;

		StatusCommand(String[] s) {
			commandStringArray = s;
		}

		public void validate() {
			if (commandStringArray.length != 1) {
				throw new IllegalArgumentException("status command should have no arguments");
			}
		}

		public String execute() {
			TicketDetailService ticketService = TicketDetailService.getInstance();
			List<ParkingDetail> statusResponseList = ticketService.getStatus();

			StringBuilder outputStringBuilder = new StringBuilder("Slot No.    Registration No    Model ");
			for (ParkingDetail allocationStatus : statusResponseList) {
				outputStringBuilder.append("\n").append(allocationStatus);
			}
			return outputStringBuilder.toString();
		}
	}

	
	private class SlotNumber implements Command {
		private String[] commandStringArray;

		SlotNumber(String[] s) {
			commandStringArray = s;
		}

		public void validate() {
			if (commandStringArray.length != 2) {
				throw new IllegalArgumentException(
						"slot_number_for_registration_number command line should have exactly 1 argument");
			}
		}

		public String execute() {
			TicketDetailService ticketService = TicketDetailService.getInstance();
			int slotNumber = ticketService.getSlotNumberFromRegistrationNumber(commandStringArray[1]);
			return "" + slotNumber;
		}
	}
}
