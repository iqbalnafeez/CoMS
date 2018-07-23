package com.tsnc.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity

@Table(name="studentform_info")
public class StudentFormInfo extends Base{
	
	@Column
	@Id
	private String applicationNo;
	@Column
	private String rollNo;
	@Column
	private Date date;
	@Column
	private Date dob;
	@Column
	private String gender;
	@Column
	private String name;
	@Column
	private String officialAaddress;
	@Column
	private String handicapped;
	@Column
	private String parentname;
	@Column
	private String occupation;
	@Column
	private String contactname;
	@Column
	private String excuractivity;
	
	@Column
	private String community;
	
	@Column
	private String acddetail;
	
	
	@Column(name="phyhandi")
	private boolean phyhandi;
	@Column
	private String religion;
	@Column
	private String nationality;
	@Column
	private String motherTongue;
	@Column
	private String commAddress;
	@Column
	private String permAaddress;

	
	
	@Column
	private String photoLocation;
	@Column
	private String courseCategory;
	@Column
	private String course;
	@Column
	private String bloodGroup;
	@Column
	private boolean wardofExservice;
	@Column
	private String lastAcademicType;
	
	@Column
	private int batchStrYr;
	@Column
	private double annincome;
	@Column
	private String contactphone;
	@Column
	private String lastschoolname;
	@Column
	private String breakstudy;
	@Column
	private double totalmarkobtained;
	@Column
	private double percentage;
	@Column
	private double totalmaxmark;
	
	
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
	public double getTotalmarkobtained() {
		return totalmarkobtained;
	}
	public void setTotalmarkobtained(double totalmarkobtained) {
		this.totalmarkobtained = totalmarkobtained;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public double getTotalmaxmark() {
		return totalmaxmark;
	}
	public void setTotalmaxmark(double totalmaxmark) {
		this.totalmaxmark = totalmaxmark;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public boolean isPhyhandi() {
		return phyhandi;
	}
	public void setPhyhandi(boolean phyhandi) {
		this.phyhandi = phyhandi;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getMotherTongue() {
		return motherTongue;
	}
	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}
	public String getCommAddress() {
		return commAddress;
	}
	public void setCommAddress(String commAddress) {
		this.commAddress = commAddress;
	}
	public String getPermAaddress() {
		return permAaddress;
	}
	public void setPermAaddress(String permAaddress) {
		this.permAaddress = permAaddress;
	}
	public String getOfficialAaddress() {
		return officialAaddress;
	}
	public void setOfficialAaddress(String officialAaddress) {
		this.officialAaddress = officialAaddress;
	}
	public String getHandicapped() {
		return handicapped;
	}
	public void setHandicapped(String handicapped) {
		this.handicapped = handicapped;
	}
	public String getParentname() {
		return parentname;
	}
	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getContactname() {
		return contactname;
	}
	public void setContactname(String contactname) {
		this.contactname = contactname;
	}
	public String getExcuractivity() {
		return excuractivity;
	}
	public void setExcuractivity(String excuractivity) {
		this.excuractivity = excuractivity;
	}
	public String getPhotoLocation() {
		return photoLocation;
	}
	public void setPhotoLocation(String photoLocation) {
		this.photoLocation = photoLocation;
	}
	public String getCourseCategory() {
		return courseCategory;
	}
	public void setCourseCategory(String courseCategory) {
		this.courseCategory = courseCategory;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public boolean isWardofExservice() {
		return wardofExservice;
	}
	public void setWardofExservice(boolean wardofExservice) {
		this.wardofExservice = wardofExservice;
	}
	public String getLastAcademicType() {
		return lastAcademicType;
	}
	public void setLastAcademicType(String lastAcademicType) {
		this.lastAcademicType = lastAcademicType;
	}
	public String getAcddetail() {
		return acddetail;
	}
	public void setAcddetail(String acddetail) {
		this.acddetail = acddetail;
	}
	public int getBatchStrYr() {
		return batchStrYr;
	}
	public void setBatchStrYr(int batchStrYr) {
		this.batchStrYr = batchStrYr;
	}
	public double getAnnincome() {
		return annincome;
	}
	public void setAnnincome(double annincome) {
		this.annincome = annincome;
	}
	public String getContactphone() {
		return contactphone;
	}
	public void setContactphone(String contactphone) {
		this.contactphone = contactphone;
	}
	


	
}