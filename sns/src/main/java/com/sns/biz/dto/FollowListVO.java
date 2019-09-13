package com.sns.biz.dto;

import java.sql.Timestamp;

public class FollowListVO {
	 private int flseq;
	 private String fwingNick;
	 private String fwerNick;
	 private Timestamp indate;
	public int getFlseq() {
		return flseq;
	}
	public void setFlseq(int flseq) {
		this.flseq = flseq;
	}
	public String getFwingNick() {
		return fwingNick;
	}
	public void setFwingNick(String fwingNick) {
		this.fwingNick = fwingNick;
	}
	public String getFwerNick() {
		return fwerNick;
	}
	public void setFwerNick(String fwerNick) {
		this.fwerNick = fwerNick;
	}
	public Timestamp getIndate() {
		return indate;
	}
	public void setIndate(Timestamp indate) {
		this.indate = indate;
	}
	 
}
