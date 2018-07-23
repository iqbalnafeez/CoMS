package com.tsnc.model;

import java.sql.Date;

public class SaleReportReq extends Base {
	
	private String course;
	private String salesLocation;
	private String format;
	private Date frmdate;
	private Date todate;
	
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
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Date getFrmdate() {
		return frmdate;
	}
	public void setFrmdate(Date frmdate) {
		this.frmdate = frmdate;
	}
	public Date getTodate() {
		return todate;
	}
	public void setTodate(Date todate) {
		this.todate = todate;
	}
	
	
}
