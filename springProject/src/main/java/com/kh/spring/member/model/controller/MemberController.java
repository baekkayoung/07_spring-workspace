package com.kh.spring.member.model.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.model.service.MemberServiceImpl;
import com.kh.spring.member.model.vo.Member;

@Controller // Controller 타입의 어노테이션을 붙여주면 빈 스캐닝을 통해 자동으로 빈 등록
public class MemberController {
	
//	private MemberServiceImpl mService = new MemberServiceImpl();
	
	@Autowired //(Dependency Injection) DI 특징  ??
	private MemberServiceImpl mService; // 스프링이 알아서 주입 @Service라고 붙어있는 걸... 원래 Impl떼고 하는건데 나중에 삭제해도 ㄱㅊ
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	/*
	@RequestMapping(value="login.me") // RequestMapping 타입의 어노테이션을 붙여줌으로써 HandlerMapping 등록
	public void loginMember() {
		
	}
	
	public void insertMember() {
			
	}
	*/
	
	/*
	 * * jsp에서 파라미터(요청시 전달값)을 받는 방법
	 *  
	 * 1. HttpServletRequest를 이용해서 전달받는 방법 (기존의 jsp/servlet 때의 방식)
	 *    해당 메소드의 매개변수로 HttpServletRequst를 작성해두면
	 *    스프링 컨테이너 메소드 호출시(실행시) 자동으로 해당 객체를 생성해서 인자로 주입해줌
	 *  
	 */
	
	/*
	public String loginMember(HttpServletRequest request) {
		// 바로 Request.get => 안됨. Request로 받아올 수 있는 doGet 메소드랑은 다르니까 => 만들어주기
		String userId = request.getParameter("id");
		String userPwd = request.getParameter("pwd");
		
		System.out.println("id" + userId );
		System.out.println("pwd"  + userPwd );
		
		return "main";
		
	}
	*/
	
	/*
	 * 2. @RequestParam 어노테이션을 이용하는 방법
	 * 	request.getParameter("키") : 벨류의 역할을 대신해주는 어노테이션
	 * 
	 */
	
	/*
	@RequestMapping("login.me")
	public String loginMember(@RequestParam(value="id", defaultValue = "aaa") String userId,
							  @RequestParam(value="pwd") String userPwd) {
		
		System.out.println("id" + userId );
		System.out.println("pwd"  + userPwd );
		
		return "main";
	}
	*/
	
	/*
	 * 3. @RequestParam 어노테이션을 생략하는 방법
	 * ** 단, 매개변수명과 jsp의 name값을 동일하게 셋팅
	 */
	
	/*
	@RequestMapping("login.me")
	public String loginMember(String id, String pwd) {
		
		System.out.println("id : " + id );
		System.out.println("pwd : "  + pwd );
		
	
		Member m = new Member();
		m.set ~ 
		
		
		return "main";
	}
	*/
	
	/*
	 * 4. 커맨드 객체 방식
	 *  해당 메소드의 매개변수로 요청시 전달값을 담고자하는 vo 클래스 타입을 세팅 후 요청시 전달값의 키값(name값)을 vo클래스에 담고자하는 필드명으로 작성
	 *  
	 *  스프링이 해당 객체를 기본생성자로 생성 후 sertter 메소드 찾아서 요청시 전달값을 해당 필드에 담아주는 내부적인 원리
	 *  
	 *  ** 반드시 jsp name값과 담고자하는 필드명 동일해야됨!! **
	 */
	
	/*
	 * * 요청 처리 후 응답페이지로 포워딩 또는 url 재요청, 응답데이터를 답는 방법
	 * 
	 * 1. 스프링에서 제공하는 Model 객체를 사용하는 방법
	 * 	  포워딩할 뷰로 전달하고자하는 데이터를 맵형식으로 담을 수 있는 영역
	 *    Model 객체는 requestScope이다.
	 *    단, serAttribute가 아닌 addAttribute 메소드 이용	
	 * 
	 */
	
	/*
	@RequestMapping("login.me") // HttpSession session 세션 필요하면 그냥 여기 넣어서 하면 됨
	public String loginMember(Member m, Model model, HttpSession session) { // Member 객체를 (이전까지 id였음)id를 userId로 바꿔줘야함. 자동으로 주입
//		System.out.println("id : " + m.getUserId() );
//		System.out.println("pwd : "  + m.getUserPwd() );
		
		Member loginUser = mService.loginMember(m);
		
		if(loginUser == null) { // 로그인 실패 => 에러페이지로 포워딩
			
			model.addAttribute("errorMsg", "로그인 실패");
			// "/WEB-INF/views/			common/errorPage				.jsp"
			return "common/errorPage"; // request.getDis~ 이런거 안 해도 됨 이거는 포워딩
			
		}else { // 로그인 성공 => 메인페이지
			session.setAttribute("loginUser", loginUser);
			return "redirect:/" ;// send~ url 재요청..  어디로?  main 쓰면 포워딩이니까.. 여기는 재요청해야하는거자나~ 
		}
		
		
	}*/
	
	/*
	 * 2. 스프링에서 제공하는 ModelAndView 객체를 이용하는 방법
	 * 
	 * 	  Model은 데이터를 Key-value 세트로 담을 수 있는 공간
	 * 	  View는 응답뷰에 대한 정보를 담을 수 있는 공간
	 */
	
