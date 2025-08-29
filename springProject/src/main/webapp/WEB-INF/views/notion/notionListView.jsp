<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
        #notionList {
            text-align: center;
        }
        
        #notionList>tbody>tr:hover {
            cursor: pointer;
        }  
</style>
</head>
<body>
<!-- 이쪽에 헤더바 포함할꺼임 -->
    <jsp:include page="../common/header.jsp"/>

    <div class="content">
        <br><br>
        <div class="innerOuter" style="padding:5% 10%;">
            <h2>경조사 게시판</h2>
            <br>
            	<c:if test="${ not empty loginUser }">
	            <!-- 로그인후 상태일 경우만 보여지는 글쓰기 버튼-->
           		<a class="btn btn-secondary btn-sm" style="float:right" href="enrollForm.notion">경조사 추가</a>
           		</c:if>
            <br></br>
            <table id="notionList" class="table table-hover" align="center">
                <thead>
                  <tr>
                    <th>구분</th>
                    <th width="400">제목</th>
                    <th>작성일</th>
                    <th>작성자</th>
                  </tr>
                </thead>
                <tbody>
	                   
	                   
                </tbody>
            </table>
            <br>
		
			
            
           
            <br clear="both"><br>
            

            
        </div>
        <br><br>
    </div>
    
    <!-- 이건 일반 String이 아니라 json 데이터라는 의미 -->
    <script id="notionData" type="application/json">
    	${dbData}
    </script>
    
    
    <script>
    
    	$(function(){
    		let dbData = JSON.parse($("#notionData").text());
    		// console.log(dbData);
    		// f12로 들어가서 확인해보면 타고타고 들어야가야 찾을 수 있음
    		// results에 객체들이 담긴 배열이 있음.
    		
    		let results = dbData.results;  // results의 객체들이 results 배열안에 담김
    		// console.log(results); // 배열에 0번, 1번
    		
    		let $tbody = $("#notionList tbody");
   			$tbody.empty();
   			
   			// (요소, 함수)  , index,item
   			$.each(results, function(index, item){
   				
   				let page = item.properties;
   				// console.log(page); // 작성자, 구분, 작성일, 제목 속성들이 객체로 뽑힘
   				let title = page.제목 && page.제목.title.length > 0 ? page.제목.title[0].text.content : "";
   				// 제목이라는 속성이 있다 트루씨..
   				// 있는가? 만족 1을 넣겠다.
   				let category = page.구분 && page.구분.select.name ? page.구분.select.name : "";
   				
   				let writer = page.작성자 && page.작성자.rich_text.length > 0 ? page.작성자.rich_text[0].text.content : "";
   				
   				let date = page.작성일 && page.작성일.date.start ? page.작성일.date.start : "";
   			
   				console.log(title, category, writer, date);
   				
   				let $tr =$("<tr></tr>")
   				$tr.append("<td>" + category + "</td>");
   				$tr.append("<td>" + title + "</td>");
   				$tr.append("<td>" + date + "</td>");
   				$tr.append("<td>" + writer + "</td>");
   				$tbody.append($tr);
   				
   			});
   			
    	});
    
    </script>
    
   
    

    <!-- 이쪽에 푸터바 포함할꺼임 -->
    <jsp:include page="../common/footer.jsp"/>



</body>
</html>