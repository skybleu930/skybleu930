package com.sns.biz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sns.biz.dto.BoardGoodVO;

@Repository
public class BoardGoodDAO {
	
	@Autowired
	SqlSessionTemplate mybatis;
	
	public void setBoardGood(BoardGoodVO vo) {
		mybatis.insert("BoardGoodDAO.setBoardGood", vo);
	}
	
	public int getBoardGood(BoardGoodVO vo) {
		return (int) mybatis.selectOne("BoardGoodDAO.getBoardGood", vo);
	}
	
	public void deleteBoardGood(BoardGoodVO vo) {
		mybatis.delete("BoardGoodDAO.deleteBoardGood", vo);
	}
}
