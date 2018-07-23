package com.tsnc.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="pgform")
public class PgForm extends Base {
	@Column
	@Id
	private String applicationNo;

	@Column
	private String qualifyexam;
	@Column
	private String collegename;
	@Column
	private String university;
	@Column
	private int mark;
	@Column
	private String percentage;
	@Column
	private Date passout;
	@Column
	private String oldstd;
	
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getQualifyexam() {
		return qualifyexam;
	}
	public void setQualifyexam(String qualifyexam) {
		this.qualifyexam = qualifyexam;
	}
	public String getCollegename() {
		return collegename;
	}
	public void setCollegename(String collegename) {
		this.collegename = collegename;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public Date getPassout() {
		return passout;
	}
	public void setPassout(Date passout) {
		this.passout = passout;
	}
	public String getOldstd() {
		return oldstd;
	}
	public void setOldstd(String oldstd) {
		this.oldstd = oldstd;
	}

}
