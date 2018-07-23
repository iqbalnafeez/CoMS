package com.tsnc.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="fee")
public class Fee extends Base {
	@Column
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	private int id;
	@Column
	private String applicationNo;
	@Column
	private String rollNo;
	@Column
	private String name;
	@Column
	private int sem;
	@Column
	private String course;
	@Column
	private int batchStrYr;
	@Column
	private double actualFee;
	@Column
	private double feeNeedTOPay;
	@Column
	private double feePaid;
	@Column
	private double due;
	@Column
	private String concessionType;
	@Column
	private double concessionPercantage;
	@Column
	private Date dueDate;
	@Column
	private int fine;
	@Column
	private Date date;
	@Column
	private String lastAcademicType;
	@Column
	private String paymenttype;
	@Column
	private int paymentnumber;
	@Column
	private String receiptNo;
	@Column
	private String remarks;
	@Column
	private String paymentdated;
	@Column
	private String paymentdrawnat;
	
	@Transient
	private double feeowe;
	
	public double getFeeowe() {
		return feeowe;
	}
	public void setFeeowe(double feeowe) {
		this.feeowe = feeowe;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSem() {
		return sem;
	}
	public void setSem(int sem) {
		this.sem = sem;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public int getBatchStrYr() {
		return batchStrYr;
	}
	public void setBatchStrYr(int batchStrYr) {
		this.batchStrYr = batchStrYr;
	}
	public double getActualFee() {
		return actualFee;
	}
	public void setActualFee(double actualFee) {
		this.actualFee = actualFee;
	}
	public double getFeeNeedTOPay() {
		return feeNeedTOPay;
	}
	public void setFeeNeedTOPay(double feeNeedTOPay) {
		this.feeNeedTOPay = feeNeedTOPay;
	}
	public double getFeePaid() {
		return feePaid;
	}
	public void setFeePaid(double feePaid) {
		this.feePaid = feePaid;
	}
	public double getDue() {
		return due;
	}
	public void setDue(double due) {
		this.due = due;
	}
	public String getConcessionType() {
		return concessionType;
	}
	public void setConcessionType(String concessionType) {
		this.concessionType = concessionType;
	}
	public double getConcessionPercantage() {
		return concessionPercantage;
	}
	public void setConcessionPercantage(double concessionPercantage) {
		this.concessionPercantage = concessionPercantage;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public int getFine() {
		return fine;
	}
	public void setFine(int fine) {
		this.fine = fine;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getLastAcademicType() {
		return lastAcademicType;
	}
	public void setLastAcademicType(String lastAcademicType) {
		this.lastAcademicType = lastAcademicType;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public int getPaymentnumber() {
		return paymentnumber;
	}
	public void setPaymentnumber(int paymentnumber) {
		this.paymentnumber = paymentnumber;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPaymentdated() {
		return paymentdated;
	}
	public void setPaymentdated(String paymentdated) {
		this.paymentdated = paymentdated;
	}
	public String getPaymentdrawnat() {
		return paymentdrawnat;
	}
	public void setPaymentdrawnat(String paymentdrawnat) {
		this.paymentdrawnat = paymentdrawnat;
	}
	
		
	
}
