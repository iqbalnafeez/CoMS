package com.tsnc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="subjectmarks")
public class SubjectMarks{
@Column
@Id
@GenericGenerator(name="kaugen" , strategy="increment")
@GeneratedValue(generator="kaugen")
private int id;
@Column
private String applicationNo;
@Column
private String subject;
@Column
private int markobtain;
@Column
private int maxmark;
@Column
private String regno;

@Column
private String yearofpass;
@Column
private int attempt;
public String getApplicationNo() {
	return applicationNo;
}
public void setApplicationNo(String applicationNo) {
	this.applicationNo = applicationNo;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public int getMarkobtain() {
	return markobtain;
}
public void setMarkobtain(int markobtain) {
	this.markobtain = markobtain;
}
public int getMaxmark() {
	return maxmark;
}
public void setMaxmark(int maxmark) {
	this.maxmark = maxmark;
}

public String getRegno() {
	return regno;
}
public void setRegno(String regno) {
	this.regno = regno;
}
public String getYearofpass() {
	return yearofpass;
}
public void setYearofpass(String yearofpass) {
	this.yearofpass = yearofpass;
}
public int getAttempt() {
	return attempt;
}
public void setAttempt(int attempt) {
	this.attempt = attempt;}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
