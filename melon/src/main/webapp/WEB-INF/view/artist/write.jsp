<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Artist 등록 페이지</title>
<script type="text/javascript" src="/melon/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	$().ready(function(){
		$("#writeForm").find("input[type=button]").click(function(){ // id writeForm인 Element 하위에서 
			// input type이 button인 녀석을 클릭하면 function 수행해라.
			$.post("/melon/artist/write",
					{
						'artistName' : $("#artistName").val(),
						'debutDate' : $("#debutDate").val(),
						'debutTitle' : $("#debutTitle").val()
			// post 요청시 'artistName을 id가 artistName인 Element의 값으로
					}
			, function(response){
				// 요청 성공했는지 실패했는지 데이터
				if ( response == 'OK' ) {
					location.reload(); // 화면 새로고침
				}
				else{
					alert("Artist 등록 실패했습니다.");
				}
			});
		});
	});
</script>
</head>
<body>
	<form id="writeForm">
		<input type="text" id="artistName" placeholder="Artist 이름을 입력하세요"></br>
		<input type="date" id="debutDate" placeholder="Artist 데뷔일을 입력하세요"></br>
		<input type="text" id="debutTitle" placeholder="Artist 타이틀곡을 입력하세요"></br>
		<input type="button" value="등록" />
	</form>
</body>
</html>