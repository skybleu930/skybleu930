package com.sns.view.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sns.biz.dao.BoardDAO;
import com.sns.biz.dao.FollowListDAO;
import com.sns.biz.dao.FollowNoticeDAO;
import com.sns.biz.dao.MemberDAO;
import com.sns.biz.dto.BoardVO;
import com.sns.biz.dto.FollowListVO;
import com.sns.biz.dto.FollowNoticeVO;
import com.sns.biz.dto.MemberVO;

@Controller
public class FollowController {
	
	@Autowired
	FollowListDAO followListDAO;
	@Autowired
	FollowNoticeDAO followNoticeDAO;
	@Autowired
	MemberDAO memberDAO;
	@Autowired
	BoardDAO boardDAO;
	
	@RequestMapping("/follow.do")
	public String follow(HttpSession session, FollowNoticeVO followNotice, 
			FollowListVO followList, @RequestParam String nickname) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			//팔로우 리스트 저장
			followList.setFwerNick(loginUser.getNickname());
			followList.setFwingNick(nickname);
			followListDAO.follow(followList);
			
			//팔로잉 대상에게 알림
			followNotice.setFwerNick(loginUser.getNickname());
			followNotice.setFwingNick(nickname);
			followNoticeDAO.setFollowNotice(followNotice);
			
