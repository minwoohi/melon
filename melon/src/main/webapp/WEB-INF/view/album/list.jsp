<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Album List</title>
<script type="text/javascript" src="/melon/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	$().ready(function(){
		<c:if test = "${isAdminUser || isOperatorUser}" >
		$("input[type=button]").click(function(){
			window.open("/melon/album/write?artistId=${param.artistId}", "앨범 등록", "resizable=no, scrollbars=yes, toolbar=no, width=300px, height=500px, menubar=no");
		});
		</c:if>
	});
</script>
</head>
<body>
	<c:choose>
		<c:when test="${isAdminUser || isOperatorUser }">
			<input type="button" value="앨범 등록" /><br/>
		</c:when>
	</c:choose>
	
	<table>
		<tr>
			<c:forEach items="${albumList}" var="album" varStatus="index"> <!-- for문에서 i에 해당하는 녀석 -->
				<td>
					${index.index}<br/>
					<div>
						<a href="/melon/music/list?albumId=${album.albumId}&artistId=${album.artistId}">
						<!-- albumId를 추가하는 경우 다음 앨범 추측해 url로 직접 접근하는 것 막고싶음. 보안성 강화 -->
							<img src="/melon/album/post?albumId=${album.albumId}" width="150px" height="150px" /><br/> <!-- 주소를 가지고 있는 서블릿 -->
						</a>
						${album.albumName}<br/>
						${album.artistVO.member}
						아티스트명
					</div>
				</td>
				<c:if test="${(index.index + 1) % 5 == 0}" >
					</tr>
					<tr>
				</c:if>
			</c:forEach>
		</tr>
</table>
</body>
</html>