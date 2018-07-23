package com.tsnc.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="fee_historys")
public class FeeHistorys extends Base {
	@Column
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	private int id;
	@Column
	private int fid;
	@Column
	private Date date;
	@Column
	private int sem;
	
	@Column
	private double actualFee;
	@Column
	private double feeNeedTOPay;
	@Column
	private double feePaid;
	@Column
	private double due;
	@Column
	private String paymenttype;
	@Column
	private int paymentnumber;
	@Column
	private int fine;
	@Column
	private String receiptNo;
	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	@Column
	private String remarks;
	@Column
	private String concessionType;
	@Column
	private double concessionPercantage;
	@Column
	private String paymentdated;
	@Column
	private String paymentdrawnat;
	
	
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



	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

public int getFid() {
	return fid;
}
public void setFid(int fid) {
	this.fid = fid;
}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getSem() {
		return sem;
	}

	public void setSem(int sem) {
		this.sem = sem;
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

	public int getFine() {
		return fine;
	}

	public void setFine(int fine) {
		this.fine = fine;
	}
	
	
}
