package com.Conf;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogManager {
	public Handler fileHandler = null;
	public Logger logger;
	
	public LogManager(String serverName) {
        
		logger = Logger.getLogger(serverName);
		try {
			//consoleHandler = new ConsoleHandler();
			fileHandler = new FileHandler(Constants.LOG_DIR+serverName + ".log",true);
			
			SimpleFormatter formatter = new SimpleFormatter();
	        fileHandler.setFormatter(formatter);
	        
	        logger.setUseParentHandlers(false);

			//logger.addHandler(consoleHandler);
			logger.addHandler(fileHandler);	
			
			//consoleHandler.setLevel(Level.ALL);
			//fileHandler.setLevel(Level.ALL);
			logger.setLevel(Level.INFO);	
			
		} catch (Exception e) {
			logger.log(Level.SEVERE,"Exception in logger :: "+e.getMessage());
		}
	}
}
