package com.tsnc.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;


@Entity
@Table(name="application_sales")
public class ApplicationSale extends Base{

	@Column
	private String studentName;
	@Column
	private String phone;
	@Column
	private String address;
	@Column
	private String salesLocation;
	@Column
	private String gender;

	@Column
	@Id
	private String applicationNo;
	@Column
	private String courseCategory;
	@Column
	private String course;
	@Column
	private double amountPaid;
	@Column
	private String reciptNo;
	public String getCourseCategory() {
		return courseCategory;
	}
	public void setCourseCategory(String courseCategory) {
		this.courseCategory = courseCategory;
	}
	@Column
	private Date date;
	
	public ApplicationSale(String studentName, String phone, String address,
			String salesLocation, String gender, String applicationNo,
			String course, int amountPaid, String reciptNo, Date date) {
		
		this.studentName = studentName;
		this.phone = phone;
		this.address = address;
		this.salesLocation = salesLocation;
		this.gender = gender;
		this.applicationNo = applicationNo;
		this.course = course;
		this.amountPaid = amountPaid;
		this.reciptNo = reciptNo;
		this.date = date;
	}
	public double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	public ApplicationSale() {
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSalesLocation() {
		return salesLocation;
	}
	public void setSalesLocation(String salesLocation) {
		this.salesLocation = salesLocation;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getReciptNo() {
		return reciptNo;
	}
	public void setReciptNo(String reciptNo) {
		this.reciptNo = reciptNo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}

