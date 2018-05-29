package com.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Conf.Constants;
import com.Conf.ServerCenterLocation;
import com.Server.*;

public class ClientMain {

	private ClientMain() {
	}

	public static void main(String[] args) throws IOException, NotBoundException {
		while (true) {
			ClientImp client = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("enter the managerID");
			String managerID = br.readLine();
			if (managerID.contains("MTL")) {
				client = new ClientImp(ServerCenterLocation.MTL, managerID);
			} else if (managerID.contains("LVL")) {
				client = new ClientImp(ServerCenterLocation.LVL, managerID);
			} else if (managerID.contains("DDO")) {
				client = new ClientImp(ServerCenterLocation.DDO, managerID);
			} else {
				System.out.println("wrong manager ID.please enter again");
				continue;
			}
			int i = 1;
			while (i != 0) {
				System.out.println("choose the operation");
				System.out.println("1) Create the Teacher record");
				System.out.println("2) Create the Student record");
				System.out.println("3) Get the record count");
				System.out.println("4) Edit the record");
				System.out.println("5) Logout manager");
				Integer choice = Integer.parseInt(br.readLine());
				switch (choice) {
				case 1:
					System.out.println("Enter the first name of the teacher");
					String firstNameT = br.readLine();
					System.out.println("Enter the last name of the teacher");
					String lastNameT = br.readLine();
					System.out.println("Enter the address of the teacher");
					String addressT = br.readLine();
					System.out.println("Enter the phone number of the teacher");
					String phoneT = br.readLine();
					System.out.println("Enter the specilization of the teacher");
					String specilizationT = br.readLine();
					System.out.println("Enter the location of the teacher");
					String locationT = br.readLine();
					System.out.println(
							client.createTRecord(firstNameT, lastNameT, addressT, phoneT, specilizationT, locationT));
					break;
				case 2:
					System.out.println("Enter the first name of the student");
					String firstNameS = br.readLine();
					System.out.println("Enter the last name of the student");
					String lastNameS = br.readLine();
					System.out.println("Enter the number of courses registered by the student");
					int coursesCount = Integer.parseInt(br.readLine());
					System.out.println("Enter the "+coursesCount+" courses(one per line) registered by the student");
					List<String> courses = new ArrayList<>();
					
					for(int n=0;n<coursesCount;n++){
						String course = br.readLine();
						courses.add(course);
					}
					
					System.out.println("Enter the status of student (Active/Inactive)");
					String status = br.readLine();
					String statusDate = null;
					if ((status.toUpperCase().equals("ACTIVE"))) {
						System.out.println("Enter the date when the student became active(Format :: 29 May 2018)");
						statusDate = br.readLine();
					}else if ((status.toUpperCase().equals("INACTIVE"))) {
						System.out.println("Enter the date when the student became inactive(Format :: 29 May 2018)");
						statusDate = br.readLine();
					}else{
						System.out.println("Status assigned Invalid!");
						status="Invalid Status";
					}
					System.out.println(
							client.createSRecord(firstNameS, lastNameS, courses, status, statusDate));
					break;
				case 3:
					System.out.println("Total Record Count from all "+Constants.TOTAL_SERVERS_COUNT+" servers is :: "
							+ client.getRecordCounts());
					break;
				case 4:
					System.out.println("Enter the Student ID");
					String recordID = br.readLine();
					System.out.println("Enter one of the fieldName to be updated (firstName,lastName,CoursesRegistered,status,statusDate)");
					String fieldName = br.readLine();
					
					if(fieldName.equals("CoursesRegistered")){
						System.out.println("Enter the number of courses registered by the student");
						coursesCount = Integer.parseInt(br.readLine());
						System.out.println("Enter the "+coursesCount+" courses(one per line) registered by the student");
						courses = new ArrayList<>();
						
						for(int n=0;n<coursesCount;n++){
							String course = br.readLine();
							courses.add(course);
						}
						client.editRecordForCourses(recordID, fieldName, courses);
					}
					else {
						System.out.println("Enter the value of the field to be updated");
						String newValue = br.readLine();
						System.out.println(client.editRecord(recordID, fieldName, newValue));
					}
					break;
				case 5:
					i = 0;
					break;
				default:
					System.out.println("Invalid choice! Please try again");
					break;
				}

			}
			System.out.println("Manager with " + managerID + "is logged Out");
		}
	}
}