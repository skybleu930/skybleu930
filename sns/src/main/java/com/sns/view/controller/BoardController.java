package com.sns.view.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sns.biz.dao.BoardDAO;
import com.sns.biz.dao.BoardGoodDAO;
import com.sns.biz.dao.FollowListDAO;
import com.sns.biz.dao.FollowNoticeDAO;
import com.sns.biz.dao.MemberDAO;
import com.sns.biz.dao.ReplyDAO;
import com.sns.biz.dto.BoardGoodVO;
import com.sns.biz.dto.BoardVO;
import com.sns.biz.dto.FollowListVO;
import com.sns.biz.dto.FollowNoticeVO;
import com.sns.biz.dto.MemberVO;
import com.sns.biz.dto.ReplyVO;
import com.sns.util.SnsUtil;

@Controller
public class BoardController {

	@Autowired
	BoardDAO boardDAO;
	@Autowired
	MemberDAO memberDAO;
	@Autowired
	ReplyDAO replyDAO;
	@Autowired
	BoardGoodDAO boardGoodDAO;
	@Autowired
	FollowListDAO followListDAO;
	@Autowired
	FollowNoticeDAO followNoticeDAO;
	//@Resource(name="boardImgUploadPath")
	String boardImgUploadPath = "image/board_image";
	@Autowired
	ServletContext servletContext;
	
	@RequestMapping("/boardDetail.do")
	public String boardDetail(HttpSession session, MemberVO member, ReplyVO reply,
			BoardVO board, BoardGoodVO boardGood, Model model) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			//게시판 가져오는 구문 
			board = boardDAO.getBoardDetail(board);
			model.addAttribute("boardDetail", board);
			if(board.getNickname().equals(loginUser.getNickname())) {
				model.addAttribute("myBoard", "yes");
			}
			
			//게시판 작성자 프로필 가져오는 구문 
			member.setNickname(board.getNickname());
			member = memberDAO.getNicknameMember(member);
			model.addAttribute("profileImg", member.getImage());
			
			//게시판에 해당하는 댓글을 가져오는 구문 
			List<ReplyVO> replyList = replyDAO.getReplyList(reply);
			int n = replyList.size();
			model.addAttribute("replyList", replyList);		
			model.addAttribute("replyListSize", n);
			
