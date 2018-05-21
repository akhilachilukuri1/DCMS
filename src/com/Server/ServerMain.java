package com.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.Conf.Constants;
import com.Conf.ServerCenterLocation;

public class ServerMain {

	static ICenterServer stubMTL, stubLVL, stubDDO;
	static ServerImp serverMTL,serverLVL,serverDDO;

	public static void main(String args[]) {

		init();

		createRemoteObj();

		registerServers();

		System.out.println("Server Ready! Listening on port :: " + Constants.PORT_NUM);

	}

	public static void init(){
		serverMTL = new ServerImp(ServerCenterLocation.MTL);
		serverLVL = new ServerImp(ServerCenterLocation.LVL);
		serverDDO = new ServerImp(ServerCenterLocation.DDO);
	}
	
	public static void createRemoteObj() {
		try {
			stubMTL = (ICenterServer) UnicastRemoteObject.exportObject(serverMTL, Constants.PORT_NUM);
			stubLVL = (ICenterServer) UnicastRemoteObject.exportObject(serverLVL, Constants.PORT_NUM);
			stubDDO = (ICenterServer) UnicastRemoteObject.exportObject(serverDDO, Constants.PORT_NUM);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void registerServers() {
		// Bind the remote object's stub in the registry
		Registry registry;
		try {
			registry = LocateRegistry.createRegistry(Constants.PORT_NUM);
			registry.bind("MTL", stubMTL);
			registry.bind("LVL", stubLVL);
			registry.bind("DDO", stubDDO);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
