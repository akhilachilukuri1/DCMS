package com.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;

import com.Conf.Constants;
import com.Conf.LogManager;
import com.Conf.ServerCenterLocation;

public class ServerMain {

	static ICenterServer stubMTL, stubLVL, stubDDO;
	static ServerImp serverMTL,serverLVL,serverDDO;

	public static void main(String args[]) {

		init();

		createRemoteObj();

		registerServers();
		
		LogManager logManager = new LogManager("ServerGlobal");
		logManager.logger.log(Level.INFO, "Server Ready! Listening on port :: " + Constants.RMI_PORT_NUM_MTL);
		System.out.println("Server Ready! Listening on port :: " + Constants.RMI_PORT_NUM_MTL);
		System.out.println("Server Ready! Listening on port :: " + Constants.RMI_PORT_NUM_LVL);
		System.out.println("Server Ready! Listening on port :: " + Constants.RMI_PORT_NUM_DDO);
	}

	public static void init(){
		serverMTL = new ServerImp(ServerCenterLocation.MTL);
		serverLVL = new ServerImp(ServerCenterLocation.LVL);
		serverDDO = new ServerImp(ServerCenterLocation.DDO);
	}
	
	public static void createRemoteObj() {
		try {
			stubMTL = (ICenterServer) UnicastRemoteObject.exportObject(serverMTL, Constants.RMI_PORT_NUM_MTL);
			stubLVL = (ICenterServer) UnicastRemoteObject.exportObject(serverLVL, Constants.RMI_PORT_NUM_LVL);
			stubDDO = (ICenterServer) UnicastRemoteObject.exportObject(serverDDO, Constants.RMI_PORT_NUM_DDO);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void registerServers() {
		// Bind the remote object's stub in the registry
		Registry registryMTL,registryLVL,registryDDO;
		try {
			registryMTL = LocateRegistry.createRegistry(Constants.RMI_PORT_NUM_MTL);
			registryLVL = LocateRegistry.createRegistry(Constants.RMI_PORT_NUM_LVL);
			registryDDO = LocateRegistry.createRegistry(Constants.RMI_PORT_NUM_DDO);
			registryMTL.bind("MTL", stubMTL);
			registryLVL.bind("LVL", stubLVL);
			registryDDO.bind("DDO", stubDDO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
