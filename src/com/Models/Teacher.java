package com.Models;

import java.io.Serializable;

public class Teacher extends Record implements Serializable {
	String firstName;
	String lastName;
	String Address;
	String phone;
	String specilization;
	String location;
	String TeacherID;

	public Teacher(){
		
	}
	public Teacher(String teacherID, String firstName, String lastname) {
		super(teacherID, firstName, lastname);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSpecilization() {
		return specilization;
	}

	public void setSpecilization(String specilization) {
		this.specilization = specilization;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTeacherID() {
		return TeacherID;
	}

	public void setTeacherID(String teacherID) {
		TeacherID = teacherID;
	}

	@Override
	public String toString() {
		return "Teacher [firstName=" + firstName + ", lastName=" + lastName + ", Address=" + Address + ", phone="
				+ phone + ", specilization=" + specilization + ", location=" + location + ", TeacherID=" + TeacherID
				+ "]";
	}

}
