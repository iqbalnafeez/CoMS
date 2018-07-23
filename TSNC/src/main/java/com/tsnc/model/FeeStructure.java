package com.tsnc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="fee_structure")
public class FeeStructure extends Base {
	@Column
	@Id
	private int id;
	@Column
	private String particulars;
	@Column
	private double amount;
	@Column
	private int sem;
	@Column
	private String course;
	@Column
	private String lastAcademicType;
	@Column
	private int batchStrYr;
	@Transient
	private boolean del;
	
	
	
	public boolean isDel() {
		return del;
	}
	public void setDel(boolean del) {
		this.del = del;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getSem() {
		return sem;
	}
	public void setSem(int sem) {
		this.sem = sem;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getLastAcademicType() {
		return lastAcademicType;
	}
	public void setLastAacademicType(String lastAcademicType) {
		this.lastAcademicType = lastAcademicType;
	}
	public int getBatchStrYr() {
		return batchStrYr;
	}
	public void setBatchStrYr(int batchStrYr) {
		this.batchStrYr = batchStrYr;
	}
	
	
	
	
}
