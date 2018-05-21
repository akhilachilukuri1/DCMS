package com.Server;

import java.util.HashMap;
import java.util.logging.Level;
import com.Conf.LogManager;
import com.Conf.ServerCenterLocation;

public class ServerImp implements ICenterServer {
	
	HashMap<String,String> recordsMap;
	LogManager logManager;
	public ServerImp(ServerCenterLocation loc) {
		recordsMap = new HashMap<>();
		logManager = new LogManager(loc.toString());
	}

	@Override
	public String createTRecord(String tRecord) {
		logManager.logger.log(Level.INFO, "Create T record successful");
		return tRecord;
	}

	@Override
	public String createSRecord(String sRecord) {
		logManager.logger.log(Level.INFO, "Create S record successful");
		return sRecord;
	}

	@Override
	public Integer getRecordCount() {
		logManager.logger.log(Level.INFO, "Record Count successful");
		return 1;
	}

	@Override
	public String editRecord(String newRecord) {
		logManager.logger.log(Level.INFO, "Record edit successful");
		return newRecord;
	}

	// @Override
	// public String sayHello() throws RemoteException {
	// // TODO Auto-generated method stub
	// return "hello";
	// }

}
