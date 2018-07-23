package com.tsnc.model;

import java.sql.Date;

public class AppSaleCount {

	
	private String course;
	private String salesLocation;
	private Date date;
	private int salecount;
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getSalesLocation() {
		return salesLocation;
	}
	public void setSalesLocation(String salesLocation) {
		this.salesLocation = salesLocation;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getSalecount() {
		return salecount;
	}
	public void setSalecount(int salecount) {
		this.salecount = salecount;
	}
	
	
}
