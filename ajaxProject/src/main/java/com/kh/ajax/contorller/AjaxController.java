package com.kh.ajax.contorller;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.ajax.model.vo.Member;

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
	
	/*
	@ResponseBody //한글이 없으면 안 해도 됨
	@RequestMapping(value="ajax1.do", produces="text/html; charset=utf-8")
	public String ajaxMethod1(String name, int age) {
		String responseData = "응답문자열 : " + name + "는 " + age + "살 입니다.";
		return responseData; // 내가 주는 건 데이터라는 걸 알려줘야 함
		
	}*/
	
	/*
	// 다수의 응답데이터가 있을 경우?
	@RequestMapping("ajax1.do") 
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
		 response -> 응답을위한거. 보내주고 싶다.. 스트림출력해서 보내줄 수 있었음.
		 요청 처리를 다 했따는 가정하에 데이터 응답.
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().print(name); // getwrtiet는 io해야되는데. => thorews
		response.getWriter().print(age);
		 succes의 매개변수 result에 넣어짐
		 => 하나의 문자열로 연이어져서 넘어옴 : 여러개를 출력하기에는 적절하지않음
		
	
	
	JSON(JavaScript Object Notation)형태로 담아서 응답
	 JSONArray => [값, 값, 값, ...] => Java의 ArrayList와 유사
	 JSONObject => 객체 형태 {키:벨류, 키:벨류} => Java의 HashMap이랑 유사
	 MAven으로 Json을 pom.xml에 추가해주기~ 
	
	// 첫번째 방법. JsonArray
	JSONArray jArr = new JSONArray(); // []
	jArr.add(age);
	jArr.add(name);
	
	
	// 두번째 방법. JSONObject로 담아서 응답
	
	JSONObject jobj = new JSONObject();
	jobj.put("name", name); // {name:'차은우'}
	jobj.put("age", age); // {age:20, name:'차은우'} 순서 상관 x 담김
	
	
	response.setContentType("application/json; charset=utf-8");
	response.getWriter().print(jobj);
	
	
	}*/
	
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces="application/json; charset=utf-8")
	public String ajaxMethod1(String name, int age) {
		JSONObject jobj = new JSONObject(); // {}
		jobj.put("name", name);
		jobj.put("age", age);
		
		return jobj.toJSONString(); // jsp가 호출됨 내가 보내는건 jsp가 아니라 응답 데이터야 => responseBody
		// JSON을 String으로 바꿔주는 메소드 => toJSONString
		// name:차은우,age:20.jsp를 찾으면 안되니까 body 필수!
		// {name: '차은우', age: 20}로 잘 넘어옴
		
	}
	
	/*
	@ResponseBody
	@RequestMapping(value="ajax2.do", produces = "application/json; charset=utf-8")
	public String ajaxMethod2(int num) {
		
//		Member m = mService.selectMember(num);
		Member m = new Member("user01", "pass01", "차은우", 20, "01011112222");
		// 디비 만들기 귀찮으니까 걍 대충 만들어준거임
	
		// JSON 형태로 만들어서 응답
		// 하나만 가지고 올거면 오브젝트로
		JSONObject jobj = new JSONObject(); // {}
		jobj.put("userId", m.getUserId());
		jobj.put("userName",m.getUserName());
		jobj.put("age", m.getAge());
		jobj.put("phone", m.getPhone());
		
		return jobj.toJSONString();
		// {phone: '01011112222', userName: '차은우', userId: 'user01', age: 20} 색별로 다르게 나옴
	 
		// 귀찮자나.. json 이라는 라이브러리 pom.xml에 또 뭐 추가
	}
	*/
	
	
	@ResponseBody
	@RequestMapping(value="ajax2.do", produces = "application/json; charset=utf-8")
	public String ajaxMethod2(int num) {
		
		// Member m = mService.selectMember(num);
		Member m = new Member("user01", "pass01", "차은우", 20, "01011112222");
		return new Gson().toJson(m); // Member의 필드를 보고 키값을 알아서  자동으로 넣어줌 간편.,
		
	}
	
	@ResponseBody
	@RequestMapping(value="ajax3.do", produces = "application/json; charset=utf-8")
	public String ajaxMethod3() {
		
//		ArrayList<Member> list = mService.selectList(); 이거를 했다치고 가라로 만들어
		ArrayList<Member> list = new ArrayList<Member>();
		list.add(new Member("user01","pass01","차은우",20, "01011111111"));
		list.add(new Member("user02","pass02","차은지",20, "01022222222"));
		list.add(new Member("user03","pass03","차은혁",20, "01033333333"));
		
		return new Gson().toJson(list);  // [{},{},{}] 
	
	}
	
	
}