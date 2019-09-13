package com.sns.biz.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sns.biz.dto.BoardVO;

@Repository
public class BoardDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<BoardVO> getBoard(String search) {
		return mybatis.selectList("BoardDAO.getBoard", search);
	}
	
	public List<BoardVO> getMyBoard(BoardVO vo) {
		return mybatis.selectList("BoardDAO.getMyBoard", vo);
	}
	
	public BoardVO getBoardDetail(BoardVO vo) {
		return (BoardVO) mybatis.selectOne("BoardDAO.getBoardDetail", vo);
	}
	

	public int getMaxBoardBseq(BoardVO vo) {
		return (int) mybatis.selectOne("BoardDAO.getMaxBoardBseq", vo);
	}
	
	public void setMyBoard(BoardVO vo) {
		mybatis.insert("BoardDAO.setMyBoard", vo);
	}
	
	
	public void boardGoodPuls(BoardVO vo) {
		mybatis.update("BoardDAO.boardGoodPuls", vo);
	}
	
	public void boardGoodMinus(BoardVO vo) {
		mybatis.update("BoardDAO.boardGoodMinus", vo);
	}

}