	@RequestMapping("login.me") 
	public ModelAndView loginMember(Member m, HttpSession session, ModelAndView mv ) {
		
		/* 암호화 작업 전에 했던 과정 (member-mapper.xml이랑 세트)
		Member loginUser = mService.loginMember(m);
		
		if(loginUser == null) { // 로그인 실패 => 에러페이지로 포워딩
			
			mv.addObject("errorMsg","로그인 실패");
			mv.setViewName("common/errorPage"); // 얘도 머리랑 꼬리는 붙음
			
		}else { // 로그인 성공 => 메인페이지
			
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/");
			
		} return mv;
		*/
		
		// 암호화 작업 후에 해야되는 과정
		// Member m userId 필드 : 사용자가 입력한 아이디
		// Member m userPwd 필드: 사용자가 입력한 비밀번호(평문)
		Member loginUser = mService.loginMember(m);
		// loginUser : 오로지 아이디만을 가지고 조회한 회원 객체
		// loginUser userPwd 필드 : db에 기록된 비번(암호문)
		
		
		// 매치(평문,암호문)-> t/f
		if(loginUser != null && bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) {
			// 로그인 성공
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/");
			
		} else {
			// 로그인 실패
			mv.addObject("errorMsg","로그인 실패");
			mv.setViewName("common/errorPage"); 
		} 
		
		return mv;
		
	}
	
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		session.invalidate(); // 모든 세션 완료 시키는..
		return "redirect:/";
		
	}
	
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		// /WEB-INF/views/     "member/memberEnrollForm"      .jsp 포워딩
		return "member/memberEnrollForm";
		
	}
	
	@RequestMapping("insert.me")
	public String insertMember(Member m, Model model, HttpSession session) {
		// 커맨드 방식. vo랑 jsp의 네임값이 동일해야 주입이 가능함!
		 
//		System.out.println(m); 
		// 1. 한글이 깨짐 -> 인코딩 설정 필요 -> 스프링에서 제공하는 인코딩 필터 등록 (web.xml에 등록)
		// 2. 나이를 입력하지 않았을 경우 "" 빈 문자열이 넘어오는데 int형 필드에 담을 수 없어서 400 에러 발생
		//	  => Member 클래스의 age 필드를 int형 --> String 형으로 변경
		// 3. 비밀번호가 사용자가 입력한 있는 그대로의 평문
		// 	  => Bcrypt 방식으로 암호화를 통해서 암호문으로 변경
		//	  => 1. spring 시큐리티 모듈에서 제공 => 라이브러리 필요 => pom.xml에 추가
		//	  => 2. bcryptPassWordEncoder라는 클래스를 빈으로 등록
		//	  => 3. web.xml에 spring-security.xml 파일을 pre-loading 할 수 있도록 작성
		
//		System.out.println("평문 : " + m.getUserPwd());
		
		// 암호화 작업 (암호문을 만들어내는 과정)
//		bcryptPasswordEncoder.encode(rawPassword); 평문!
		String encPwd =  bcryptPasswordEncoder.encode(m.getUserPwd());
//		System.out.println(encPwd);
		
		m.setUserPwd(encPwd); // MEmber객체에 userPwd에 평문이 아닌 암홈누으ㅜ로 변ㄱ여
		int result = mService.insertMember(m);
		
		if(result >0) { // 성공 => 메인페이지 url 재요청
			session.setAttribute("alertMsg", "회원가입에 성공했습니다.");
			return "redirect:/";
		} else { // 실패 => 에러페이지
			model.addAttribute("errorMsg", "회원가입실패");
			return "common/errorPage";
		}
		
	}
	
	@RequestMapping("myPage.me")
	public String myPage() {
		return "member/myPage"; // myPage.jsp
	}
	
	
	@RequestMapping("update.me")
	public String updateMemer(Member m, Model model, HttpSession session) {
		
		int result = mService.updateMember(m);
		
		if(result > 0) {
			// db로 부터 수정된 회원정보를 다시 조회해와서
			// session에 loginUser라는 키값으로 덮어 씌워야 함!
//			Member updateMem = mService.loginMember(m); + 
//			session.setAttribute("loginUser", updateMem); =
			session.setAttribute("loginUser", mService.loginMember(m));
			
			// alert 띄워줄 문구 세팅
			session.setAttribute("alertMsg", "성공적으로 회원정보 변경되었습니다");
			
			// 마이페이지 url 재요청
			return "redirect:myPage.me";
			
			
		}else {
			model.addAttribute("errorMsg","회원 정보 수정 실패");
			return "common/errorPage";
		}
	}
	
	
	
	@RequestMapping("delete.me")
	public String deleteMember(String userId, String userPwd, HttpSession session, Model model) {
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		userId = loginUser.getUserId();
		
	    if(loginUser == null || !loginUser.getUserId().equals(userId)) {
	        model.addAttribute("errorMsg", "잘못된 접근입니다.");
	        return "common/errorPage";
	    }
	     
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		if(!bcrypt.matches(userPwd, loginUser.getUserPwd())) {
	        model.addAttribute("errorMsg", "비밀번호가 일치하지 않습니다.");
	        return "common/errorPage";
	    }
		
		 int result = mService.deleteMember(userId);
		    if(result > 0) {
		        session.invalidate();
		        return "redirect:/"; // 탈퇴 성공
		    } else {
		        model.addAttribute("errorMsg","회원삭제 실패");
		        return "common/errorPage";
		    }
	}
	
	@ResponseBody // 응답뷰를 찾게돼서 이상해짐. 내가 보내는 데이터는 응답뷰가 아니라 글자 자체의 데이터다 !!
	@RequestMapping("idCheck.me")
	public String idCheck(String checkId) {
		int count = mService.idCheck(checkId);
		
		/* 3항 연산자로 하면 더 가능
		if( count > 0 ) { // 이미 존재하는 아이디 => 사용 불가능(NNNNN)
			return "NNNNN";
		}else { // 사용 가능(NNNNY)
			return "NNNNY";
		}
		*/
		return count > 0 ? "NNNNN" : "NNNNY";
	}
}
