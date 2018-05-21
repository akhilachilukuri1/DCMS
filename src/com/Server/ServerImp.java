package com.Server;

import java.util.HashMap;
import java.util.logging.Level;
import com.Conf.LogManager;
import com.Conf.ServerCenterLocation;
import com.Models.Student;
import com.Models.Teacher;

public class ServerImp implements ICenterServer {
	
	HashMap<String,String> recordsMap;
	LogManager logManager;
	static int studentCount=0;
	static int teacherCount=0;
	public ServerImp(ServerCenterLocation loc) {
		recordsMap = new HashMap<>();
		logManager = new LogManager(loc.toString());
	}

	@Override
	public String createTRecord(Teacher teacher) {
		String teacherid="TR"+(teacherCount+1);
		teacher.setTeacherID(teacherid);
		logManager.logger.log(Level.INFO, "Teacher record is assigned with "+teacherid);
		logManager.logger.log(Level.INFO, "Create T record successful");
		return teacherid;
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
		return "MTL x LVL x DDO x";
	}

	@Override
	public String editRecord(String recordID,String fieldname,String newvalue) {
		String message="found and edited";
		logManager.logger.log(Level.INFO, "Record edit successful");
		return message;
	}

	// @Override
	// public String sayHello() throws RemoteException {
	// // TODO Auto-generated method stub
	// return "hello";
	// }

}
