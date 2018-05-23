package com.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.Models.Student;
import com.Models.Teacher;

public interface ICenterServer extends Remote {

	// String sayHello() throws RemoteException;

	public String createTRecord(Teacher teacher) throws RemoteException;

	public String createSRecord(Student student) throws RemoteException;

	public String getRecordCount() throws RemoteException;

	public String editRecord(String recordID,String fieldname,String newvalue) throws RemoteException;
}
