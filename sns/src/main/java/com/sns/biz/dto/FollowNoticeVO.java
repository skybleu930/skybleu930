package com.sns.biz.dto;

import java.sql.Timestamp;


public class FollowNoticeVO {
	private int fnseq;
	private int bseq;
	private String fwerNick;
	private String fwingNick;
	private String ffwingNick;
	private String notiyn;
	private String ntype;
	private Timestamp indate;
	
	public String getFfwingNick() {
		return ffwingNick;
	}
	public void setFfwingNick(String ffwingNick) {
		this.ffwingNick = ffwingNick;
	}
	public int getFnseq() {
		return fnseq;
	}
	public void setFnseq(int fnseq) {
		this.fnseq = fnseq;
	}
	public int getBseq() {
		return bseq;
	}
	public void setBseq(int bseq) {
		this.bseq = bseq;
	}
	public String getFwerNick() {
		return fwerNick;
	}
	public void setFwerNick(String fwerNick) {
		this.fwerNick = fwerNick;
	}
	public String getFwingNick() {
		return fwingNick;
	}
	public void setFwingNick(String fwingNick) {
		this.fwingNick = fwingNick;
	}
	public String getNotiyn() {
		return notiyn;
	}
	public void setNotiyn(String notiyn) {
		this.notiyn = notiyn;
	}
	public String getNtype() {
		return ntype;
	}
	public void setNtype(String ntype) {
		this.ntype = ntype;
	}
	public Timestamp getIndate() {
		return indate;
	}
	public void setIndate(Timestamp indate) {
		this.indate = indate;
	}
	
}
