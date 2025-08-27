package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	/*
	 * * Interceptor (정확히는 HandlerInterceptor)
	 * - 해당 Controller를 실행하기 전에, 실행된 후에 낚아채서 실행할 내용 작성
	 * - 로그인 유무 판단, 회원의 권한을 체크
	 * 
	 * preHandle(전처리) : DispatcherServlet이 Controller를 호출하기 전에 낚아채는 영역
	 * postHandle(후처리) : 컨트롤러에서 요청 처리 후 DispatcherServlet으로 뷰 정보가 리턴되는 순간 낚아채는 영역
	 * 
	 */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// true 리턴 => 기존 요청 흐름대로 Controller 제대로 실행
		// false 리턴 => Controller 실행되지 않음
		
		//로그인이 됐는지 안됐는지?
		HttpSession session = request.getSession();
		
		
		// 현재 요청을 보낸 사람이 로그인 되어 있을 경우 => Controller 실행
		if(session.getAttribute("loginUser")!=null) {
			return true;
		}else {
			// 로그인이 되어있지 않을 경우 => Controller 실행 x
			session.setAttribute("alert", "로그인 후 이용가능한 서비스입니다.");
			response.sendRedirect(request.getContextPath());
			return false;
		}
	}
}
