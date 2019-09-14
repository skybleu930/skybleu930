package com.sns.view.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.sns.biz.dao.MemberDAO;
import com.sns.biz.dto.MemberVO;
import com.sns.util.SnsUtil;

@Controller
@SessionAttributes("loginUser")
public class MemberController {
	
	@Autowired
	MemberDAO memberDAO;
	//@Resource(name="memberImgUploadPath")
	String memberImgUploadPath = "image/member_image";
	@Autowired
	ServletContext ServletContext;
	
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String loginForm(HttpServletRequest request, SessionStatus sessionStatus) {
		if(request.getSession(false) != null) {
			sessionStatus.setComplete();
		}
		request.setAttribute("loginform", 1); //로그인 페이지 헤더 로그인 버튼 숨기기 
//		request.setAttribute("email", "aa");
//		request.setAttribute("pw", "aa");
		return "member/loginForm.jsp";
	}
	
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(MemberVO vo, Model model) {
		MemberVO memberVO = memberDAO.getMember(vo);
		if(memberVO != null) {
			model.addAttribute("loginUser", memberVO);
			return "redirect:main.do";
		} 
		return "member/loginFail.jsp";
	}
	
	@RequestMapping("/joinForm.do")
	public String joinForm(HttpServletRequest request, SessionStatus sessionStatus) {
		if(request.getSession(false) != null) {
				sessionStatus.setComplete();
		}	
		request.setAttribute("joinform", 1); //회원가입 페이지 헤더 회원가입 버튼 숨기기 
		return "member/joinForm.jsp";
	}
	
	@RequestMapping("/joinCheck.do")
	public String joinCheck(MemberVO vo, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(request.getParameter("hidden_email").trim().equals("1")) {
			session.setAttribute("email_nullCheck", "yes");
		}
		if(request.getParameter("hidden_nickname").trim().equals("1")) {
			session.setAttribute("nickname_nullCheck", "yes");
		}
		
		if(!vo.getEmail().equals("")) {
			request.setAttribute("email_message", memberDAO.checkEmail(vo)); 
			request.setAttribute("email", vo.getEmail());
		}
		
		if(!vo.getNickname().equals("")) {
			request.setAttribute("nickname_message", memberDAO.checkNickname(vo)); 
			request.setAttribute("nickname", vo.getNickname());
		}
		return "member/joinForm.jsp";
	}
	
	@RequestMapping("/join.do")
	public String join(MemberVO vo, Model model) {
		memberDAO.insertMember(vo);
		model.addAttribute("nickname", vo.getNickname());
		return "member/joinConfirm.jsp";
	}
	
	@RequestMapping("/logout.do")
	public String logout(SessionStatus sessionStatus, HttpServletRequest request) {
		//HttpSession session = request.getSession(false); //false는 또다른 세션을 생성하지 않고 있는 세션을 그대로 가져온다는 뜻이다.
		if(request.getSession(false) != null) {
			sessionStatus.setComplete();
		}
		return "redirect:index.do"; 
		//return "redirect:login.do";
	}
	
	@RequestMapping("/memberModify.do")
	public String memberModify(HttpSession session, @RequestParam MultipartFile upfile,
			MemberVO member) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			try {
				String path = ServletContext.getRealPath(memberImgUploadPath);
				String saveName = SnsUtil.uploadFile(upfile.getOriginalFilename(), 
													 upfile.getBytes(), 
													 path);
				member.setEmail(loginUser.getEmail());
				if(member.getPw().trim().equals("")) {
					member.setPw(loginUser.getPw());
				} 
				member.setImage(saveName);
				memberDAO.memberUpdate(member);
				session.setAttribute("loginUser", member);
			} catch (Exception e) {e.printStackTrace();}
		}
		return "redirect:myBoard.do";
	}
	
	@RequestMapping("/memberModifyForm.do")
	public String memberModifyForm(HttpSession session) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} 
		return "member/memberModify.jsp";
	}
	
	@RequestMapping("/memberModifyFormCheck.do")
	public String memberModifyFormCheck(HttpSession session, MemberVO member, Model model) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			if(!member.getNickname().trim().equals("")) {
				model.addAttribute("nickname_message", memberDAO.checkNickname(member)); //메세지에 따라 분기를 준다.
				model.addAttribute("nickname", member.getNickname());
			}
			model.addAttribute("oneCheck", "yes"); //지정된 input값이 한번
		}
		return "memberModifyForm.do";
	}
}
