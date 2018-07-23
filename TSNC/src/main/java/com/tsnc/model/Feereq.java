package com.tsnc.model;

public class Feereq extends Base{

	private String type;
	private String num;
	private int sem;
	/*private String lastAacademicType;
	*/
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	/*public String getLastAacademicType() {
		return lastAacademicType;
	}
	public void setLastAacademicType(String lastAacademicType) {
		this.lastAacademicType = lastAacademicType;
	}*/
	public int getSem() {
		return sem;
	}
	public void setSem(int sem) {
		this.sem = sem;
	}
	
}
