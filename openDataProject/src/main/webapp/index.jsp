<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<h2>실시간 대기 오염 정보</h2>
	지역
	<select id="location">
		<option>서울</option>
		<option>부산</option>
		<option>대전</option>
	</select>
	<button id="btn1">해당 지역 대기오염정보 확인</button>
 
 	<table id="result1" border="1" align="center">
 		<thead>
 			<tr>
 				<th>측정소명</th>
 				<th>측정일시</th>
 				<th>통합대기환경수치</th>
 				<th>미세먼지농도</th>
 				<th>일산화탄소농도</th>
 				<th>이산화질소농도</th>
 				<th>아황산가스농도</th>
 				<th>오존농도</th>
 			</tr>
 		</thead>
 		
 		<tbody>
 		
 		</tbody>
 		

 	</table>
 	
 	
 	<script>
 		$(function(){
 			$("#btn1").click(function(){
 				$.ajax({
 					url:"air.do",
 					data:{location:$("#location").val()},
 					success:function(data){
 						
 						
 					
 						//console.log(data.response.body.items)
 						
 						/* json 형식으로 응답데이터를 받을 때
 						const itemArr = data.response.body.items;
 						
 						let value = "";
 						for(let i in itemArr){
 							// console.log(itemArr[i])
 							let item = itemArr[i];
 							
 							value += "<tr>"
									+ "<td>" + item.stationName + "</td>"
									+ "<td>" + item.dataTime + "</td>"
									+ "<td>" + item.khaiValue + "</td>"
									+ "<td>" + item.pm10Value + "</td>"
									+ "<td>" + item.coValue + "</td>"
									+ "<td>" + item.no2Value + "</td>"
									+ "<td>" + item.so2Value + "</td>"
									+ "<td>" + item.o3Value + "</td>"
 								 + "</tr>";
 						}
 						$("#result1 tbody").html(value);*/
 					
 						// xml 형식으로 응답 데이터를 받을 때
 						
 						//console.log(data)
 						// jQuery에서의 find 메소드 : 기준이 되는 요소의 하위요소들 중 특정 요소를 찾을 때 사용 (html, xml 다 사용 가능)
 						//console.log($(data).find("item")) // jQuery화를 해줘야 함. 
 						// 배열로 보임 
 						// 담아보기
 						// xml 형식의 응답데이터를 받았을 때
 						// 1. 응답데이터 안에 실제 데이터가 담겨있는 요소 선택
 						let itemArr = $(data).find("item");
 						
 						// 2. 반복문을 통해 실제 데이터가 담긴 요소들에 접근해서 동적으로 요소를 만들기
 						let value ="";
 						itemArr.each(function(i, item){ // 반복문과 좀 차이
 							//console.log($(item).find("stationName").text()) // <stationName>중구</stationName>에서 텍스트만
 							
 							value += "<tr>"
	 									+ "<td>" + $(item).find("stationName").text() + "</td>"
	 									+ "<td>" + $(item).find("dataTime").text() + "</td>"
	 									+ "<td>" + $(item).find("khaiValue").text() + "</td>"
	 									+ "<td>" + $(item).find("pm10Value").text() + "</td>"
	 									+ "<td>" + $(item).find("coValue").text() + "</td>"
	 									+ "<td>" + $(item).find("no2Value").text() + "</td>"
	 									+ "<td>" + $(item).find("so2Value").text() + "</td>"
	 									+ "<td>" + $(item).find("o3Value").text() + "</td>"
 									+"</tr>"
 							
 							})
 						// 3. 동적으로 만들어낸 요소를 화면에 출력
 							
 						$("#result1 tbody").html(value);
 					
 					}, error:function(){
 						
 					}
 				});
 			})
 		})
 	</script>
 	
 	
 	<h2>산불예보정보</h2>
 	<input type="button" value="실행" id="btn2">
 	
 	<div id="result2"></div>
 	
 	<script>
 	 	$(function(){
 	 		
 	 		/*
 	 		$("#btn2").click(function(){
 	 			$.ajax({
 	 				url:"fire.do",
 	 				success:function(data){
 	 					//console.log($(data).find("item"))
 	 					
 	 					let $table = $("<table border='1'></table>");
 	 					let $thead = $("<thead></thead>");
 	 					let headTr = "<tr>"
 	 								+"<th>면적</th>"
 	 								+"<th>데이터기준일자</th>"
 	 								+"<th>산불위험낮음</th>"
 	 								+"<th>산불위험예보최대값</th>"
 	 								+"</tr>";
 	 					$thead.html(headTr);
 	 					
 	 					let $tbody = $("<tbody></tbody>");
 	 					let bodyTr = "";
 	 					
 	 					
 	 					$(data).find("item").each(function(i, item){ // 이게 뭐지
 	 						//console.log(item)
 	 						
 	 						bodyTr += "<tr>"
 	 									+"<td>" + $(item).find("area").text() + "</td>"
 	 									+"<td>" + $(item).find("analdate").text() + "</td>"
 	 									+"<td>" + $(item).find("d1").text() + "</td>"
 	 									+"<td>" + $(item).find("maxi").text() + "</td>"
 	 								+ "</tr>"
 	 					});
 	 					$tbody.html(bodyTr);
 	 					
 	 					
 	 					$table.append($thead, $tbody);
 	 					$table.appendTo("#result2"); 
 	 					
 	 					$table.append($thead, $tbody)
 	 						   .appendTo("#result2");
 	 					
 	 					
 	 					
 	 				},error:function(){
 	 					
 	 				} 
 	 			})
 	 		})	*/
 	 		 // 화살표 함수 적용!
 	 		$("#btn2").click(()=>{
 	 			
 	 			$.ajax({
 	 				url:"fire.do",
 	 				success:data=>{
 	 					
 	 					let $table = $("<table border='1'></table>");
 	 		 	 		let $thead = $("<thead></thead>");
 	 		 	 		let headTr = "<tr>"
 	 		 	 					+"<th>면적</th>"
 	 		 	 					+"<th>데이터기준일자</th>"
 	 		 	 					+"<th>산불위험낮음</th>"
 	 		 	 					+"<th>산불위험예보최대값</th>"
 	 		 	 					+"</tr>";
 	 		 	 		$thead.html(headTr);
 	 		 	 					
 	 		 	 		let $tbody = $("<tbody></tbody>");
 	 		 	 		let bodyTr = "";
 	 		 	 		
 	 		 	 		$(data).find("item").each((i,item)=>{
	 	 		 	 		bodyTr += "<tr>"
								+"<td>" + $(item).find("area").text() + "</td>"
								+"<td>" + $(item).find("analdate").text() + "</td>"
								+"<td>" + $(item).find("d1").text() + "</td>"
								+"<td>" + $(item).find("maxi").text() + "</td>"
							+ "</tr>"
							
 	 		 	 		});	
 	 		 	 		
 	 		 	 	
 	 		 	 		$tbody.html(bodyTr);
		 	 		 	 	$table.append($thead, $tbody)
							   .appendTo("#result2");
 	
 	 				}, error:()=>{
 	 					console.log("ajax 통신 실패");
 	 				}
 	 			})
 	 		});
 	 		
 	 		
 	 	})
 	
 	 	/*	
 	 		** 화살표 함수
 	 		익명함수를 화살표함수로 작성할 수 있음
 	 		"function(){}"를 "()=>{}" 이런 식으로 작성 가능
 	 		
 	 		"function(data){}"를 "(data) => {}" 이런식으로 작성 가능
 	 		
 	 		"function(a,b){}"를 "(a,b) => {}" 이런식으로 작성 가능
 	 		
 	 		"function(){return 10;}"를  "() => return 10;" 이런식으로 작성 가능
 	 	
 	 	*/
 	 	
 	 	

 	</script>
 	
 	<h3>재난 피해자 심리회복지원사업 건수</h3>
 	
	
 	
	<button id="btn3">조회</button>
	
	
	
	<table id="result3" border="1" align="center">
 		<thead>
 			<tr>
 				<th>연도</th>
 				<th>합계</th>
 				<th>서울</th>
 				<th>부산</th>
 				<th>대구</th>
 				<th>인천</th>
 				<th>광주</th>
 				<th>대전</th>
 				<th>울산</th>
 				<th>세종</th>
 			</tr>
 		</thead>
 		
 		<tbody>
 		
 		</tbody>
 		

 	</table>
 	
 					
 	
 	<script>
 		
 		$(function(){
 			$("#btn3").click(function(){
 				$.ajax({
 					url:"emergency.do",
 					success:function(data){
 						const rowObj = data.MentalRcovSptPrjDisasterVict[1].row;
 						
 						
 						let value = "";
 						for(let i in rowObj){
 							console.log(rowObj[i])
 							
 							let row = rowObj[i];
 							
 							value += "<tr>"
 										+"<th>" +  row.bas_yy+ "</th>"
 										+"<td>" +  row.tot+ "</td>"
		 								+"<td>" +  row.lseoul+ "</td>"		
		 								+"<td>" +  row.lbusan+ "</td>"		
		 								+"<td>" +  row.ldaegu+ "</td>"		
		 								+"<td>" +  row.lincheon+ "</td>"		
		 								+"<td>" +  row.lgwangju+ "</td>"		
		 								+"<td>" +  row.ldaejeon+ "</td>"		
		 								+"<td>" +  row.lulsan+ "</td>"		
		 								+"<td>" +  row.lsejong+ "</td>"		
 									+ "</tr>";
 						}
 						$("#result3 tbody").html(value);
		
 					}, error:function(){
 						console.log("ajax 통신 오류! 다시 시도하십시오.")
 					}
 				 });
 		    });
 		});
 		
 		
 			
 	</script>
 	
 	
 	
 	
 	
 	
</body>
</html>