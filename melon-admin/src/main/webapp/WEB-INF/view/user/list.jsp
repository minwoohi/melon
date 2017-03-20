<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>/WEB-INF/view/user/list</title>
<script type="text/javascript" src="/melon-admin/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	$().ready(function(){
		$("#selectForm").find("input[type=button]").click(function(){
			$("#selectForm").attr({
				"method": "post",
				"action": "/melon-admin/user/selectModify"
			});
			$("#selectForm").submit();
		});
		
		$("#checkBoxBtn").click(function(){
			$("#selectForm").attr({
				"method" : "post",
				"action" : "/melon-admin/user/checkedUserModify"
			});
			$("#selectForm").submit();
		});
	});
</script>
</head>
<body>
	<form id="selectForm">
	<table>
		<tr>
			<th>    </th>
			<th>번호</th>
			<th>ID</th>
			<th>이름</th>
			<th>Point</th>
			<th>권한</th>
		</tr>
		<c:forEach items="${userList }" var="user">
		<tr>
			<td><input type="checkbox" name="checkedUserId" value="${user.userId }" /></td>
			<td>${user.index }</td>
			<td>${user.userId }</td>
			<td>
				<a href="/melon-admin/user/detail?userId=${user.userId }">${user.userName }</a>
			</td>
			<td>${user.userPoint }</td>
			<td>${user.authorizationVO.authorizationName }</td>
		</tr>
		</c:forEach>
	</table><br/>
	<input type="button" id="checkBoxBtn" value="체크박스 수정" />
		<select name="beforeAuth" >
			<option value="">권한없음</option>
			<c:forEach items="${authList}" var="auth">
				<option value="${auth.authorizationId}">${auth.authorizationName }</option>
			</c:forEach>
		</select>
		권한을
		<select name="afterAuth">
			<option value="">권한없음</option>
			<c:forEach items="${authList}" var="auth">
				<option value="${auth.authorizationId}">${auth.authorizationName }</option>
			</c:forEach>
		</select> 권한으로
		<input type="button" id="optionAuthBtn" value="일괄수정" />
	</form>
<form id="searchForm">
	${pager }
</form>
</body>
</html>