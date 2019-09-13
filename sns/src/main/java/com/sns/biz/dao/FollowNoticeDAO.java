package com.sns.biz.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sns.biz.dto.FollowNoticeVO;

@Repository
public class FollowNoticeDAO {
	
	@Autowired
	SqlSessionTemplate mybatis;
	
	public int getNoticeSize(FollowNoticeVO vo) {
		return (int) mybatis.selectOne("FollowNoticeDAO.getNoticeSize", vo);
	}	
	
	public void noticeCheck(FollowNoticeVO vo) {
		mybatis.update("FollowNoticeDAO.noticeCheck", vo);
	}	
	
	
	public void setFollowNoticeGood(FollowNoticeVO vo) {
		mybatis.insert("FollowNoticeDAO.setFollowNoticeGood", vo);
	}
	
	public void setFollowNotice(FollowNoticeVO vo) {
		mybatis.insert("FollowNoticeDAO.setFollowNotice", vo);
	}
	
	public void setFollowNoticeBoard(FollowNoticeVO vo) {
		mybatis.insert("FollowNoticeDAO.setFollowNoticeBoard", vo);
	}
	
	public void deleteFollowNoticeGood(FollowNoticeVO vo) {
		mybatis.delete("FollowNoticeDAO.deleteFollowNoticeGood", vo);
	}
	
	public void deleteFollowNotice(FollowNoticeVO vo) {
		mybatis.delete("FollowNoticeDAO.deleteFollowNotice", vo);
	}
	
	public List<FollowNoticeVO> getFollowNotice(FollowNoticeVO vo) {
		return mybatis.selectList("FollowNoticeDAO.getFollowNotice", vo);
	}	
	
	public List<FollowNoticeVO> getNoticeGood(FollowNoticeVO vo) {
		return mybatis.selectList("FollowNoticeDAO.getNoticeGood", vo);
	}	
	
	public List<FollowNoticeVO> getNoticeFollow(FollowNoticeVO vo) {
		return mybatis.selectList("FollowNoticeDAO.getNoticeFollow", vo);
	}	
	
	public List<FollowNoticeVO> getNoticeNews(FollowNoticeVO vo) {
		return mybatis.selectList("FollowNoticeDAO.getNoticeNews", vo);
	}	
}
