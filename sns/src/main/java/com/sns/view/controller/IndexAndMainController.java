package com.sns.view.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.sns.biz.dao.BoardDAO;
import com.sns.biz.dao.FollowNoticeDAO;
import com.sns.biz.dao.MemberDAO;
import com.sns.biz.dao.MessageDAO;
import com.sns.biz.dto.FollowNoticeVO;
import com.sns.biz.dto.MemberVO;
import com.sns.biz.dto.MessageVO;

@Controller
public class IndexAndMainController {
	
	@Autowired
	BoardDAO boardDAO;
	@Autowired
	MessageDAO messageDAO;
	@Autowired
	FollowNoticeDAO followNoticeDAO;
	@Autowired
	MemberDAO memberDAO;
	
	@RequestMapping("/index.do")
	public String indexView(HttpServletRequest request, SessionStatus sessionStatus) {
		if(request.getSession(false) != null) {
			sessionStatus.setComplete();
		}
		return "index.jsp";
		//return "redirect:main.do";
	}
	
	@RequestMapping("/main.do")
	public String mainView(HttpSession session, MessageVO message, Model model, 
			FollowNoticeVO followNotice, HttpServletRequest request) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
//			MemberVO vo = new MemberVO();
//			vo.setEmail("aa");
//			vo.setPw("aa");
//			loginUser = memberDAO.getMember(vo);
//			session.setAttribute("loginUser", loginUser);
		} else {
			String search = "";
			if(request.getParameter("search") != null) {
				 search = request.getParameter("search");
			}
			
			//게시판 불러오는 구문
			model.addAttribute("boardList", boardDAO.getBoard(search));
			
			//메세지 불러오는 구문
			message.setDearNick(loginUser.getNickname());
			session.setAttribute("messageSize", messageDAO.getMessageSize(message));
			
			//팔로우 알림 불러오는 구문 
			followNotice.setFwerNick(loginUser.getNickname());
			followNotice.setFwingNick(loginUser.getNickname());
			session.setAttribute("noticeSize", followNoticeDAO.getNoticeSize(followNotice));
		}
		return "main.jsp";
	}
}
