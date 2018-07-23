package com.tsnc.model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="fee_particulars")
public class FeeParticulars extends Base{
	@Column
	@Id
	private int id;
	@Column
	private int fid;
	@Column
	private int hid;
	@Column
	private int sid;
	@Column
	private String particulars;
	@Column
	private boolean overrided;
	@Column
	private double amount;
	
	@Transient
	private boolean isnewone;
	
	public boolean isIsnewone() {
		return isnewone;
	}
	public void setIsnewone(boolean isnewone) {
		this.isnewone = isnewone;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public boolean isOverrided() {
		return overrided;
	}

	public void setOverrided(boolean overrided) {
		this.overrided = overrided;
	}

	@Column
	private Date date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

public int getFid() {
	return fid;
}
public void setFid(int fid) {
	this.fid = fid;
}

	public int getHid() {
		return hid;
	}

	public void setHid(int hid) {
		this.hid = hid;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
