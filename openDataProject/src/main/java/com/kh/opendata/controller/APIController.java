package com.kh.opendata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController { 
	
	private static final String SERVICE_KEY="43a956f1b17e2b6e6884deafb2c9f8158f7d755e6ae161ca1e8d1bead1817e28";
	
	/* josn 형식으로 응답할때의 메소드
	@ResponseBody
	@RequestMapping(value="air.do", produces="application/json; charset=utf-8") // 리턴하는 이 String 데이터를 json으로 
	public String airPollution(String location) throws IOException { // index.jsp에서 data에 있는 location
		
		String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + SERVICE_KEY;
		url += "&sidoName=" + URLEncoder.encode(location,"utf-8"); // 예외처리 IOException
		url += "&returnType=json";
		
		URL requestUrl = new URL(url); // 내가 가진 url을 가지고 요청할 수 있는 객체 만들기
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		
		// 겟방식인지 뭔지 헤더설정
		
		urlConnection.setRequestMethod("GET");
		//한줄한줄 읽기위해.  (문자기반스트림) => 바이트기반인거고.. 앞에는 문자기반이라 바이트기반을 문자기반으로 바꿔주눈 젠더인 뉴인풋스트림리더로 감싸주기
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		
		String responseText="";
		String line;
		
		while((line = br.readLine()) != null){ // 한줄한줄 넣다가 끝나면 null 뜨고 멈춤
			responseText += line;
		}
		
		br.close();
		urlConnection.disconnect();
		
//		System.out.println(responseText); // 문자열이기때문에 활용할 수 없어서 json으로 
		
		return responseText;
	}
	*/
	
	
	@ResponseBody
	@RequestMapping(value="air.do", produces="text/xml; charset=utf-8") // 리턴하는 이 String 데이터를 json으로 
	public String airPollution(String location) throws IOException { // index.jsp에서 data에 있는 location
		
		String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + SERVICE_KEY;
		url += "&sidoName=" + URLEncoder.encode(location,"utf-8"); // 예외처리 IOException
		url += "&returnType=xml"; // 여기랑 produeces를 xml
		
		URL requestUrl = new URL(url); // 내가 가진 url을 가지고 요청할 수 있는 객체 만들기
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		
		// 겟방식인지 뭔지 헤더설정
		
		urlConnection.setRequestMethod("GET");
		//한줄한줄 읽기위해.  (문자기반스트림) => 바이트기반인거고.. 앞에는 문자기반이라 바이트기반을 문자기반으로 바꿔주눈 젠더인 뉴인풋스트림리더로 감싸주기
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		
		String responseText="";
		String line;
		
		while((line = br.readLine()) != null){ // 한줄한줄 넣다가 끝나면 null 뜨고 멈춤
			responseText += line;
		}
		
		br.close();
		urlConnection.disconnect();
		
//		System.out.println(responseText); // 문자열이기때문에 활용할 수 없어서 json으로 
		
		return responseText;
	}
	
	@ResponseBody
	@RequestMapping(value="fire.do", produces="text/xml; charset=utf-8")
	public String fire() throws IOException {
		String url = "https://apis.data.go.kr/1400377/forestPoint/forestPointListGeongugSearch";
		url += "?serviceKey=" + SERVICE_KEY;
		url += "&numOfRows=10";
		url += "&pageNo=1";
		url += "&_type=xml";
		
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		
		urlConnection.setRequestMethod("GET");
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText ="";
		String line;
		
		while((line=br.readLine()) != null) {
			responseText += line;
		}
		br.close();
		urlConnection.disconnect();
		
		return responseText;
		
	}
	
	@ResponseBody
	@RequestMapping(value="emergency.do", produces="application/json; charset=utf-8")
	public String emergency() throws IOException {
		String url = "https://apis.data.go.kr/1741000/MentalRcovSptPrjDisasterVict/getMentalRcovSptPrjDisasterVictList";
		url += "?serviceKey=" + SERVICE_KEY;
		url += "&pageNo=1";
		url += "&numOfRows=10";
		url += "&type=json";
		
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		
		urlConnection.setRequestMethod("GET");
		BufferedReader br = new BufferedReader( new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText = "";
		String line;
		
		while((line = br.readLine()) != null) {
			responseText += line;
		}

		br.close();
		urlConnection.disconnect();
		
		return responseText;	
	}
	
	

}
