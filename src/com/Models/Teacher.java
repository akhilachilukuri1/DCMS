package com.Models;

public class Teacher {
String firstName;
String lastName;
String Address;
String phone;
String specilization;
String location;
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
@Override
public String toString() {
	return "Teacher [firstName=" + firstName + ", lastName=" + lastName + ", Address=" + Address + ", phone=" + phone
			+ ", specilization=" + specilization + ", location=" + location + "]";
}
}
