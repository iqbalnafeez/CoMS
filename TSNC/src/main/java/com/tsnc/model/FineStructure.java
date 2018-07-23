package com.tsnc.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fine_structure")
public class FineStructure extends Base {
	@Column
	@Id
	private int id;
	@Column
	private int batchStrYr;
	@Column
	private int sem;
	@Column
	private Date dueDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBatchStrYr() {
		return batchStrYr;
	}
	public void setBatchStrYr(int batchStrYr) {
		this.batchStrYr = batchStrYr;
	}
	public int getSem() {
		return sem;
	}
	public void setSem(int sem) {
		this.sem = sem;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	
}
