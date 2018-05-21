package com.Client;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.Conf.Constants;
import com.Server.*;

public class ClientMain {

    private ClientMain() {}

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost",Constants.RMI_PORT_NUM);
            ICenterServer iCenterServerMTL = (ICenterServer) registry.lookup("MTL");
            ICenterServer iCenterServerLVL = (ICenterServer) registry.lookup("LVL");
            ICenterServer iCenterServerDDO = (ICenterServer) registry.lookup("DDO");
            String responseMTL = iCenterServerMTL.createTRecord("MTL");
            Integer responseCnt = iCenterServerMTL.getRecordCount();
            String responseLVL = iCenterServerLVL.createTRecord("LVL");
            String responseDDO = iCenterServerDDO.createTRecord("DDO");
            System.out.println(responseMTL+" "+responseLVL+" "+responseDDO);
            System.out.println(responseCnt);
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
