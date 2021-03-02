package com.parkinglot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import com.parkinglot.service.CommandService;

public class Application {

	public static void main(String[] args) throws Exception {
		Logger log = Logger.getLogger(Application.class.getName());
		log.info("Logger Name: "+log.getName());
		CommandService commandService = CommandService.getInstance();
		BufferedReader bufferedReader;

		if (args.length == 0) {
			// Input Command Line Reader
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				String commandText = bufferedReader.readLine();
				if (commandText == null || "exit".equalsIgnoreCase(commandText)) {
					break;
				} else {
					boolean executionSuccess = commandService.execute(commandText);

					if (!executionSuccess) {
						break;
					}
				}

			}
		}
	}
}
