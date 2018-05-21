package com.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICenterServer extends Remote {

	// String sayHello() throws RemoteException;

	public String createTRecord(String tRecord) throws RemoteException;

	public String createSRecord(String sRecord) throws RemoteException;

	public Integer getRecordCount() throws RemoteException;

	public String editRecord(String newRecord) throws RemoteException;
}
