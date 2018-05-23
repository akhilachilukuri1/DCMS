package com.Models;

public class Record {
	private String firstName;
	private String lastname;
	private String recordID;

	public String getRecordID() {
		return recordID;
	}

	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}

	public Record() {

	}

	public Record(String recordID, String firstName, String lastname) {
		this.setFirstName(firstName);
		this.setLastname(lastname);
		this.setRecordID(recordID);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}