package com.Models;

import java.util.ArrayList;
import java.util.Date;

public class Student {
	String firstName;
	String lastName;
	ArrayList<String> CoursesRegistered = new ArrayList<String>();
	boolean status;
	Date statusDate;

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

	public ArrayList<String> getCoursesRegistered() {
		return CoursesRegistered;
	}

	public void setCoursesRegistered(ArrayList<String> coursesRegistered) {
		CoursesRegistered = coursesRegistered;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	@Override
	public String toString() {
		return "Student [firstName=" + firstName + ", lastName=" + lastName + ", CoursesRegistered=" + CoursesRegistered
				+ ", status=" + status + ", statusDate=" + statusDate + "]";
	}
}
