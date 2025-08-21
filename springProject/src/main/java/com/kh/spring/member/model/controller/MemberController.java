package com.kh.spring.member.model.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.member.model.service.MemberServiceImpl;
import com.kh.spring.member.model.vo.Member;

@Controller // Controller 타입의 어노테이션을 붙여주면 빈 스캐닝을 통해 자동으로 빈 등록
public class MemberController {
	
//	private MemberServiceImpl mService = new MemberServiceImpl();
	
	@Autowired //(Dependency Injection) DI 특징  ??
	private MemberServiceImpl mService; // 스프링이 알아서 주입 @Service라고 붙어있는 걸... 원래 Impl떼고 하는건데 나중에 삭제해도 ㄱㅊ
	
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
		
		
	}

}