			//팔로워에게 내가 팔로우 한사람을 알려준다.
			followList.setFwingNick(loginUser.getNickname());
			List<FollowListVO> myFolloWerList =
					followListDAO.getMyFolloWerList(followList);
			if(myFolloWerList.size() != 0) {
				for (FollowListVO fwerNick : myFolloWerList) {
					followNotice.setFwerNick(fwerNick.getFwerNick());
					followNotice.setFwingNick(loginUser.getNickname());
					followNotice.setFfwingNick(nickname);
					followNoticeDAO.setFollowNotice(followNotice);
				}
			}
		}
		return "memberBoard.do";
	}
	
	@RequestMapping("/unFollow.do")
	public String unFollow(HttpSession session, FollowListVO followList, @RequestParam String nickname) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			followList.setFwerNick(loginUser.getNickname());
			followList.setFwingNick(nickname);
			followListDAO.unFollow(followList);
		}
		return "memberBoard.do";
	}
	
	@RequestMapping("/followerList.do")
	public String followerList(HttpSession session, MemberVO member, Model model,
			FollowListVO followList) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			List<FollowListVO> myFolloWerList = new ArrayList<FollowListVO>();
			if(member.getNickname() == null) {
				followList.setFwingNick(loginUser.getNickname());
				myFolloWerList = followListDAO.getMyFolloWerList(followList);
				model.addAttribute("myInfo", "yes");
			} else {
				followList.setFwingNick(member.getNickname());
				myFolloWerList = followListDAO.getMyFolloWerList(followList);
				model.addAttribute("nickname", member.getNickname());
			}
			model.addAttribute("myFolloWerList", myFolloWerList);
			
			ArrayList<MemberVO> myFollowerListInfo = new ArrayList<MemberVO>();
			for(FollowListVO fwerNick  : myFolloWerList) {
				MemberVO memberVO = new MemberVO();
				memberVO.setNickname(fwerNick.getFwerNick());
				memberVO = memberDAO.getNicknameMember(memberVO);
				myFollowerListInfo.add(memberVO);
			}
			model.addAttribute("myFollowerListInfo", myFollowerListInfo);	
		}
		return "follow/followList.jsp";
	}
	
	@RequestMapping("/followingList.do")
	public String followingList(HttpSession session, MemberVO member, Model model,
			FollowListVO followList) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			List<FollowListVO> myFolloWingList = new ArrayList<FollowListVO>();
			if(member.getNickname() == null) {
				followList.setFwerNick(loginUser.getNickname());
				myFolloWingList = followListDAO.getMyFolloWingList(followList);
				model.addAttribute("myInfo", "yes");
			} else {
				followList.setFwerNick(member.getNickname());
				myFolloWingList = followListDAO.getMyFolloWingList(followList);
				model.addAttribute("nickname", member.getNickname());
			}
			model.addAttribute("myFolloWingList", myFolloWingList);
			
			ArrayList<MemberVO> myFollowingListInfo = new ArrayList<MemberVO>();
			for(FollowListVO fwingNick  : myFolloWingList) {
				MemberVO memberVO = new MemberVO();
				memberVO.setNickname(fwingNick.getFwingNick());
				memberVO = memberDAO.getNicknameMember(memberVO);
				myFollowingListInfo.add(memberVO);
			}
			model.addAttribute("myFollowingListInfo", myFollowingListInfo);
		}
		return "follow/followList.jsp";
	}
	
	@RequestMapping("/noticeFollow.do")
	public String noticeFollow(HttpSession session, FollowNoticeVO followNotice, Model model) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			followNotice.setFwerNick(loginUser.getNickname());
			followNotice.setFwingNick(loginUser.getNickname());
			List<FollowNoticeVO> noticeFollowList =
					followNoticeDAO.getNoticeFollow(followNotice);
			
			
			ArrayList<MemberVO> memberList = new ArrayList<MemberVO>();
			for (FollowNoticeVO nickname : noticeFollowList) {
				MemberVO member = new MemberVO();
				if(nickname.getFfwingNick() == null) {
					member.setNickname(nickname.getFwerNick());
					member = memberDAO.getNicknameMember(member);
					memberList.add(member);
				} else {
					member.setNickname(nickname.getFwingNick());
					member = memberDAO.getNicknameMember(member);
					memberList.add(member);
				}
			}
			model.addAttribute("noticeFollowList", noticeFollowList);
			model.addAttribute("memberList", memberList);
		}
		return "follow/noticeList.jsp";
	}
	
	@RequestMapping("/noticeGood.do")
	public String noticeGood(HttpSession session, FollowNoticeVO followNotice, Model model) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			followNotice.setFwerNick(loginUser.getNickname());
			followNotice.setFwingNick(loginUser.getNickname());
			List<FollowNoticeVO> noticeGoodList = 
					followNoticeDAO.getNoticeGood(followNotice);
			followNoticeDAO.noticeCheck(followNotice);
			int noticeSize = followNoticeDAO.getNoticeSize(followNotice);
			session.setAttribute("noticeSize", noticeSize);
			
			//좋아요한 게시물 정보를 가져옴 
			ArrayList<BoardVO> boardList = new ArrayList<BoardVO>();
			for (FollowNoticeVO bseq : noticeGoodList) {
				BoardVO board = new BoardVO();
				board.setBseq(bseq.getBseq());
				board = boardDAO.getBoardDetail(board);
				boardList.add(board);
			}
			
			//좋아요한사람 정보를 가져온다.
			ArrayList<MemberVO> memberList = new ArrayList<MemberVO>();
			for (FollowNoticeVO nickname : noticeGoodList) {
				MemberVO member = new MemberVO();
				if(nickname.getFfwingNick() == null) {
					member.setNickname(nickname.getFwerNick());
					member = memberDAO.getNicknameMember(member);
					memberList.add(member);
				} else {
					member.setNickname(nickname.getFwingNick());
					member = memberDAO.getNicknameMember(member);
					memberList.add(member);
				}
			}
			
			model.addAttribute("noticeGoodList", noticeGoodList);
			model.addAttribute("boardList", boardList);
			model.addAttribute("memberList", memberList);
		}
		return "follow/noticeList.jsp";
	}
	
	@RequestMapping("/noticeNews.do")
	public String noticeNews(HttpSession session, FollowNoticeVO followNotice, Model model) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			followNotice.setFwerNick(loginUser.getNickname());
			List<FollowNoticeVO> noticeNewsList =
					followNoticeDAO.getNoticeNews(followNotice);
			
			//팔로잉한 회원이 게시한 글의 디테일 정보를 가져온다.
			ArrayList<BoardVO> boardList = new ArrayList<BoardVO>();
			for (FollowNoticeVO bseq : noticeNewsList) {
				BoardVO board = new BoardVO();
				board.setBseq(bseq.getBseq());
				board = boardDAO.getBoardDetail(board);
				boardList.add(board);
			}
			
			//팔로잉한 회원의 정보를 가져온다.
			ArrayList<MemberVO> memberList = new ArrayList<MemberVO>();
			for (FollowNoticeVO nickname : noticeNewsList) {
					MemberVO member = new MemberVO();
					member.setNickname(nickname.getFwingNick());
					member = memberDAO.getNicknameMember(member);
					memberList.add(member);
			}
			
			model.addAttribute("noticeNewsList", noticeNewsList);
			model.addAttribute("boardList", boardList);
			model.addAttribute("memberList", memberList);
		}
		return "follow/noticeList.jsp";
	}
}
