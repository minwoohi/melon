<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
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
				"method" : "post",
				"action" : "/melon/music/write?albumId=${param.albumId}"
			});
			$("#writeForm").submit();
		});
	});
</script>
</head>
<body>
	<form id="writeForm" enctype="multipart/form-data">
		<input type="text" name="title" placeholder="제목"/><br/>
		<input type="text" name="musician" placeholder="작곡가" /><br/>
		<input type="text" name="director"  placeholder="작사가"/><br/>
		<input type="file" name="mp3File" accept=".mp3" placeholder="음악 파일"/><br/>
		<textarea name="lyrics" placeholder="가사"></textarea><br/>
		<input type="button" value="등록"/>
	</form>
</body>
</html>