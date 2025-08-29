package com.kh.spring.notion.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class NotionConfig {
	
	//Vo처럼 사용 해볼거임
	
	public static String TOKEN;
	public static String DATABASE_ID;
	
	// 클래스가 처음 로딩이 될 때 딱 한 번만 실행
	static {
		
		Properties prop = new Properties();
		
		// 노션과 우리 프로그램의 통로가 필요 => NotionConfig의 class 파일이 있는 곳 찾아가서(resources)연결
		// 클래스패스에서 파일을 찾아서 스트림을 연결하는 구문.
		// .getClassLoader() => 파일 찾는 도우미
		// .getResourceAsStream() => 스트림 연결
		InputStream inputStream = NotionConfig.class.getClassLoader().getResourceAsStream("notion.properties");
		
		if(inputStream != null) {
			try {
				prop.load(inputStream); 
				// null이 아니면 파일을 읽는다.
				
				TOKEN = prop.getProperty("notion.token"); // key 값 가지고 오기 : map 계열 특징(key:value)
				DATABASE_ID = prop.getProperty("notion.database.id");
				inputStream.close();
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
	

}
