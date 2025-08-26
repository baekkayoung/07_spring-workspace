package com.kh.ajax.contorller;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 빈등록 해야 디스패쳐 서블렛이 여기 꽂아줌
public class AjaxController {

	/* 
	 * 1.  HttpServletResponse 객체로 응답하기(기존 jsp/servlet 때 했던 Stream을 이용한 방식
	 * 
    @RequestMapping("ajax1.do")
    public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {

        // 요청처리를 위해 서비스 호출
        
        // 요청처리 다 됐다는 가정하에 요청한 그 페이지에 응답할 데이터가 있을 경우?
        String responseData = "응답문자열 : " + name + "는 " + age + "살 입니다.";
        
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().print(responseData);
    }
    */
    
	
	/*
	 *  2. 응답할 데이터를 문자열로 리턴
	 *  => responser 객체를 안쓸 수 있음
	 *  단, 문자열을 리턴하면 원래는 포워딩 방식임 => 응답뷰로 인식해서 해당 jsp view 페이지를 찾음 => 없으니까 404 나옴
	 *  따라서 내가 리턴하는 문자열이 응답뷰가 아닌 응답데이터라는 걸 선언하는
	 *  어노테이션 @ResponseBody를 붙여야 됨
	 */
	@ResponseBody //한글이 없으면 안 해도 됨
	@RequestMapping(value="ajax1.do", produces="text/html; charset=utf-8")
	public String ajaxMethod1(String name, int age) {
		String responseData = "응답문자열 : " + name + "는 " + age + "살 입니다.";
		return responseData; // 내가 주는 건 데이터라는 걸 알려줘야 함
		
	}
	


}