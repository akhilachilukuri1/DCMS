package com.Server;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.rmi.RemoteException;
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
	String recordsCount;
	String location;

	public ServerImp(ServerCenterLocation loc) {
		logManager = new LogManager(loc.toString());
		recordsMap = new HashMap<>();
		serverUDP = new ServerUDP(loc, logManager.logger, this);
		System.out.println("Passing server instance :: "+this + loc);
		serverUDP.start();
		location = loc.toString();
		setIPAddress(loc);
	}

	// getting the location instance
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

	// Creating teacher records
	@Override
	public String createTRecord(Teacher teacher) {

		String teacherid = "TR" + (++teacherCount);
		teacher.setTeacherID(teacherid);
		teacher.setRecordID(teacherid);

		String key = teacher.getLastName().substring(0, 1);
		// adding the teacher record to HashMap
		String message = addRecordToHashMap(key, teacher, null);

		System.out.println(recordsMap);

		System.out.println("teacher is added " + teacher + "with this key" + key);
		logManager.logger.log(Level.INFO, "Teacher record created " + teacherid);
		return teacherid;
	}

	// adding the records into HashMap
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

	// creating student record
	@Override
	public String createSRecord(Student student) {
		String studentid = "SR" + (studentCount + 1);
		student.setRecordID(studentid);
		student.setStudentID(studentid);

		String key = student.getLastName().substring(0, 1);
		// adding the student record to HashMap
		String message = addRecordToHashMap(key, null, student);

		System.out.println(recordsMap);

		System.out.println("Student is added " + student + "with this key" + key);
		logManager.logger.log(Level.INFO, "Student record created " + studentid);

		return studentid;
	}

	// Get the record count in all the hashmaps
	@Override
	public String getRecordCount() {
		logManager.logger.log(Level.INFO, "Record Count successful");
		int t = 0;// this.recordsMap.size();

		// gettin the record count for current manager location instance
		for (Entry<String, List<Record>> entry : recordsMap.entrySet()) {
			List<Record> lst = entry.getValue();
			long l = lst.stream().distinct().count();
			t = (int) (t + l);

		}
		
		// getting record count for the other two location instances
		getOtherServersRecCount();
		String totalrecCount;
		if (this.recordsCount != null)
			totalrecCount = this.recordsCount + location + " " + t;
		else
			totalrecCount = location + " " + t;
		return totalrecCount;
	}

	// getting record count for the other two location instances using UDP

	private synchronized void getOtherServersRecCount() {
		System.out.println("Getting other servers count");
		String askCountPkt = "GET_RECORD_COUNT";
		byte[] sendData = new byte[1024];
		sendData = askCountPkt.getBytes();
		serverUDP.sendPacket = new DatagramPacket(sendData, sendData.length);
		try {
			sendToOtherServerLoc();
			serverUDP.join();
			String recCount = serverUDP.getValue();
			System.out.println("Record count of other servers :: " + recCount);
			this.recordsCount = recCount;
		} catch (Exception e) {
			logManager.logger.log(Level.SEVERE, "Exception in sending GET_RECORD_COUNT Packet" + e.getMessage());
		}
	}

	private synchronized void sendToOtherServerLoc() throws Exception {
		switch (location) {
		case "MTL":
			// Set the destination host and port
			System.out.println("Sending pkt from MTL  to LVL:: "+serverUDP.sendPacket);
			serverUDP.sendPacket.setAddress(InetAddress.getByName(Constants.LVL_SERVER_ADDRESS));
			serverUDP.sendPacket.setPort(Constants.UDP_PORT_NUM_LVL);
			serverUDP.serverSocket.send(serverUDP.sendPacket);

			// Set the destination host and port
			System.out.println("Sending pkt from MTL  to DDO:: "+serverUDP.sendPacket);
			serverUDP.sendPacket.setAddress(InetAddress.getByName(Constants.DDO_SERVER_ADDRESS));
			serverUDP.sendPacket.setPort(Constants.UDP_PORT_NUM_DDO);
			serverUDP.serverSocket.send(serverUDP.sendPacket);
			break;
		case "LVL":
			// Set the destination host and port
			serverUDP.sendPacket.setAddress(InetAddress.getByName(Constants.MTL_SERVER_ADDRESS));
			serverUDP.sendPacket.setPort(Constants.UDP_PORT_NUM_MTL);
			System.out.println("Sending pkt from LVL  to MTL:: "+serverUDP.sendPacket);
			serverUDP.serverSocket.send(serverUDP.sendPacket);

			// Set the destination host and port
			System.out.println("Sending pkt from LVL  to DDO:: "+serverUDP.sendPacket);
			serverUDP.sendPacket.setAddress(InetAddress.getByName(Constants.DDO_SERVER_ADDRESS));
			serverUDP.sendPacket.setPort(Constants.UDP_PORT_NUM_DDO);
			serverUDP.serverSocket.send(serverUDP.sendPacket);
			break;
		case "DDO":
			// Set the destination host and port
			System.out.println("Sending pkt from DDO  to MTL:: "+serverUDP.sendPacket);
			serverUDP.sendPacket.setAddress(InetAddress.getByName(Constants.MTL_SERVER_ADDRESS));
			serverUDP.sendPacket.setPort(Constants.UDP_PORT_NUM_MTL);
			serverUDP.serverSocket.send(serverUDP.sendPacket);

			// Set the destination host and port
			System.out.println("Sending pkt from DDO  to LVL:: "+serverUDP.sendPacket);
			serverUDP.sendPacket.setAddress(InetAddress.getByName(Constants.LVL_SERVER_ADDRESS));
			serverUDP.sendPacket.setPort(Constants.UDP_PORT_NUM_LVL);
			serverUDP.serverSocket.send(serverUDP.sendPacket);
			break;
		}
	}

	// Editing student and teacher records
	@Override
	public String editRecord(String recordID, String fieldname, String newvalue) {
		String type = recordID.substring(0, 2);

		if (type.equals("TR")) {
			return editTRRecord(recordID, fieldname, newvalue);
		}

		else if (type.equals("SR")) {
			return editSRRecord(recordID, fieldname, newvalue);
		}

		logManager.logger.log(Level.INFO, "Record edit successful");

		return "Operation not performed!";
	}

	// Editing students records
	private String editSRRecord(String recordID, String fieldname, String newvalue) {

		System.out.println(recordsMap);

		for (Entry<String, List<Record>> value : recordsMap.entrySet()) {

			List<Record> mylist = value.getValue();
			Optional<Record> record = mylist.stream().filter(x -> x.getRecordID().equals(recordID)).findFirst();
			if (record.isPresent()) {
				if (record.isPresent() && fieldname.equals("Status")) {
					((Student) record.get()).setStatus(newvalue);
					logManager.logger.log(Level.INFO, "Updated the records\t" + location);
					return "Updated record with status :: "+newvalue;
					// ((Student) record.get()).setStatus(null);
				} else if (record.isPresent() && fieldname.equals("StatusDate")) {
					((Student) record.get()).setStatusDate(newvalue);
					logManager.logger.log(Level.INFO, "Updated the records\t" + location);
					return "Updated record with status date :: "+newvalue;
				} 
			}
		}
		return "Record with "+recordID+"not found!";
	}

	// Editing Teacher records
	private String editTRRecord(String recordID, String fieldname, String newvalue) {
		for (Entry<String, List<Record>> val : recordsMap.entrySet()) {

			List<Record> mylist = val.getValue();
			Optional<Record> record = mylist.stream().filter(x -> x.getRecordID().equals(recordID)).findFirst();

			System.out.println(record);
			if (record.isPresent()) {
				if (record.isPresent() && fieldname.equals("Phone")) {
					((Teacher) record.get()).setPhone(newvalue);
					logManager.logger.log(Level.INFO, "Updated the records\t" + location);
					return "Updated record with Phone :: "+newvalue;
				}

				else if (record.isPresent() && fieldname.equals("Address")) {
					((Teacher) record.get()).setAddress(newvalue);
					logManager.logger.log(Level.INFO, "Updated the records\t" + location);
					return "Updated record with address :: "+newvalue;
				}

				else if (record.isPresent() && fieldname.equals("Location")) {
					((Teacher) record.get()).setLocation(newvalue);
					logManager.logger.log(Level.INFO, "Updated the records\t" + location);
					return "Updated record with location :: "+newvalue;
				} 
			}
		}
		return "Record with "+recordID+" not found";
	}

	// Editing the Students record for courses Registered.
	@Override
	public String editRecordForCourses(String recordID, String fieldName, List<String> newCourses)
			throws RemoteException {
		for (Entry<String, List<Record>> value : recordsMap.entrySet()) {

			List<Record> mylist = value.getValue();
			Optional<Record> record = mylist.stream().filter(x -> x.getRecordID().equals(recordID)).findFirst();
			if (record.isPresent() && fieldName.equals("CoursesRegistered")) {
				((Student) record.get()).setCoursesRegistered(newCourses);
				logManager.logger.log(Level.INFO, "Updated the records\t" + location);
			}
		}
		System.out.println(recordsMap);
		return null;
	}
}
