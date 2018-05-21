package com.Conf;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogManager {
	public Handler consoleHandler = null;
	public Handler fileHandler = null;
	public Logger logger;
	
	public LogManager(String serverName) {
		logger = Logger.getLogger(serverName);
		try {
			consoleHandler = new ConsoleHandler();
			fileHandler = new FileHandler(serverName + ".log");
			
			logger.addHandler(consoleHandler);
			logger.addHandler(fileHandler);
			
			consoleHandler.setLevel(Level.ALL);
			fileHandler.setLevel(Level.ALL);
			logger.setLevel(Level.ALL);
			
			logger.config("Logger configuration done!");
			
		} catch (Exception e) {
			logger.log(Level.SEVERE,"Exception in logger :: "+e.getMessage());
		}
	}
}
