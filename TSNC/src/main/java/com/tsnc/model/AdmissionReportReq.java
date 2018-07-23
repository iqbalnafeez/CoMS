package com.tsnc.model;

import java.sql.Date;

public class AdmissionReportReq extends Base{
private int batchstartyear;
private Date fromdate;
private Date todate;
private String reportFormat;

public int getBatchstartyear() {
	return batchstartyear;
}
public void setBatchstartyear(int batchstartyear) {
	this.batchstartyear = batchstartyear;
}
public Date getFromdate() {
	return fromdate;
}
public void setFromdate(Date fromdate) {
	this.fromdate = fromdate;
}
public Date getTodate() {
	return todate;
}
public void setTodate(Date todate) {
	this.todate = todate;
}
public String getReportFormat() {
	return reportFormat;
}
public void setReportFormat(String reportFormat) {
	this.reportFormat = reportFormat;
}

}
