package com.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student extends Record implements Serializable {
	String firstName;
	String lastName;
	List<String> CoursesRegistered;
	String status;
	String statusDate;
	String studentID;

	public Student() {
		CoursesRegistered = new ArrayList<>();
	}

	public Student(String studentID, String firstName, String lastname) {
		super(studentID, firstName, lastname);
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

	public List<String> getCoursesRegistered() {
		return CoursesRegistered;
	}

	public void setCoursesRegistered(List<String> courses) {
		CoursesRegistered = courses;
	}

	public String isStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	@Override
	public String toString() {
		return "Student [firstName=" + firstName + ", lastName=" + lastName + ", CoursesRegistered=" + CoursesRegistered
				+ ", status=" + status + ", statusDate=" + statusDate + ", studentID=" + studentID + "]";
	}

}
