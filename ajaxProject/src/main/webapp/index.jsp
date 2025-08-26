<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<h1>Spring에서의 AJAX 사용법</h1>
	
	<h3>1. 요청시에 값을 전달하고, 응답 결과 받아보기</h3>
	이름 : <input type="text" id="name"> <br>
	나이 : <input type="number" id="age"> <br>
	<button onclick="test1()">전송</button>
	
	<script>
		function test1(){
			$.ajax({
				url : "ajax1.do",
				data : {
						name:$("#name").val(),
						age:$("#age").val()
				}, success:function(result){
					console.log(result);
				}, error:function(){
					console.log("ajax 통신실패");
				}				
			})
		}
	</script>
	
	
</body>
</html>