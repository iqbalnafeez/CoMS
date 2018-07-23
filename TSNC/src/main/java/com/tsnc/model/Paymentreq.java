package com.tsnc.model;

import java.util.List;

public class Paymentreq  extends Base
{
	private Fee fee;
	public Fee getFee() {
		return fee;
	}
	public void setFee(Fee fee) {
		this.fee = fee;
	}
	public List<FeeParticulars> getLsfeeParticulars() {
		return lsfeeParticulars;
	}
	public void setLsfeeParticulars(List<FeeParticulars> lsfeeParticulars) {
		this.lsfeeParticulars = lsfeeParticulars;
	}
	private List<FeeParticulars> lsfeeParticulars;
}