			//댓글단 회원 프로필 이미지 가져오기 위한 구문
			ArrayList<MemberVO> memberList = new ArrayList<MemberVO>(); 
			for(ReplyVO replyVO : replyList) {
				if(replyVO.getDepth() == 0) { //댓글 프로필 
					member.setNickname(replyVO.getParentNick());
					memberList.add(memberDAO.getNicknameMember(member));
				} else { //대댓글 프로필
					member.setNickname(replyVO.getChildNick());
					memberList.add(memberDAO.getNicknameMember(member));
				}
			}
			model.addAttribute("memberList", memberList);
			boardGood.setBseq(board.getBseq());
			boardGood.setNickname(loginUser.getNickname());
			int goodCheck = boardGoodDAO.getBoardGood(boardGood);
			model.addAttribute("goodCheck", goodCheck);
		}
		return "board/boardDetail.jsp";
	}
	
	@RequestMapping("/boardGood.do")
	public String boardGood(HttpSession session, BoardGoodVO boardGood, BoardVO board,
			FollowNoticeVO followNotice, FollowListVO followList, @RequestParam String nickname) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			
			//게시판에 좋아요한 내역을 저장
			boardGood.setNickname(loginUser.getNickname());
			boardGoodDAO.setBoardGood(boardGood);
			
			//게시판에 좋아요 플러스
			boardDAO.boardGoodPuls(board);
			//내가 아닌 다른 회원의 게시판을 좋아요 했을때 작성자에게 알려준다.
			if(!loginUser.getNickname().equals(nickname)) {
				followNotice.setFwerNick(loginUser.getNickname());
				followNotice.setFwingNick(nickname);
				followNoticeDAO.setFollowNoticeGood(followNotice);
			}
			//팔로워에게 내가 좋아요한 게시판을 알려준다.
			followList.setFwingNick(loginUser.getNickname());
			List<FollowListVO> myFolloWerList =
					followListDAO.getMyFolloWerList(followList);
			if(myFolloWerList.size() != 0) {
				for (FollowListVO fwerNick : myFolloWerList) {
					followNotice.setFwerNick(fwerNick.getFwerNick());
					followNotice.setFwingNick(loginUser.getNickname());
					followNotice.setFfwingNick(nickname);
					followNoticeDAO.setFollowNoticeGood(followNotice);
				}
			}
		}
		return "boardDetail.do";
	}

	@RequestMapping("/boardGoodDelete.do")
	public String boardGoodDelete(HttpSession session, BoardGoodVO boardGood, BoardVO boardVO,
			FollowNoticeVO followNotice, @RequestParam String nickname) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			boardGood.setNickname(loginUser.getNickname());
			boardGoodDAO.deleteBoardGood(boardGood);
			
			boardDAO.boardGoodMinus(boardVO);
			
			if(!loginUser.getNickname().equals(nickname)) {
				followNotice.setFwerNick(loginUser.getNickname());
				followNoticeDAO.deleteFollowNoticeGood(followNotice);
			}
		}
		return "boardDetail.do";
	}
	
	@RequestMapping("/boardWriteForm.do")
	public String boardWriteForm(HttpSession session) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} 
		return "board/boardWriteForm.jsp";
	}
	
	@RequestMapping("/boardWrite.do")
	public String BoardWrite(HttpSession session, BoardVO board, FollowListVO followList,
			FollowNoticeVO followNotice, @RequestParam MultipartFile upfile) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			try {
				String path = servletContext.getRealPath(boardImgUploadPath);
				String saveName = SnsUtil.uploadFile(upfile.getOriginalFilename(), 
													 upfile.getBytes(), 
													 path);
				board.setNickname(loginUser.getNickname());
				board.setImage(saveName);
				boardDAO.setMyBoard(board);
				int bseq = boardDAO.getMaxBoardBseq(board);
				
				//팔로워에게 내가 작성한 게시판을 알려준다.
				followList.setFwingNick(loginUser.getNickname());
				List<FollowListVO> myFolloWerList =
						followListDAO.getMyFolloWerList(followList);
				if(myFolloWerList.size() != 0) {
					for (FollowListVO fwerNick : myFolloWerList) {
						followNotice.setBseq(bseq);
						followNotice.setFwerNick(fwerNick.getFwerNick());
						followNotice.setFwingNick(loginUser.getNickname());
						followNoticeDAO.setFollowNoticeBoard(followNotice);
					}
				}
			} catch (Exception e) {e.printStackTrace();}
		}
		return "myBoard.do";
	}
	
	@RequestMapping("/myBoard.do")
	public String myBoard(HttpSession session, Model model, BoardVO board,
			FollowListVO followList) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			//게시판 가져오는 구문 
			board.setNickname(loginUser.getNickname());
			List<BoardVO> myBoardList 
				= boardDAO.getMyBoard(board);
			model.addAttribute("myBoardList", myBoardList);
			
			followList.setFwerNick(loginUser.getNickname());
			followList.setFwingNick(loginUser.getNickname());
			model.addAttribute("myFolloWingList", followListDAO.getMyFolloWingList(followList));
			model.addAttribute("myFolloWerList", followListDAO.getMyFolloWerList(followList));
			model.addAttribute("myBoard", "yes");
		}
		return "board/myBoard.jsp";
	}
	
	@RequestMapping("/memberBoard.do")
	public String memberBoard(HttpSession session, Model model, MemberVO member,
			BoardVO board, FollowListVO followList, @RequestParam String nickname) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			//게시판 회원정보 가져오는 구문 
			member = memberDAO.getNicknameMember(member);
			model.addAttribute("member", member);
			
			//게시판 가져오는 구문 
			List<BoardVO> myBoardList 
				= boardDAO.getMyBoard(board);
			model.addAttribute("myBoardList", myBoardList);
			
			followList.setFwingNick(nickname);
			followList.setFwerNick(nickname);
			List<FollowListVO> myFolloWingList = 
			followListDAO.getMyFolloWingList(followList);
			List<FollowListVO> myFolloWerList = 
			followListDAO.getMyFolloWerList(followList);
			model.addAttribute("myFolloWingList", myFolloWingList);
			model.addAttribute("myFolloWerList", myFolloWerList);
			
			followList.setFwerNick(loginUser.getNickname());
			followList.setFwingNick(nickname);
			int result = followListDAO.confirmFollow(followList);
			model.addAttribute("result", result);
		}
		return "board/myBoard.jsp";
	}
	
	@RequestMapping("/replyList.do")
	public String replyList(HttpSession session, BoardVO board, ReplyVO reply, Model model) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			board = boardDAO.getBoardDetail(board);
			model.addAttribute("boardDetail", board);
			
			List<ReplyVO> replyList = replyDAO.getReplyList(reply);
			model.addAttribute("replyList", replyList);	
			model.addAttribute("replyMsg", "yes");	
		}
		return "board/boardDetail.jsp";
	}
	
	@RequestMapping("/replyReg.do")
	public String replyReg(HttpSession session, ReplyVO	reply, HttpServletRequest request) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if(loginUser == null ) {
			return "index.do";
		} else {
			if(reply.getParentNick() == null) { //게시판에 바로 댓글을 달때
				ReplyVO ReplyVO = replyDAO.getMaxGrpAndSeq(reply);
				if(ReplyVO != null) {
					reply.setGrp(ReplyVO.getGrp()+1);
					reply.setSeq(ReplyVO.getSeq()+1);
				} else {
					reply.setGrp(1);
					reply.setSeq(1);
				}
				reply.setParentNick(loginUser.getNickname());
				replyDAO.setParentReply(reply);
			} else { //댓글에 댓그를 달았을때 
				reply.setComment(request.getParameter("comment"+request.getParameter("rseq")));
				reply.setChildNick(loginUser.getNickname());
				reply.setDepth(reply.getDepth()+1);
				replyDAO.setChildReply(reply);
			}
		}
		return "boardDetail.do";
	}
 }

