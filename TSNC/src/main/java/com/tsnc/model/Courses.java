package com.tsnc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="courses")
public class Courses extends Base {
	@Column
	@Id
	private int id;
	@Column
	private String course;
	@Column
	private String courseCategory;
	@Column
	private int sanctionedStrength;
	@Column
	private int adhoc;
	@Column
	private int batchStrYr;
	@Column
	private String rollcode;
	
	public String getRollcode() {
		return rollcode;
	}
	public void setRollcode(String rollcode) {
		this.rollcode = rollcode;
	}
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
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCourseCategory() {
		return courseCategory;
	}
	public void setCourseCategory(String courseCategory) {
		this.courseCategory = courseCategory;
	}
	public int getSanctionedStrength() {
		return sanctionedStrength;
	}
	public void setSanctionedStrength(int sanctionedStrength) {
		this.sanctionedStrength = sanctionedStrength;
	}
	public int getAdhoc() {
		return adhoc;
	}
	public void setAdhoc(int adhoc) {
		this.adhoc = adhoc;
	}
	public int getBatchStrYr() {
		return batchStrYr;
	}
	public void setBatchStrYr(int batchStrYr) {
		this.batchStrYr = batchStrYr;
	}
	
	
}
