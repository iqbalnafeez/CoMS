package com.tsnc.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="referralinfo")
public class ReferralInfo extends Base{
	@Column
	@Id
	private String applicationNo;
	@Column
	private String howknow;
	@Column
	private String whyselect;
	@Column
	private String whourged;
	@Column
	private String frndstudy;
	@Column
	private String ref1;
	@Column
	private String ref2;

	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getHowknow() {
		return howknow;
	}
	public void setHowknow(String howknow) {
		this.howknow = howknow;
	}
	public String getWhyselect() {
		return whyselect;
	}
	public void setWhyselect(String whyselect) {
		this.whyselect = whyselect;
	}
	public String getWhourged() {
		return whourged;
	}
	public void setWhourged(String whourged) {
		this.whourged = whourged;
	}
	public String getFrndstudy() {
		return frndstudy;
	}
	public void setFrndstudy(String frndstudy) {
		this.frndstudy = frndstudy;
	}
	public String getRef1() {
		return ref1;
	}
	public void setRef1(String ref1) {
		this.ref1 = ref1;
	}
	public String getRef2() {
		return ref2;
	}
	public void setRef2(String ref2) {
		this.ref2 = ref2;
	}

}
