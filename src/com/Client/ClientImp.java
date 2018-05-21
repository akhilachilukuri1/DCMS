package com.Client;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.logging.Level;

import com.Conf.Constants;
import com.Conf.LogManager;
import com.Conf.ServerCenterLocation;
import com.Models.Student;
import com.Models.Teacher;
import com.Server.ICenterServer;

public class ClientImp {
	static Registry registry;
	
	
	 LogManager logManager=null;
	ICenterServer iCenterServer=null;
static
{
	try {
		registry = LocateRegistry.getRegistry("localhost",Constants.PORT_NUM);
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
ClientImp(ServerCenterLocation location,String ManagerID) throws AccessException, RemoteException, NotBoundException
{
	iCenterServer=(ICenterServer) registry.lookup(location.toString());
	logManager=new LogManager(ManagerID);
}
public  String createTRecord(String firstName,String lastName,String address,String phone,String specilization,String location ) {
	logManager.logger.log(Level.INFO, "Initiating T record object creation request");
	String result="";
	Teacher teacher=new Teacher();
	teacher.setFirstName(firstName);
	teacher.setLastName(lastName);
	teacher.setAddress(address);
	teacher.setPhone(phone);
	teacher.setSpecilization(specilization);
	teacher.setLocation(location);
	String teacherID="";
	try {
		teacherID = iCenterServer.createTRecord(teacher);
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(teacherID!=null)
	result="Teacher record is created and assigned with "+teacherID;
	else
	result="Teacher record is not created";
	logManager.logger.log(Level.INFO,result );
	return result;
}
public  String createSRecord(String firstName,String lastName,String coursesRegistered,boolean status,Date statusDate) {
	logManager.logger.log(Level.INFO, "Initiating S record object creation request");
	String result="";
	Student student=new Student();
	student.setFirstName(firstName);
	student.setLastName(lastName);
	student.setStatus(status);
	student.setStatusDate(statusDate);
	String studentID="";
	try {
		studentID = iCenterServer.createSRecord(student);
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(studentID!=null)
		result="student record is created and assigned with "+studentID;
		else
		result="student record is not created";
	logManager.logger.log(Level.INFO,result);
	return result;
}
public String getRecordCounts()
{
	String count="";
	logManager.logger.log(Level.INFO, "Initiating record count request");
	try {
		count=iCenterServer.getRecordCount();
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	logManager.logger.log(Level.INFO, "received....count as follows");
	logManager.logger.log(Level.INFO,count);
	return count;
}
public String editRecord(String recordID,String fieldname,String newvalue)
{
	String message="";
	logManager.logger.log(Level.INFO, "Initiating the record edit request");
	try {
		iCenterServer.editRecord(recordID,fieldname,newvalue);
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	logManager.logger.log(Level.INFO,message);
	return message;
}
}
