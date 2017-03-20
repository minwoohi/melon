<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Music List</title>
<script type="text/javascript" src="/melon/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	$().ready(function(){
		$("input[type=button]").click(function(){
			window.open("/melon/music/write?albumId=${param.albumId}", "Music 등록", "resizable=no, scrollbars=yes, toolbar=no, width=300px, height=500px, menubar=no");
		});
		
		$(".play").click(function(){
			var mp = $(this).data("mp");
			var albumid = $(this).data("albumid");
			
			var source = $("<source src='' type='audio/mp3'></source>");
			source.attr("src", '/melon/mp3/' + albumid + "/" + mp);
			
			$("#mp3player").find("video").html(source);
			$("#mp3player").find("video")[0].play();
		});
		
	});
	
	
</script>
</head>
<body>
	<input type="button" value="노래 등록" /><br/>
	
		<table>
			<tr>
				<th>번호</th>
				<th>노래 제목</th>
				<th>앨범명</th>
				<th>작곡가</th>
				<th>제작사</th>
				<th>가사</th>
				<th>좋아요</th>
			</tr>
			<c:forEach items= "${musicList}" var="music" varStatus="index">
			<tr>
				<td>${index.index+1}</td>
				<td>
					<a href="/melon/music/detail?musicId=${music.musicId }">${music.title}</a>
				</td>
				<td>${music.albumVO.albumName}</td>
				<td>${music.musician}</td>
				<td>${music.director}</td>
				<td>${music.lyrics}</td>
				<td class="play" data-albumid="${music.albumId}" data-mp="${music.mp3File}">듣기</td>
			</tr>
			</c:forEach>
	</table>
		<form id="searchForm">
			${pager}
		</form>
		<div id="mp3player">
			<video controls="controls">
				<source src="" type="audio/mp3"></source>
			</video>
		</div>
</body>
</html>