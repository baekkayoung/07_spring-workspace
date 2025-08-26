package com.kh.spring.board.model.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.tomcat.util.log.UserDataHelper.Mode;
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
	
	
	// 만약 다중파일 업로드시?
	// 여러개의 input 타입이 file인 요소에 동일한 키값 부여 (ex:upfile)
	// MultipartFile[] upfile로 받으면 됨 배열로! 0번 인덱스부터 차곡차곡 담기게 됨
	
	
	@RequestMapping("insert.bo")
	public String insertBoard(Board b, MultipartFile upfile, HttpSession session , Model model )  {
//		System.out.println(b);
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
		int result = bService.increaseCount(bno);
		// >> 성공적으로 조회수 증가
		// >> boardDetailView.jsp상에 필요한 데이터 조회 (게시글상세정보조회)
		// 조회된 데이터 담아서
		// >> board/baoprddetialvire.jsp로 포회딩
		
		// >> 조회수 증가 실패
		// >> 에러페이지
		
		if(result > 0) { // 조회수 증가 성공 시
	        // 2. 게시글 상세조회
	        Board b = bService.selectBoard(bno);
	        
	        // 3. 데이터 담기
	        model.addAttribute("b", b);
	        
	        // 4. forward (상세 페이지 JSP)
	        return "board/boardDetailView";
	        
	    } else { // 조회수 증가 실패 시
	        model.addAttribute("errorMsg", "게시글 상세조회 실패"); // ModelAndView로 해도 됨
	        return "common/errorPage";
	    }
	}
	
	@RequestMapping("delete.bo") // 첨부파일도 삭제
	public String deleteBoard(int bno, String filePath, HttpSession session, Model model) { 
		
		int result = bService.deleteBoard(bno); // jsp에서 name값이 bno 4
		
		if(result > 0 ) { 
			// 삭제 성공
			// 첨부파일이 있었을 경우 -> 파일삭제
			if(!filePath.equals("")) { // filePath = " resources/xxx/xx.png"
				//delete를 삭제하기 위해서 file이라는 객체를 만든거임
				new File (session.getServletContext().getRealPath(filePath)).delete();
			}
			session.setAttribute("alertMsg", "성공적으로 게시글이 삭제되었습니다");
			return "redirect:list.bo"; 
		}else { // 삭제 실패
			model.addAttribute("errorMsg","게시글 삭제 실패");
			return "common/errorPage";
			
		}
	}

	@RequestMapping("updateForm.bo") // Model -> 기존데이터를 가지고 화면에 가야 가지고올 수 있으니까 담아서가려고
	public String updateForm(int bno, Model model) {
		model.addAttribute("b", bService.selectBoard(bno));// b라는 키값에 정보가 담김
		return  "board/boardUpdateForm";
		
	}
	
	@RequestMapping("update.bo") // 인젝션 해주려면 jsp 네임이랑 여기랑 이름이 똑같아야함
	public String updateBoard(Board b, MultipartFile reupfile, HttpSession session, Model model) {
		
		// 새로 넘어온 첨부파일이 있을 경우
		if(!reupfile.getOriginalFilename().equals("")) { // 첨부를 안 했다는 의미
		
			// 기존에 첨부파일이 있었을 경우 => 기존의 첨부파일을 지워야 함
			if(b.getOriginName() != null) { // 물리적 삭제
				new File(session.getServletContext().getRealPath(b.getChangeName())).delete();
			}
			// 방금 새로 넘어온 첨부파일을 서버에 업로드 시켜야 함
			String changeName = saveFile(reupfile, session);
			
			// b에 새로 넘어온 첨부파일의 원본명, 첨부파일에 대한 체인지네임에 저장경로 담기
			b.setOriginName(reupfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/"+changeName);
			
		}
		/*
		 * b 에 boardNo, boardTitle, boardContent 무조건 담겨 있음
		 * 
		 * 1. 새로 첨부된 파일이 x, 기존 첨부 파일 x
		 * 	  => originName : null / chagneName : null
		 *    
		 * 2. 새로 첨부된 파일 x, 기존 첨부 파일 o
		 * 	  => originName : 기존파일원본명, changeName: 기존파일경로
		 * 
		 * 3. 새로 첨부된 파일 o, 기존 첨부 파일 x
		 * 	  => 새로 전달된 파일 서버에 업로드
		 *    => originName : 새로전달된파일원본명, changeName : 새로전달된파일경로
		 * 
		 * 4. 새로 첨부된 파일 o, 기존 첨부 파일 o
		 * 	  => 기존의 파일 삭제 , 새로 전달된 파일 서버에 업로드
		 *    => originName : 새로전달된파일원본명, changeName : 새로전달된파일경로
		 */
		
		int result = bService.updateBoard(b);
		
		if(result > 0) {
			// 수정 성공 => 상세페이지(detail.bo)로 url 재요청 -> 한 번 가서 봐봐 => bno라는 변수를 넘겨야 하겠네
			// detail.bo?bno=해당게시글번호 url 재요청 그러니까 bno 있어야겠찌
			session.setAttribute("alertMsg", "성공적으로 게시글이 수정되었습니다");
			return "redirect:detail.bo?bno=" + b.getBoardNo();
			
		} else {
			// 수정 실패 => 에러메세지
			model.addAttribute("errorMsg","게시글 수정 실패");
			return "common/errorPage";
		}
		
	}
	
	
	
	
	
	// 현재 넘어온 첨부파일 그 자체를 서버의 폴더에 저장시키는 역할
	// uploadFiles에 저장시키는
	
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

