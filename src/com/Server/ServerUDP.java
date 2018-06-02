package com.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.Conf.Constants;
import com.Conf.LogManager;
import com.Conf.ServerCenterLocation;
import com.Models.Record;


public class ServerUDP extends Thread {
	DatagramSocket serverSocket;
	DatagramPacket receivePacket;
	DatagramPacket sendPacket;
	int udpPortNum;
	ServerCenterLocation location;
	Logger loggerInstance;
	volatile String recordCount;
	volatile int c = 0;
	ServerImp server;
	
	public ServerUDP(ServerCenterLocation loc, Logger logger, ServerImp serverImp) {
		location = loc;
		loggerInstance = logger;
		this.server = serverImp;
		try {
			switch (loc) {
			case MTL:
				serverSocket = new DatagramSocket(Constants.UDP_PORT_NUM_MTL);
				udpPortNum = Constants.UDP_PORT_NUM_MTL;
				logger.log(Level.INFO, "MTL UDP Server Started");
				break;
			case LVL:
				serverSocket = new DatagramSocket(Constants.UDP_PORT_NUM_LVL);
				udpPortNum = Constants.UDP_PORT_NUM_LVL;
				logger.log(Level.INFO, "LVL UDP Server Started");
				break;
			case DDO:
				serverSocket = new DatagramSocket(Constants.UDP_PORT_NUM_DDO);
				udpPortNum = Constants.UDP_PORT_NUM_DDO;
				logger.log(Level.INFO, "DDO UDP Server Started");
				break;
			}

		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	@Override
	public void run() {
		byte[] receiveData,responseData;
		//call for only 2 other server requests
		while (c!=Constants.TOTAL_SERVERS_COUNT-1) {
			try {
				receiveData = new byte[1024];
				responseData = new byte[1024];
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				String inputPkt = new String(receivePacket.getData()).trim();
				
				//getting record counts
				if (inputPkt.equals("GET_RECORD_COUNT")) {
					
					int count =0;
					for (Entry<String, List<Record>> entry : server.recordsMap.entrySet()) {
						List<Record> lst = entry.getValue();
						long l = lst.stream().distinct().count();
						count = (int) (count + l);

					}

					
					System.out.println("GET RECORD COUNT :: "+count+" records in "+location);	
					responseData = (location.toString() +" "+ Integer.toString(count)).getBytes();
					serverSocket.send(new DatagramPacket(responseData, responseData.length, receivePacket.getAddress(),
                            receivePacket.getPort()));
				}else{
					System.out.println("Received pkt :: "+inputPkt+" in UDP loc :: "+location);
					if(recordCount!=null){
						recordCount = recordCount + inputPkt +",";
					}else{
						recordCount = inputPkt +",";
					}
					c++;
				}				
				loggerInstance.log(Level.INFO, "Received " + inputPkt + " from " + location);
			} catch (Exception e) {
				System.out.println("Exception in UDP Server thread ::"+e.getMessage());
			}
		}
	}
	
	public String getValue(){
		System.out.println("========"+recordCount);
		return recordCount;
	}
}
