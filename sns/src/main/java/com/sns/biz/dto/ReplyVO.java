package com.sns.biz.dto;

import java.sql.Timestamp;

public class ReplyVO {
	private int rseq;
	private int bseq;
	String comment;
	String parentNick;
	String childNick;
	int parent;
	int grp;
	int depth;
	int seq;
	String viewtype;
	Timestamp indate;
	
	public int getRseq() {
		return rseq;
	}
	public void setRseq(int rseq) {
		this.rseq = rseq;
	}
	public int getBseq() {
		return bseq;
	}
	public void setBseq(int bseq) {
		this.bseq = bseq;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String conment) {
		this.comment = conment;
	}
	public String getParentNick() {
		return parentNick;
	}
	public void setParentNick(String parentNick) {
		this.parentNick = parentNick;
	}
	public String getChildNick() {
		return childNick;
	}
	public void setChildNick(String childNick) {
		this.childNick = childNick;
	}
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
	public int getGrp() {
		return grp;
	}
	public void setGrp(int grp) {
		this.grp = grp;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getViewtype() {
		return viewtype;
	}
	public void setViewtype(String viewtype) {
		this.viewtype = viewtype;
	}
	public Timestamp getIndate() {
		return indate;
	}
	public void setIndate(Timestamp indate) {
		this.indate = indate;
	}
}
