package com.sns.biz.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sns.biz.dto.ReplyVO;

@Repository
public class ReplyDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public void setParentReply(ReplyVO vo) {
		mybatis.insert("ReplyDAO.setParentReply", vo);
	}
	
	public void setChildReply(ReplyVO vo) {
		mybatis.insert("ReplyDAO.setChildReply", vo);
	}
	
	public ReplyVO getMaxGrpAndSeq(ReplyVO vo) {
		return (ReplyVO) mybatis.selectOne("ReplyDAO.getMaxGrpAndSeq", vo);
	}
	
	public List<ReplyVO> getReplyList(ReplyVO vo) {
		return mybatis.selectList("ReplyDAO.getReplyList", vo);
	}
}
