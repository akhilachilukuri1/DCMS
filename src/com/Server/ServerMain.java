package com.Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.Conf.Constants;

public class ServerMain {
	
public static void main(String args[]) {
        
        try {
            ServerImp serverMTL = new ServerImp();
            ServerImp serverLVL = new ServerImp();
            ServerImp serverDDO = new ServerImp();

            ICenterServer stubMTL = (ICenterServer) UnicastRemoteObject.exportObject(serverMTL, Constants.PORT_NUM);
            ICenterServer stubLVL = (ICenterServer) UnicastRemoteObject.exportObject(serverLVL, Constants.PORT_NUM);
            ICenterServer stubDDO = (ICenterServer) UnicastRemoteObject.exportObject(serverDDO, Constants.PORT_NUM);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(Constants.PORT_NUM);
            registry.bind("MTL", stubMTL);
            registry.bind("LVL", stubLVL);
            registry.bind("DDO", stubDDO);
            System.err.println("3 Servers Ready! Listening on port :: "+Constants.PORT_NUM);
        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
        }
 }

}
