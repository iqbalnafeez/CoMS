package com.tsnc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lisstd")
public class StudentAdmittedReport {
@Id
@Column
private String course;
@Column
private int sactionedstrength;
@Column
private int adhocincrease;
@Column
private int total;

@Column
private int moc;
@Column
private int mbc;
@Column
private int mbcm;
@Column
private int mmbc;
@Column
private int msc;
@Column
private int msca;
@Column
private int mst;
@Column
private int mtotal;
@Column
private int foc;
@Column
private int fbc;
@Column
private int fbcm;
@Column
private int fmbc;
@Column
private int fsc;
@Column
private int fsca;
@Column
private int fst;
@Column
private int ftotal;
@Column
private int noOfAddmittedStudent;


public String getCourse() {
	return course;
}
public void setCourse(String course) {
	this.course = course;
}
public int getSactionedstrength() {
	return sactionedstrength;
}
public void setSactionedstrength(int sactionedstrength) {
	this.sactionedstrength = sactionedstrength;
}
public int getNoOfAddmittedStudent() {
	return noOfAddmittedStudent;
}
public void setNoOfAddmittedStudent(int noOfAddmittedStudent) {
	this.noOfAddmittedStudent = noOfAddmittedStudent;
}
public int getAdhocincrease() {
	return adhocincrease;
}
public void setAdhocincrease(int adhocincrease) {
	this.adhocincrease = adhocincrease;
}
public int getTotal() {
	return total;
}
public void setTotal(int total) {
	this.total = total;
}

public int getMoc() {
	return moc;
}
public void setMoc(int moc) {
	this.moc = moc;
}
public int getMbc() {
	return mbc;
}
public void setMbc(int mbc) {
	this.mbc = mbc;
}
public int getMbcm() {
	return mbcm;
}
public void setMbcm(int mbcm) {
	this.mbcm = mbcm;
}
public int getMmbc() {
	return mmbc;
}
public void setMmbc(int mmbc) {
	this.mmbc = mmbc;
}
public int getMsc() {
	return msc;
}
public void setMsc(int msc) {
	this.msc = msc;
}
public int getMsca() {
	return msca;
}
public void setMsca(int msca) {
	this.msca = msca;
}
public int getMst() {
	return mst;
}
public void setMst(int mst) {
	this.mst = mst;
}
public int getMtotal() {
	return mtotal;
}
public void setMtotal(int mtotal) {
	this.mtotal = mtotal;
}
public int getFoc() {
	return foc;
}
public void setFoc(int foc) {
	this.foc = foc;
}
public int getFbc() {
	return fbc;
}
public void setFbc(int fbc) {
	this.fbc = fbc;
}
public int getFbcm() {
	return fbcm;
}
public void setFbcm(int fbcm) {
	this.fbcm = fbcm;
}
public int getFmbc() {
	return fmbc;
}
public void setFmbc(int fmbc) {
	this.fmbc = fmbc;
}
public int getFsc() {
	return fsc;
}
public void setFsc(int fsc) {
	this.fsc = fsc;
}
public int getFsca() {
	return fsca;
}
public void setFsca(int fsca) {
	this.fsca = fsca;
}
public int getFst() {
	return fst;
}
public void setFst(int fst) {
	this.fst = fst;
}
public int getFtotal() {
	return ftotal;
}
public void setFtotal(int ftotal) {
	this.ftotal = ftotal;
}
/*public int getTrangender() {
	return trangender;
}
public void setTrangender(int trangender) {
	this.trangender = trangender;
}*/
/*public int getGrandtotal() {
	return grandtotal;
}
public void setGrandtotal(int grandtotal) {
	this.grandtotal = grandtotal;
}*/



}
