<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>/WEB-INF/view/rank/ranking.jsp</title>
<script type="text/javascript" src="/melon/static/js/jquery-3.1.1.min.js"></script>
</head>
<body>
	<table>
		<tr>
			<th>순위</th>
			<th>ID</th>
			<th>앨범ID</th>
			<th>제목</th>
			<th>좋아요</th>
			<th>작사</th>
			<th>작곡</th>
			<th>가사</th>
		</tr>
		<c:forEach items="${musicList }" var="music" varStatus="index">
		<tr>
			<td>${index.index + 1 }</td>
			<td>${music.musicId }</td>
			<td>${music.albumId }</td>
			<td>
				<a href="/melon-admin/music/detail?musicId=${music.musicId }">${music.title }</a>
			</td>
			<td>${music.likeCount }</td>
			<td>${music.musician }</td>
			<td>${music.director }</td>
			<td>${music.lyrics }</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>