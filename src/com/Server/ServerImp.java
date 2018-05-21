package com.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.Conf.Constants;
import com.Conf.LogManager;
import com.Conf.ServerCenterLocation;

public class ServerImp implements ICenterServer {

	HashMap<String, String> recordsMap;
	LogManager logManager;
	ServerUDP serverUDP;
	String IPaddress;
	
	public ServerImp(ServerCenterLocation loc) {
		recordsMap = new HashMap<>();
		logManager = new LogManager(loc.toString());
		
		serverUDP = new ServerUDP(loc,logManager.logger);
		serverUDP.start();
		
		setIPAddress(loc);
		
	}
	
	private void setIPAddress(ServerCenterLocation loc){
		switch (loc){
		case MTL:
			IPaddress = Constants.MTL_SERVER_ADDRESS;
			break;
		case LVL:
			IPaddress = Constants.LVL_SERVER_ADDRESS;
			break;
		case DDO:
			IPaddress = Constants.DDO_SERVER_ADDRESS;
			break;
		}
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
		int totalrecCount = this.recordsMap.size();
		getOtherServersRecCount();
		return 1;
	}

	private void getOtherServersRecCount() {
		String askCountPkt = "GET_RECORD_COUNT";
		byte[] sendData = new byte[1024];
		sendData = askCountPkt.getBytes();
		serverUDP.sendPacket = new DatagramPacket(sendData, sendData.length);
		try {
			// Set the destination host and port
			serverUDP.sendPacket.setAddress(InetAddress.getByName(this.IPaddress));
			serverUDP.sendPacket.setPort(this.serverUDP.udpPortNum);
			serverUDP.serverSocket.send(serverUDP.sendPacket);
			
		} catch (Exception e) {
			logManager.logger.log(Level.SEVERE, "Exception in sending GET_RECORD_COUNT Packet" + e.getMessage());
		}
	}

	@Override
	public String editRecord(String newRecord) {
		logManager.logger.log(Level.INFO, "Record edit successful");
		return newRecord;
	}
}
