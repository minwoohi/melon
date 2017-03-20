<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/melon/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	$().ready(function(){
		$("#likeBtn").find("input[type=button]").click(function(){
			$.post("/melon/music/doModifyLikeCount", {
				"likeCnt" : "${music.likeCount}",
				"musicId" : "${param.musicId}"
			}, function(response){
				var jsonObj = JSON.parse(response);
				
				if(jsonObj.status == "success"){
					var td = $('<td>'+jsonObj.music.likeCount+'♥</td>');
					$("#likeBtn").find("input[type=button]").attr("disabled", "disabled");
					$("#likeBtn").find("#likeCnt").remove();
					$("#likeBtn").append(td);
				}
			})
		});
	});
</script>
</head>
<body>
	<h1>${music.title }</h1>
		<form id="likeBtn">
			<input type="button" name="likeCnt" value="좋아요" />
			<p id="likeCnt"> ${music.likeCount } ♡</p>
		</form>
	<h3>${music.albumVO.artistVO.member }</h3>
	<hr/>
	<img src="/melon/album/post?albumId=${music.albumId}" width="150px" height="150px" /><br/> <!-- 주소를 가지고 있는 서블릿 -->
	<video src="/melon/mp3/${music.albumId }/${music.mp3File}" controls="controls"></video>
	<!-- controls : 제어할 수 있는 제어기 (?) -->
</body>
</html>