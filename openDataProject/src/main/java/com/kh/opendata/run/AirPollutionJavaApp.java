package com.kh.opendata.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.opendata.model.vo.AirVo;

public class AirPollutionJavaApp {
	
	public static final String SERVICE_KEY ="43a956f1b17e2b6e6884deafb2c9f8158f7d755e6ae161ca1e8d1bead1817e28";
	

	public static void main(String[] args) throws IOException {
		
		// openAPI 서버로 요청하고자하는 url 만들기
		
		String url ="https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"; //?
		url += "?serviceKey=" + SERVICE_KEY ; // 서비스키가 제대로 부여되지 않았을 경우 >SERVICE_KEY_IS_NOT_REGISTERED_ERROR<
		url += "&sidoName=" + URLEncoder.encode("부산", "UTF-8"); // 우리가 요청시 전달값 중 한글이 있을 경우에는 encoding 해야됨!
//		url += "&returnType=xml"; 비교적 가시적
		url += "&returnType=json"; // 개발에서 사용하기에는 적합
		
		// ** HttpURLConnection 객체를 활용해서 OPENAPI 요청 절차 **
		// 1. 요청하고자하는 url 전달 java.net.URL 객체 생성
		URL requestUrl = new URL(url);
		
		// 2. 1번 과정으로 생성된 URL 객체 가지고 HttpURLConnection 객체 생성
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		
		// 3. 요청에 필요한 Header 설정하기
		urlConnection.setRequestMethod("GET"); // 문서에 REST 확인해서 
		
		// 4. 해당 OPENAPI 서버로 요청 보낸 후 입력 스트림을 통해 응답 데이터 읽어들이기
		BufferedReader br = new BufferedReader( new InputStreamReader(urlConnection.getInputStream())); 
		// 보조스트림이기때문에 기반 스트림이 있어야 함. 스트림에는 바이트기반 문자기반이 있는데 버퍼드리더는 문자기반이기때문에 기반스트림 중 문자기반스트림을 제시해야 함
		// urlConnection으로 getInputStream 얻어올 수 있음.
		// urlConnecion에서 제공하는 인풋스트림이라서 바이트 스트림 젠더  new 끼워서 바꿔주기
		
		
		
		String responseText=""; // sysout 많이 하면 프로그램 무거워지니까 이 방법./ 
		String line; // 다 읽고 나면 null
		while ((line = br.readLine()) != null) {
//			System.out.println(line);
			responseText += line;
		}
//		System.out.println(responseText); 
		
		/*
		 * {
			"response":
				{
					"body":
					{
						"totalCount":35,
						"items":
							[
								{
									"so2Grade":"1",
									"coFlag":null,
									"khaiValue":"61",
									"so2Value":"0.003",
									"coValue":"0.3",
									"pm10Flag":null,
									"o3Grade":"2",
									"pm10Value":"23",
									"khaiGrade":"2",
									"sidoName":"부산",
									"no2Flag":null,
									"no2Grade":"1",
									"o3Flag":null,
									"so2Flag":null,
									"dataTime":"2025-08-28 10:00",
									"coGrade":"1",
									"no2Value":"0.010",
									"stationName":"광복동",
									"pm10Grade":"1",
									"o3Value":"0.042"
								},
		 * 
		 */
		// responseText 문자열이라 json으로 만들어야함...
		// Jsonject, JsonArray, JsonElemnet(gson라이브러리) 이용해서 파싱할 수 있음 => 내가 원하는 데이터만을 추출할 수 있음
		// 각각의 item 정보를 AriVo객체에 담기 => ArrayList에 차곡차곡 쌓기
		
		// 대문자 => JSON 라이브러리 소문자 => gson라이브러리
		
		// 문자열을 바꿔주기 ! json타입으로 바꾸는데 그 중에서도 오브젝트로 바꿔주기
		JsonObject totalObj = JsonParser.parseString(responseText).getAsJsonObject();
		JsonObject responseObj = totalObj.getAsJsonObject("response"); // resopnse라는 속성에 접근 => {} JsonObject 안에 넣어준 것
		// System.out.println(responseObj); // response안에 있는 body부터 나오기 시작
		JsonObject bodyObj = responseObj.getAsJsonObject("body"); // body라는 속성에 접근 
//		System.out.println(bodyObj); // body안의 totoalCount 부터 나오기 시작
		int totalCount = bodyObj.get("totalCount").getAsInt(); // totalCount라는 속성에 접근해서 int 형으로 변환
		
		JsonArray itemArr = bodyObj.getAsJsonArray("items"); // 배열로 들어오게 됨
		
		ArrayList<AirVo> list = new ArrayList<AirVo>();
		
		for (int i=0; i<itemArr.size(); i++) {
			JsonObject item = itemArr.get(i).getAsJsonObject(); // 배열에서 i번째 요소 꺼내고 JsonObject 객체{}로 변환
//			System.out.println(item);
			
			// JSON데이터(문자열)을 객체(AriVo)에 하나하나 넣어주기
			AirVo air = new AirVo();
			air.setStationName(item.get("stationName").getAsString()); // 광복동을 뽑아서 StationName이라는 것에 넣어줌
			air.setDataTime(item.get("dataTime").getAsString());
			air.setKhaiValue(item.get("khaiValue").getAsString());
			air.setPm10Value(item.get("pm10Value").getAsString());
			air.setSo2Value(item.get("so2Value").getAsString());
			air.setCoValue(item.get("coValue").getAsString());
			air.setNo2Value(item.get("no2Value").getAsString());
			air.setO3Value(item.get("o3Value").getAsString());
			
			list.add(air);
		}
		
//		System.out.println(list);
		for(AirVo a : list) {
			System.out.println(a); // 이제 한 세트씩 나옴
		}

		// 5. 다 사용한 Stream 반납
		br.close();
		urlConnection.disconnect();
		
		
		
		
		
	}

}
