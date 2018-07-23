package com.tsnc.model;

import java.sql.Date;

public class FeesReportReq extends Base {
	private String course;
	private int sem;
	private String format;
	private Date frmdate;
	private Date todate;
	private String status;
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public int getSem() {
		return sem;
	}
	public void setSem(int sem) {
		this.sem = sem;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
