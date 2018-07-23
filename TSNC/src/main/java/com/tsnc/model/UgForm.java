package com.tsnc.model;


import java.util.List;

public class UgForm extends Base{
	
	private String applicationNo;
	private String lastschoolname;
	
	private String breakstudy;
	private double totalmaxmark;
	private double totalmarkobtained;
	
	private double percentage;

	private List<SubjectMarks> lssubjectMarks;

	
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getLastschoolname() {
		return lastschoolname;
	}
	public void setLastschoolname(String lastschoolname) {
		this.lastschoolname = lastschoolname;
	}

	
	public String getBreakstudy() {
		return breakstudy;
	}
	public void setBreakstudy(String breakstudy) {
		this.breakstudy = breakstudy;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public List<SubjectMarks> getLssubjectMarks() {
		return lssubjectMarks;
	}
	public void setLssubjectMarks(List<SubjectMarks> lssubjectMarks) {
		this.lssubjectMarks = lssubjectMarks;
	}
	public double getTotalmaxmark() {
		return totalmaxmark;
	}
	public void setTotalmaxmark(double totalmaxmark) {
		this.totalmaxmark = totalmaxmark;
	}
	public double getTotalmarkobtained() {
		return totalmarkobtained;
	}
	public void setTotalmarkobtained(double totalmarkobtained) {
		this.totalmarkobtained = totalmarkobtained;
	}
	
	
	
}
