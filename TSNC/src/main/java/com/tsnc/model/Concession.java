package com.tsnc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="concession")
public class Concession extends Base{
	@Id
	@Column
private int id;
	@Column
private String type;
	@Column
private double percent;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}


}
