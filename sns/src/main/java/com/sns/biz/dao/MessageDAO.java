package com.sns.biz.dao;



import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sns.biz.dto.MessageVO;

@Repository
public class MessageDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public void setMessage(MessageVO vo) {
		mybatis.insert("MessageDAO.setMessage", vo);
	}	
	
	public MessageVO getMessage(MessageVO vo) {
		return (MessageVO) mybatis.selectOne("MessageDAO.getMessage", vo);
	}	
	
	public int getMessageSize(MessageVO vo) {
		return (int) mybatis.selectOne("MessageDAO.getMessageSize", vo);
	}	
	
	public MessageVO getConnectMseq(MessageVO vo) {
		return (MessageVO) mybatis.selectOne("MessageDAO.getConnectMseq", vo);
	}	
	
	public List<MessageVO> getMessageList(MessageVO vo) {
		return mybatis.selectList("MessageDAO.getMessageList", vo);
	}	
	
	public List<MessageVO> getSendMessageList(MessageVO vo) {
		return mybatis.selectList("MessageDAO.getSendMessageList", vo);
	}	
	
	public void ReMessageComplete(MessageVO vo) {
		mybatis.update("MessageDAO.ReMessageComplete", vo);
	}	
	
	public void messageCheck(MessageVO vo) {
		mybatis.update("MessageDAO.messageCheck", vo);
	}	

}
