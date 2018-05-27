package com.Server;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
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
	static int studentCount = 0;
	static int teacherCount = 0;
	int recordsCount;
	String location;

	public ServerImp(ServerCenterLocation loc) {
		logManager = new LogManager(loc.toString());
		recordsMap = new HashMap<>();
		this.recordsCount = 0;
		serverUDP = new ServerUDP(loc, logManager.logger,this);
		serverUDP.start();
		location = loc.toString();
		setIPAddress(loc);
	}

	private void setIPAddress(ServerCenterLocation loc) {
		switch (loc) {
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

		String teacherid = "TR" + (++teacherCount);
		teacher.setTeacherID(teacherid);
		teacher.setRecordID(teacherid);

		String key = teacher.getLastName().substring(0, 1);
		String message = addRecordToHashMap(key, teacher, null);

		System.out.println(recordsMap);

		System.out.println("teacher is added " + teacher + "with this key" + key);
		logManager.logger.log(Level.INFO, "Teacher record created " + teacherid);
		return teacherid;
	}

	private String addRecordToHashMap(String key, Teacher teacher, Student student) {

		String message = "Error";
		if (teacher != null) {
			List<Record> recordList = recordsMap.get(key);
			if (recordList != null) {
				recordList.add(teacher);
			} else {
				List<Record> records = new ArrayList<Record>();
				records.add(teacher);
				recordList = records;
			}
			recordsMap.put(key, recordList);
			message = "success";
		}

		if (student != null) {
			List<Record> recordList = recordsMap.get(key);
			if (recordList != null) {
				recordList.add(student);
			} else {
				List<Record> records = new ArrayList<Record>();
				records.add(student);
				recordList = records;
			}
			recordsMap.put(key, recordList);
			message = "success";
		}

		return message;
	}

	@Override
	public String createSRecord(Student student) {
		String studentid = "SR" + (studentCount + 1);
		student.setRecordID(studentid);
		student.setStudentID(studentid);

		String key = student.getLastName().substring(0, 1);
		String message = addRecordToHashMap(key, null, student);

		System.out.println(recordsMap);

		System.out.println("Student is added " + student + "with this key" + key);
		logManager.logger.log(Level.INFO, "Student record created " + studentid);

		return studentid;
	}

	@Override
	public String getRecordCount() {
		logManager.logger.log(Level.INFO, "Record Count successful");
		int totalrecCount = this.recordsMap.size();
		getOtherServersRecCount();
		totalrecCount+=this.recordsCount;
		return Integer.toString(totalrecCount);
	}

	private void getOtherServersRecCount() {
		System.out.println("Getting other servers count");
		String askCountPkt = "GET_RECORD_COUNT";
		byte[] sendData = new byte[1024];
		sendData = askCountPkt.getBytes();
		serverUDP.sendPacket = new DatagramPacket(sendData, sendData.length);
		try {
			sendToOtherServerLoc();	
			serverUDP.join();
			int count = serverUDP.getValue();
			System.out.println("Record count of other servers :: "+count);
			this.recordsCount = count;
			} catch (Exception e) {
				logManager.logger.log(Level.SEVERE, "Exception in sending GET_RECORD_COUNT Packet" + e.getMessage());
			}
	}

	private void sendToOtherServerLoc()throws Exception{
		switch(location) {
			case "MTL":
				// Set the destination host and port
				serverUDP.sendPacket.setAddress(InetAddress.getByName(Constants.LVL_SERVER_ADDRESS));
				serverUDP.sendPacket.setPort(Constants.UDP_PORT_NUM_LVL);
				serverUDP.serverSocket.send(serverUDP.sendPacket);
				
				// Set the destination host and port
				serverUDP.sendPacket.setAddress(InetAddress.getByName(Constants.DDO_SERVER_ADDRESS));
				serverUDP.sendPacket.setPort(Constants.UDP_PORT_NUM_DDO);
				serverUDP.serverSocket.send(serverUDP.sendPacket);
				break;
			case "LVL":
				// Set the destination host and port
				serverUDP.sendPacket.setAddress(InetAddress.getByName(Constants.MTL_SERVER_ADDRESS));
				serverUDP.sendPacket.setPort(Constants.UDP_PORT_NUM_MTL);
				serverUDP.serverSocket.send(serverUDP.sendPacket);
				
				// Set the destination host and port
				serverUDP.sendPacket.setAddress(InetAddress.getByName(Constants.DDO_SERVER_ADDRESS));
				serverUDP.sendPacket.setPort(Constants.UDP_PORT_NUM_DDO);
				serverUDP.serverSocket.send(serverUDP.sendPacket);
				break;
			case "DDO":
				// Set the destination host and port
				serverUDP.sendPacket.setAddress(InetAddress.getByName(Constants.MTL_SERVER_ADDRESS));
				serverUDP.sendPacket.setPort(Constants.UDP_PORT_NUM_MTL);
				serverUDP.serverSocket.send(serverUDP.sendPacket);
				
				// Set the destination host and port
				serverUDP.sendPacket.setAddress(InetAddress.getByName(Constants.LVL_SERVER_ADDRESS));
				serverUDP.sendPacket.setPort(Constants.UDP_PORT_NUM_LVL);
				serverUDP.serverSocket.send(serverUDP.sendPacket);
				break;
		}
	}

	@Override
	public String editRecord(String recordID, String fieldname, String newvalue) {
		String message = "found and edited";
		String type = recordID.substring(0, 2);

		if (type.equals("TR")) {
			editTRRecord(recordID, fieldname, newvalue);
		}

		if (type.equals("SR")) {
			editSRRecord(recordID, fieldname, newvalue);
		}

		logManager.logger.log(Level.INFO, "Record edit successful");

		return message;
	}

	private void editSRRecord(String recordID, String fieldname, String newvalue) {

		System.out.println(recordsMap);

		for (Entry<String, List<Record>> value : recordsMap.entrySet()) {

			List<Record> mylist = value.getValue();
			Optional<Record> record = mylist.stream().filter(x -> x.getRecordID().equals(recordID)).findFirst();

			if (record.isPresent() && fieldname.equals("CoursesRegistered")) {
				((Student) record.get()).setCoursesRegistered(newvalue);
				logManager.logger.log(Level.INFO, "Updated the records\t" + location);
			} else if (record.isPresent() && fieldname.equals("Status")) {
				((Student) record.get()).setStatus(newvalue);
				((Student) record.get()).setStatus(null);
				logManager.logger.log(Level.INFO, "Updated the records\t" + location);
			} else if (record.isPresent() && fieldname.equals("StatusDate")) {
				((Student) record.get()).setStatusDate(newvalue);
				logManager.logger.log(Level.INFO, "Updated the records\t" + location);
			} else {
				logManager.logger.log(Level.INFO, "Records not found\t" + location);
				System.out.println("Record not found");
			}
		}

		System.out.println(recordsMap);

	}

	private void editTRRecord(String recordID, String fieldname, String newvalue) {

		System.out.println(recordsMap);

		for (Entry<String, List<Record>> val : recordsMap.entrySet()) {

			List<Record> mylist = val.getValue();
			Optional<Record> record = mylist.stream().filter(x -> x.getRecordID().equals(recordID)).findFirst();

			System.out.println(record);

			if (record.isPresent() && fieldname.equals("Phone")) {
				((Teacher) record.get()).setPhone(newvalue);
				logManager.logger.log(Level.INFO, "Updated the records\t" + location);
			}

			else if (record.isPresent() && fieldname.equals("Specialization")) {
				((Teacher) record.get()).setSpecilization(newvalue);
				logManager.logger.log(Level.INFO, "Updated the records\t" + location);

			}

			else if (record.isPresent() && fieldname.equals("Location")) {
				((Teacher) record.get()).setLocation(newvalue);
				logManager.logger.log(Level.INFO, "Updated the records\t" + location);
			}

			else {
				logManager.logger.log(Level.INFO, "Records not found\t" + location);
				System.out.println("Record not found");
			}
		}

		System.out.println(recordsMap);

	}
}
