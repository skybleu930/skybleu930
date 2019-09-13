package com.sns.biz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sns.biz.dto.MemberVO;


@Repository
public class MemberDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public void insertMember(MemberVO vo) {
		mybatis.insert("MemberDAO.insertMember", vo);
	}
	
	public MemberVO getMember(MemberVO vo) {
		return (MemberVO) mybatis.selectOne("MemberDAO.getMember", vo);
	}
	
	public int checkEmail(MemberVO vo) {
		return (int) mybatis.selectOne("MemberDAO.checkEmail", vo);	
	}
	
	public int checkNickname(MemberVO vo) {
		return (int) mybatis.selectOne("MemberDAO.checkNickname", vo);	
	}
	
	public MemberVO getNicknameMember(MemberVO vo) {
		return (MemberVO) mybatis.selectOne("MemberDAO.getNicknameMember", vo);
	}

	public void memberUpdate(MemberVO vo) {
		mybatis.update("MemberDAO.memberUpdate", vo);
	}
	
}
