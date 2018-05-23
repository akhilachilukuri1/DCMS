package com.Server;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import com.Conf.Constants;
import com.Conf.LogManager;
import com.Conf.ServerCenterLocation;
import com.Models.Record;
import com.Models.Student;
import com.Models.Teacher;

public class ServerImp implements ICenterServer {
	LogManager logManager;
	ServerUDP serverUDP;
	String IPaddress;
	HashMap<String, List<Record>> recordsMap;
	static int studentCount=0;
	static int teacherCount=0;
	String location;
	
	public ServerImp(ServerCenterLocation loc) {
		logManager = new LogManager(loc.toString());
		recordsMap = new HashMap<>();
		serverUDP = new ServerUDP(loc,logManager.logger);
		serverUDP.start();
		location = loc.toString();
		
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
	public String createTRecord(Teacher teacher) {
		
		String teacherid ="TR"+(++teacherCount);
		teacher.setTeacherID(teacherid);
		
		String key = teacher.getLastName().substring(0, 1);
		addRecordToHashMap(key, teacher, null);
		
		System.out.println("teacher is added "+teacher+ "with this key" +key);
		logManager.logger.log(Level.INFO, "Teacher record created "+teacherid);
		return teacherid;
	}

	private void addRecordToHashMap(String key,Teacher teacher,Student student){
		List<Record> recordList = recordsMap.get(key);
		if (recordList != null) {
			recordList.add(teacher);
		} else {
			List<Record> records = new ArrayList<Record>();
			records.add(teacher);
			recordList = records;
		}
		System.out.println(recordList);
		recordsMap.put(key, recordList);
	}
	
	@Override
	public String createSRecord(Student student) {
		String studentid="SR"+(studentCount+1);
		student.setStudentID(studentid);
		logManager.logger.log(Level.INFO, "Teacher record is assigned with "+studentid);
		logManager.logger.log(Level.INFO, "Create S record successful");
		return studentid;
	}

	@Override
	public String getRecordCount() {
		logManager.logger.log(Level.INFO, "Record Count successful");
		int totalrecCount = this.recordsMap.size();
		getOtherServersRecCount();
		return "";
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
	public String editRecord(String recordID,String fieldname,String newvalue) {
		String message="found and edited";
		logManager.logger.log(Level.INFO, "Record edit successful");
		return message;
	}
}
