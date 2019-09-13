package com.sns.biz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sns.biz.dto.FollowListVO;

@Repository
public class FollowListDAO {
	
	@Autowired
	SqlSessionTemplate mybatis;
	
	public List<FollowListVO> getMyFolloWingList(FollowListVO vo) {
		return mybatis.selectList("FollowListDAO.getMyFolloWingList", vo);
	}
	
	public List<FollowListVO> getMyFolloWerList(FollowListVO vo) {
		return mybatis.selectList("FollowListDAO.getMyFolloWerList", vo);
	}
	
	public int confirmFollow(FollowListVO vo) {
		return (int) mybatis.selectOne("FollowListDAO.confirmFollow", vo);
	}
	
	public void follow(FollowListVO vo) {
		mybatis.insert("FollowListDAO.follow", vo);
	}
	
	public void unFollow(FollowListVO vo) {
		mybatis.delete("FollowListDAO.unFollow", vo);
	}
}
