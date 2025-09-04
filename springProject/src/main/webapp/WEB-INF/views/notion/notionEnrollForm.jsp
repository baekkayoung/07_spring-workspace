<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

 <style>
        #notionAddForm>table {
            width: 100%;
        }
        
        #notionAddForm>table * {
            margin: 5px;
        }
    </style>
</head>
<body>
<!-- 이쪽에 메뉴바 포함 할꺼임 -->

     <jsp:include page="../common/header.jsp"/>

     <div class="content">
         <br><br>
         <div class="innerOuter">
             <h2>경조사 게시글 추가하기</h2>
             <br>
 
             <form id="notionAddForm" method="post" action="" enctype="">
                 <table align="center">
                     <tr>
                         <th><label>구분</label></th>
                         <td>
                            <select class="form-control-file border" name="category">
                                <option value="결혼">결혼</option>
                                <option value="출산">출산</option>
                                <option value="부고">부고</option>
                         </select>
                        </td>
                     </tr>
                     <tr>
                         <th><label for="title">제목</label></th>
                         <td><input type="text" id="title" name="title" class="form-control" required></td>
                     </tr>
                     <tr>
                         <th><label for="writer">작성자</label></th>
                         <td><input type="text" id="writer" name="writer" class="form-control" value="${ loginUser.userName }" readonly></td>
                     </tr>
                    
                 </table>
                 <br>
 
                 <div align="center">
                     <button type="submit" class="btn btn-primary">등록하기</button>
                     <button type="reset" class="btn btn-danger">취소하기</button>
                 </div>
             </form>
         </div>
         <br><br>
     </div>
     
         <script>
            $(function(){
                $("#notionAddForm").on("submit", function(e){
                    e.preventDefault(); // 기존에 있떤 이벤트 막음 action으로 넘어가는걸 막음 (비동기로하려고하는 걸 막음)

                    let formData ={
                            title : $(this).find("input[name='title']").val(), // this=notionAddform id로 줘도 됨 굳이 this로 해봄
                            category : $(this).find("select[name='category']").val(),
                            writer : $(this).find("input[name='writer']").val()


                    };

                    $.ajax({
                        url:"add.notion",
                        method:"POST",
                        contentType:"application/json", // 우리가 너한테 보내는 데이터는 json 타입이야!
                        data:JSON.stringify(formData), // 우리의 일반 객체를 json 문자열로 바꿔줌! => Controller에서 Map으로 받으려고
                        success:function(res){

                            if(res == "success"){
                                alertify.alert("경조사 게시글 등록 완료!")
                                location.href="list.notion";

                            }else{
                                alertify.alert("경조사 게시글 등록 실패!")
                            }
                        }, error:function(){
                            alertify.alert("등록 실패")
                        }
                    })

                })
            })

         </script>
     
 
     <!-- 이쪽에 푸터바 포함할꺼임 -->
     <jsp:include page="../common/footer.jsp"/>
    
</body>
</html>