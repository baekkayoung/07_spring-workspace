package com.kh.spring.board.model.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.service.BoardServiceImpl;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
public class BoardController {
	
	@Autowired
	private BoardServiceImpl bService;
	
	
	// 메뉴바 클릭시 /list.bo (기본적으로 1번 페이징 요청)
	// 페이징바 클릭시 /list.bo?cpage=요청하는 페이지수
	/*
	@RequestMapping("list.bo")
	public String selectList(@RequestParam(value="cpage", defaultValue="1")int currentPage, Model model) {
		
		int listCount = bService.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		
		ArrayList<Board> list = bService.selectList(pi);
		
		model.addAttribute("pi",pi);
		model.addAttribute("list",list);
		
		// 포워딩할 뷰 (web-inf/views/board/boardListView.jsp)
		return "board/boardListView";
		
	}
	*/
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		return "board/boardEnrollForm";
	}
	
	@RequestMapping("list.bo")
	public ModelAndView selectList(@RequestParam(value="cpage", defaultValue="1")int currentPage, ModelAndView mv) {
		
		int listCount = bService.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		ArrayList<Board> list = bService.selectList(pi);
		
	
		
		/*
		mv.addObject("pi",pi);
		mv.addObject("list",list);
		mv.setViewName("board/boardListView");
		*/
		
		mv.addObject("pi",pi)
		  .addObject("list",list)
		  .setViewName("board/boardListView");
		
		return mv;
		
	}
	
	@RequestMapping("insert.bo")
	public String insertBoard(Board b, MultipartFile upfile, HttpSession session , Model model )  {
		System.out.println(b);
//		System.out.println(upfile); 첨부파일을 선택했든 안했든 생성된 객체임 (다만 filename에 원본명이 있냐 없냐 그 차이)
		
		// 전달된 파일이 있을 경우 => 파일명 수정 작업 후 서버 업로드 => 원본명, 서버 업로드된경로를 b에 마저 담기
		if(!upfile.getOriginalFilename().equals("")) {
		
			/*
			// 파일명 수정 작업 후 서버에 업로드 시키기("flower.png" => 2025082512345514789.png)
			String originName = upfile.getOriginalFilename();
			
			//"20250825123555"
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // "2025082132555"
			int ranNum = (int)(Math.random() * 90000 + 10000); // 85236 5자리 랜덤값
			String ext = originName.substring(originName.lastIndexOf(".")); // ".png"; 이 셋 결합
			
			String changeName = currentTime + ranNum + ext;
			
			// 업로드 시키고자하는 폴더의 물리적인 경로 알아내기
			String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
			
			try {
				upfile.transferTo(new File(savePath + changeName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			} //트라이캐치
			*/
			String changeName = saveFile(upfile, session);
			
			// 원본명, 서버업로드 된 경로 Board B에 마저 담기
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
		}
	
		// 넘어온 첨부파일이 있을 경우 b  : 제목, 작성자, 내용, 파일원봉면, 파일 저장경로
		// 첨부파일이 없을 경우 b : 제목, 작성자, 내용
		
		int result = bService.insertBoard(b);
		
		if (result > 0 ) { // 게시글리스트페이지
			session.setAttribute("alertMsg", "게시글이 성공적으로 등록되었습니다");
			return "redirect:list.bo";
			
		} else {
			model.addAttribute("errorMsg", "게시글 등록 실패");
			return "common/errorPage";
		}
	
	}
	
	@RequestMapping("detail.bo")
	public String selectBoard(int bno, Model model) {
		// bno에는 상세조회 하고자하는 해당 게시글 번호가 담겨있음
		
		// 해당 게시글의 조회수를 증가용 서비스 호출 결과 받기 (update하고옴)
		
		// >> 성공적으로 조회수 증가
		// >> boardDetailView.jsp상에 필요한 데이터 조회 (게시글상세정보조회)
		// 조회된 데이터 담아서
		// >> board/baoprddetialvire.jsp로 포회딩
		
		// >> 조회수 증가 실패
		// >> 에러페이지
		int result = bService.increaseCount(bno);
		if(result > 0) { // 조회수 증가 성공 시
	        // 2. 게시글 상세조회
	        Board b = bService.selectBoard(bno);
	        
	        // 3. 데이터 담기
	        model.addAttribute("b", b);
	        
	        // 4. forward (상세 페이지 JSP)
	        return "board/boardDetailView";
	        
	    } else { // 조회수 증가 실패 시
	        model.addAttribute("errorMsg", "게시글 상세조회 실패");
	        return "common/errorPage";
	    }
	}
	
	
	
	// 현재 넘어온 첨부파일 그 자체를 서버의 폴더에 저장시키는 역할
	
	public String saveFile(MultipartFile upfile, HttpSession session) {
		
	String originName = upfile.getOriginalFilename();
	
	//"20250825123555"
	String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // "2025082132555"
	int ranNum = (int)(Math.random() * 90000 + 10000); // 85236 5자리 랜덤값
	String ext = originName.substring(originName.lastIndexOf(".")); // ".png"; 이 셋 결합
	
	String changeName = currentTime + ranNum + ext;
	
	// 업로드 시키고자하는 폴더의 물리적인 경로 알아내기
	String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
	
	try {
		upfile.transferTo(new File(savePath + changeName));
	} catch (IllegalStateException | IOException e) {
		e.printStackTrace();
	} //트라이캐치
		return changeName;
	}
	
	
}

