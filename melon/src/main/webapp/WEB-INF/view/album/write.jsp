<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/melon/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	$().ready(function(){
		$("#writeForm").find("input[type=button]").click(function(){
			$("#writeForm").attr({
				"action": "/melon/album/write?artistId=${param.artistId}",
				"method": "post"
			});
			$("#writeForm").submit();
		});
	});
</script>
</head>
<body>
	<form id="writeForm" enctype="multipart/form-data"> 
		<input type="text" name="albumName" placeholder="앨범 명을 입력하세요." /><br/>
		<input type="date" name="releaseDate" plceholder="발매일을 선택하세요" /><br/>
		<input type="text" name="publisher" placeholder="발매사" /><br/>
		<input type="text" name="entertainment" placeholder="소속사" /><br/>
		<input type="text" name="genre" placeholder="장르" /><br/>
		<input type="file" name="post" accept=".gif, .jpg, .png"/><br/>
				
		<input type="button" value="등록" />
		
	</form>
</body>
</html>