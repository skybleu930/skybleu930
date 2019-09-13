package com.sns.view.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sns.biz.dao.MemberDAO;
import com.sns.biz.dao.MessageDAO;
import com.sns.biz.dto.MemberVO;
import com.sns.biz.dto.MessageVO;
@Controller
public class MessageController {

	@Autowired
	MessageDAO messageDAO;
	@Autowired
	MemberDAO memberDAO;
	
	@RequestMapping("/messageForm.do")
	public String messageForm(HttpSession session, Model model,
			@RequestParam String dearNick, @RequestParam String profileImg) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			model.addAttribute("dearNick", dearNick);
			model.addAttribute("profileImg", profileImg);
		}
		return"message/message_form.jsp";
	}
	
	@RequestMapping("/messageSend.do")
	public String messageSend(HttpSession session, MessageVO message, Model model) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			message.setFromNick(loginUser.getNickname());
			messageDAO.setMessage(message);
			model.addAttribute("sendOk", "ok");
		}
		return"message/message_form.jsp";
	}
	
	@RequestMapping("/messageList.do")
	public String messageList(HttpSession session, MessageVO message, Model model) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			//메세지 불러오는 구문
			message.setDearNick(loginUser.getNickname());
			List<MessageVO> messageList = 
					messageDAO.getMessageList(message);
					messageDAO.messageCheck(message);
			int messageSize = messageDAO.getMessageSize(message);
			session.setAttribute("messageSize", messageSize);
			model.addAttribute("messageList", messageList);
			
			ArrayList<MemberVO> messageListInfo = new ArrayList<MemberVO>();
			for(MessageVO fromNick  : messageList) {
				MemberVO member = new MemberVO();
				member.setNickname(fromNick.getFromNick());
				member = memberDAO.getNicknameMember(member);
				messageListInfo.add(member);
			}
			model.addAttribute("messageListInfo", messageListInfo);
		}
		return"message/messageList.jsp";
	}
	
	@RequestMapping("/messageDetail.do")
	public String messageDetail(HttpSession session, Model model, MessageVO message, MemberVO member) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			//메세지 불러오는 구문
			message = messageDAO.getMessage(message);
			model.addAttribute("message", message);
			
			member.setNickname(message.getFromNick());
			member = memberDAO.getNicknameMember(member);
			model.addAttribute("messageInfo", member);
		}
		return "message/messageDetail.jsp";
	}
	
	@RequestMapping("/sendMessageList.do")
	public String sendMessageList(HttpSession session, MessageVO message, Model model) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			//메세지 불러오는 구문
			message.setFromNick(loginUser.getNickname());
			List<MessageVO> sendMessageList = 
					messageDAO.getSendMessageList(message);
			model.addAttribute("sendMessageList", sendMessageList);
			
			//메시지를 보낸 회원 정보
			ArrayList<MemberVO> sendMessageListInfo = new ArrayList<MemberVO>();
			for(MessageVO dearNick  : sendMessageList) {
				MemberVO member = new MemberVO();
				member.setNickname(dearNick.getDearNick());
				member = memberDAO.getNicknameMember(member);
				sendMessageListInfo.add(member);
			}
			model.addAttribute("sendMessageListInfo", sendMessageListInfo);
		}
		return "message/messageList.jsp";
	}
	
	@RequestMapping("/sendMessageDetail.do")
	public String sendMessageDetail(HttpSession session, MessageVO message, Model model, MemberVO member) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			//메세지 불러오는 구문
			message = messageDAO.getMessage(message);
			int connectMseq = 0;
			if(messageDAO.getConnectMseq(message) != null) {
				connectMseq = messageDAO.getConnectMseq(message).getMseq();
			}
			model.addAttribute("connectMseq", connectMseq);
			model.addAttribute("sendMessage", message);
			
			member.setNickname(message.getDearNick());
			MemberVO sendMessageInfo = memberDAO.getNicknameMember(member);
			model.addAttribute("sendMessageInfo", sendMessageInfo);
		}
		return "message/messageDetail.jsp";
	}
	
	@RequestMapping("/reMessageSend.do")
	public String reMessageSend(HttpSession session, MessageVO message) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			message.setFromNick(loginUser.getNickname());
			messageDAO.setMessage(message);
			messageDAO.ReMessageComplete(message);
		}
		return "message/messageDetail.jsp";
	}
}
