package com.Conf;
//setting the port number,IP address,server count and logs path
public class Constants {
	public static int RMI_PORT_NUM_MTL = 6666;
	public static int RMI_PORT_NUM_LVL = 1099;
	public static int RMI_PORT_NUM_DDO = 1234;

	public static int UDP_PORT_NUM_MTL = 9999;
	public static int UDP_PORT_NUM_LVL = 1111;
	public static int UDP_PORT_NUM_DDO = 7777;

	public static String MTL_SERVER_ADDRESS = "localhost";
	public static String LVL_SERVER_ADDRESS = "localhost";
	public static String DDO_SERVER_ADDRESS = "localhost";

	public static int TOTAL_SERVERS_COUNT = 3;
	
	public static String PROJECT_DIR = "F:\\DCMS\\";
	public static String LOG_DIR = PROJECT_DIR+"Logs\\";

}