<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>/WEB-INF/view/user/detail</title>
<script type="text/javascript" src="/melon-admin/static/js/jquery-3.1.1.min.js"></script>
<script>
	$().ready(function(){
		
		//var userId = "${param.userId}"; // url의 userId
		$("#auth").val("${user.authorizationId}");
		
		$("#pointBtn").click(function(){
			// disable > able로, 버튼명 변경완료로 바꾸기
			var buttonText = $(this).val();
			
			if(buttonText == "변경"){
				$(this).val("변경완료");
				$("#point").removeAttr("disabled");
			}else if (buttonText == "변경완료"){
				// $(this).val("변경");
				// $("#point").attr("disabled", "disabled");
				
				$.post("/melon-admin/user/modify", {
					"userPoint" : $("#point").val(),
					"userId" : "${param.userId}"
				}, function(response){
					var jsonObj = JSON.parse(response);
					if(jsonObj.status == "success") { // update 성공시
						$("#pointBtn").val("변경");
						$("#point").attr("disabled", "disabled");
					}
				});
			}
		});
		
		$("#passwordBtn").click(function(){
			// disable > able로, 버튼명 변경완료로 바꾸기
			var buttonText = $(this).val();
			if(buttonText == "변경"){
				$(this).val("변경완료");
				$("#password").removeAttr("disabled");
			}else if (buttonText == "변경완료"){
				$.post("/melon-admin/user/modify", {
					"userPassword" : $("#password").val(),
					"userId" : "${param.userId}"
				}, function(response){
					var jsonObj = JSON.parse(response);
					if(jsonObj.status == "success") { // update 성공시
						$("#passwordBtn").val("변경");
						$("#password").attr("disabled", "disabled");
					}
				});
			}
		});
		
		$("#authBtn").click(function(){
			// disable > able로, 버튼명 변경완료로 바꾸기
			var buttonText = $(this).val();
			if(buttonText == "변경"){
				$(this).val("변경완료");
				$("#auth").removeAttr("disabled");
			}else if (buttonText == "변경완료"){
				$.post("/melon-admin/user/modify", {
					"authorizationId" : $("#auth").val(),
					"userId" : "${param.userId}"
				}, function(response){
					var jsonObj = JSON.parse(response);
					if(jsonObj.status == "success") { // update 성공시
						$("#authBtn").val("변경");
						$("#auth").attr("disabled", "disabled");
					}
				});
				
			}
		});
		
	});
</script>
</head>
<body>
		${user.userId }<br/>
		${user.userName }<br/>
		
		<form id="modifyForm">
			<input type="text" id="point" disabled="disabled" value="${user.userPoint }" />
			<input type="button" id="pointBtn" value="변경" />
			<br/>
			<input type="password" id="password" disabled="disabled" />
			<input type="button" id="passwordBtn" value="변경" />
			<br/>
			<select id="auth" disabled="disabled">
				<c:forEach items="${authList}" var="auth">
					<option value="${auth.authorizationId}">${auth.authorizationName }</option>
				</c:forEach>
			</select>
			<input type="button" id="authBtn" value="변경" />
		</form>	
</body>
</html>