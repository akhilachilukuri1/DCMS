package com.Server;

import java.rmi.RemoteException;

public class ServerImp implements ICenterServer {
	
	public ServerImp(){
		
	}

	@Override
	public String createTRecord(String tRecord) {
		// TODO Auto-generated method stub
		return tRecord;
	}

	@Override
	public String createSRecord(String sRecord) {
		// TODO Auto-generated method stub
		return sRecord;
	}

	@Override
	public Integer getRecordCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String editRecord(String newRecord) {
		// TODO Auto-generated method stub
		return newRecord;
	}

//	@Override
//	public String sayHello() throws RemoteException {
//		// TODO Auto-generated method stub
//		return "hello";
//	}
	
}
