package com.tsnc.model;

import java.math.BigInteger;
import java.util.List;

public class AppSaleReport {
private int s_no;
	private String courses;
	private BigInteger  col_sale_1;
	private BigInteger  col_sale_2;
	private BigInteger  col_sale_3;
	private BigInteger  col_sale_4;
	private BigInteger  col_sale_5;
	private BigInteger  col_sale_6;
	private BigInteger  col_sale_7;
	private BigInteger col_total;

	private BigInteger  dhun_sale_1;
	private BigInteger  dhun_sale_2;
	private BigInteger  dhun_sale_3;
	private BigInteger  dhun_sale_4;
	private BigInteger  dhun_sale_5;
	private BigInteger  dhun_sale_6;
	private BigInteger  dhun_sale_7;
	private BigInteger dhun_total;
	private BigInteger  icl_sale_1;
	private BigInteger  icl_sale_2;
	private BigInteger  icl_sale_3;
	private BigInteger  icl_sale_4;
	private BigInteger  icl_sale_5;
	private BigInteger  icl_sale_6;
	private BigInteger  icl_sale_7;
	private BigInteger icl_total;
	
	private BigInteger all_total;

	public int getS_no() {
		return s_no;
	}

	public void setS_no(int s_no) {
		this.s_no = s_no;
	}

	public BigInteger getCol_sale_7() {
		return col_sale_7;
	}

	public void setCol_sale_7(BigInteger col_sale_7) {
		this.col_sale_7 = col_sale_7;
	}

	public BigInteger getDhun_sale_7() {
		return dhun_sale_7;
	}

	public void setDhun_sale_7(BigInteger dhun_sale_7) {
		this.dhun_sale_7 = dhun_sale_7;
	}

	public BigInteger getIcl_sale_7() {
		return icl_sale_7;
	}

	public void setIcl_sale_7(BigInteger icl_sale_7) {
		this.icl_sale_7 = icl_sale_7;
	}

	public String getCourses() {
		return courses;
	}

	public void setCourses(String courses) {
		this.courses = courses;
	}

	public BigInteger getCol_sale_1() {
		return col_sale_1;
	}

	public void setCol_sale_1(BigInteger col_sale_1) {
		this.col_sale_1 = col_sale_1;
	}

	public BigInteger getCol_sale_2() {
		return col_sale_2;
	}

	public void setCol_sale_2(BigInteger col_sale_2) {
		this.col_sale_2 = col_sale_2;
	}

	public BigInteger getCol_sale_3() {
		return col_sale_3;
	}

	public void setCol_sale_3(BigInteger col_sale_3) {
		this.col_sale_3 = col_sale_3;
	}

	public BigInteger getCol_sale_4() {
		return col_sale_4;
	}

	public void setCol_sale_4(BigInteger col_sale_4) {
		this.col_sale_4 = col_sale_4;
	}

	public BigInteger getCol_sale_5() {
		return col_sale_5;
	}

	public void setCol_sale_5(BigInteger col_sale_5) {
		this.col_sale_5 = col_sale_5;
	}

	public BigInteger getCol_sale_6() {
		return col_sale_6;
	}

	public void setCol_sale_6(BigInteger col_sale_6) {
		this.col_sale_6 = col_sale_6;
	}

	public BigInteger getCol_total() {
		return col_total;
	}

	public void setCol_total(BigInteger col_total) {
		this.col_total = col_total;
	}

	

	public BigInteger getDhun_sale_1() {
		return dhun_sale_1;
	}

	public void setDhun_sale_1(BigInteger dhun_sale_1) {
		this.dhun_sale_1 = dhun_sale_1;
	}

	public BigInteger getDhun_sale_2() {
		return dhun_sale_2;
	}

	public void setDhun_sale_2(BigInteger dhun_sale_2) {
		this.dhun_sale_2 = dhun_sale_2;
	}

	public BigInteger getDhun_sale_3() {
		return dhun_sale_3;
	}

	public void setDhun_sale_3(BigInteger dhun_sale_3) {
		this.dhun_sale_3 = dhun_sale_3;
	}

	public BigInteger getDhun_sale_4() {
		return dhun_sale_4;
	}

	public void setDhun_sale_4(BigInteger dhun_sale_4) {
		this.dhun_sale_4 = dhun_sale_4;
	}

	public BigInteger getDhun_sale_5() {
		return dhun_sale_5;
	}

	public void setDhun_sale_5(BigInteger dhun_sale_5) {
		this.dhun_sale_5 = dhun_sale_5;
	}

	public BigInteger getDhun_sale_6() {
		return dhun_sale_6;
	}

	public void setDhun_sale_6(BigInteger dhun_sale_6) {
		this.dhun_sale_6 = dhun_sale_6;
	}

	public BigInteger getDhun_total() {
		return dhun_total;
	}

	public void setDhun_total(BigInteger dhun_total) {
		this.dhun_total = dhun_total;
	}

	public BigInteger getIcl_sale_1() {
		return icl_sale_1;
	}

	public void setIcl_sale_1(BigInteger icl_sale_1) {
		this.icl_sale_1 = icl_sale_1;
	}

	public BigInteger getIcl_sale_2() {
		return icl_sale_2;
	}

	public void setIcl_sale_2(BigInteger icl_sale_2) {
		this.icl_sale_2 = icl_sale_2;
	}

	public BigInteger getIcl_sale_3() {
		return icl_sale_3;
	}

	public void setIcl_sale_3(BigInteger icl_sale_3) {
		this.icl_sale_3 = icl_sale_3;
	}

	public BigInteger getIcl_sale_4() {
		return icl_sale_4;
	}

	public void setIcl_sale_4(BigInteger icl_sale_4) {
		this.icl_sale_4 = icl_sale_4;
	}

	public BigInteger getIcl_sale_5() {
		return icl_sale_5;
	}

	public void setIcl_sale_5(BigInteger icl_sale_5) {
		this.icl_sale_5 = icl_sale_5;
	}

	public BigInteger getIcl_sale_6() {
		return icl_sale_6;
	}

	public void setIcl_sale_6(BigInteger icl_sale_6) {
		this.icl_sale_6 = icl_sale_6;
	}

	public BigInteger getIcl_total() {
		return icl_total;
	}

	public void setIcl_total(BigInteger icl_total) {
		this.icl_total = icl_total;
	}

	public BigInteger getAll_total() {
		return all_total;
	}

	public void setAll_total(BigInteger all_total) {
		this.all_total = all_total;
	}

	
	
}
