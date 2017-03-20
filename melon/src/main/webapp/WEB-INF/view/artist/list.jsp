<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Artist List</title>
<script type="text/javascript" src="/melon/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	$().ready(function(){
		<c:if test = "${isAdminUser || isOperatorUser}" >
		$("input[type=button]").click(function(){
			var writeDiv = $("<div id='writeDiv'></div>");
			writeDiv.css({
				position: 'absolute', // 클릭한 녀석부터 시작해라
				top: '20px',
				left: '20px',
				border: '1px solid #333333',
				padding: '15px',
				'z-index': 3, // z축으로 위로 올린다. 1 이상이면 위로 올릴 수 있다.
				'background-color' : '#FFFFFF'
			});
			writeDiv.load("/melon/artist/write"); // Ajax
			$(this).before(writeDiv);
		});
		</c:if>
	});
</script>
</head>
<body>
	<c:choose>
		<c:when test="${isAdminUser || isOperatorUser }">
			<input type="button" value="Artist 등록" />
		</c:when>
	</c:choose>

	<p>${artistCount }명의 가수들이 검색되었다.</p>
	<table>
		<tr>
			<th>번호</th>
			<th>아티스트명</th>
			<th>데뷔 날짜</th>
		</tr>
	<c:forEach items ="${artistList }" var="artist">
		<tr>
		<td>
			<fmt:parseNumber>
				${fn:split(artist.artistId, '-')[2]}
			</fmt:parseNumber>
			</td>
			<td>
				<a href="/melon/album/list?artistId=${artist.artistId }"> ${artist.member } </a>
			</td>
			<td>${artist.debutDate }</td>
		</tr>
	</c:forEach>
	</table>
	<div>
		<form id="searchForm">
			${pager}
		</form>
	</div>

</body>
</html>