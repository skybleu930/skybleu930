package com.sns.biz.dto;

import java.sql.Timestamp;

public class MessageVO {
	private int mseq;
	private int remseq;
	private String dearNick;
	private String fromNick;
	private String message;
	private String myn;
	private String mrep;
	private Timestamp indate;
	
	
	public int getRemseq() {
		return remseq;
	}
	public void setRemseq(int remseq) {
		this.remseq = remseq;
	}
	public int getMseq() {
		return mseq;
	}
	public void setMseq(int mseq) {
		this.mseq = mseq;
	}
	public String getDearNick() {
		return dearNick;
	}
	public void setDearNick(String dearNick) {
		this.dearNick = dearNick;
	}
	public String getFromNick() {
		return fromNick;
	}
	public void setFromNick(String fromNick) {
		this.fromNick = fromNick;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMyn() {
		return myn;
	}
	public void setMyn(String myn) {
		this.myn = myn;
	}
	public String getMrep() {
		return mrep;
	}
	public void setMrep(String mrep) {
		this.mrep = mrep;
	}
	public Timestamp getIndate() {
		return indate;
	}
	public void setIndate(Timestamp indate) {
		this.indate = indate;
	}
	
}
