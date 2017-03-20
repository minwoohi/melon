<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/melon-admin/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	$().ready(function(){
		
		// 1. 권한 목록 가져오기
		$.post("/melon-admin/authorization/all", {}, function(response){
			// JSON format String을 JavaScript Object로 변환
			var auth = JSON.parse(response);
			var authList = auth.authorizations;
			
			
			var authorizationDiv = $("#authorization"); // authorization Div
			var parentAuthorizaztionId = $("#parentAuthorizationId"); // select Tag
			
			for(var i in authList){
				console.log(authList[i].authorizationName);
				
				/*
				인증 : Authentication, 인증에 필요한 데이터 : Credential
				인가 : Authorization
				*/
				
				var eachAuth = $("<div id='" + authList[i].authorizationId + "'></div>");
				// div의 id를 authorizationId로 해 태그 생성
				eachAuth.text(authList[i].authorizationName); // 값 설정
				eachAuth.data("parent", authList[i].parentAuthorizationId); // key, value로 설정
				authorizationDiv.append(eachAuth); // 붙임
				// Shadow DOM 추가하는 패턴
				
				var itemAuth = $("<option value='" + authList[i].authorizationId + "'>" + authList[i].authorizationName + "</option>");
				parentAuthorizaztionId.append(itemAuth);
				// select 태그에 설정한 Option을 붙인다.
			}
		});
		
		// DOM : $("#authorization").find("div").click(function(){});
		$("#authorization").on("click", "div", function(){
			//alert( $(this).attr("id") );
			//$(this).text();
			
			$("#authorizationId").val($(this).attr("id"));
			$("#authorizationName").val( $(this).text() );
			$("#parentAuthorizationId").val( $(this).data("parent") );
			
			$("#modifyBtn").remove();
			$("#deleteBtn").remove();
			
			var modifyBtn = $("<input type='button' id='modifyBtn' value='수정' />");
			var deleteBtn = $("<input type='button' id='deleteBtn' value='삭제' />");
			
			$("#registBtn").after(deleteBtn);
			$("#registBtn").after(modifyBtn);
			
		});
		
		$("#registForm").on("click", "#modifyBtn", function(){
			$.post("/melon-admin/authorization/update", {
				"authorizationId" : $("#authorizationId").val(),
				"authorizationName" : $("#authorizationName").val(),
				"parentAuthorizationId" : $("#parentAuthorizationId").val()
			}, function(response){
				var jsonObj = JSON.parse(response);
				if(jsonObj.status == "success") {
					var modifiedAuth = $("#authorizationId").val();
					$("#" + modifiedAuth).text($("#authorizationName").val());
					$("#" + modifiedAuth).data("parent", $("#parentAuthorizationId").val() );
					
					$("#parentAuthorizationId")
					.find("option[value=" + modifiedAuth + "]")
					.text($("#authorizationName").val());
					
					$("#modifyBtn").remove();
					$("#deleteBtn").remove();
					$("#authorizationId").val("");
					$("#authorizationName").val("");
					$("#parentAuthorizationId").val("");
				}
			});
		});
		
		$("#registForm").on("click", "#deleteBtn", function(){
			
			$.post("/melon-admin/authorization/delete", {
				"authorizationId" : $("#authorizationId").val()
			}, function(response){
				var jsonObj = JSON.parse(response);
				if( jsonObj.status == "success" ) {
					var deletedAuth = $("#authorizationId").val();
					$("#" + deletedAuth).remove();
					
					$("#parentAuthorizationId").find("option[value=" + deletedAuth + "]").remove();
					
					$("#modifyBtn").remove();
					$("#deleteBtn").remove();
					
					// 입력값 초기화
					$("#authorizationId").val("");
					$("#authorizationName").val("");
					$("#parentAuthorizationId").val("");
				}
			});
		});
		
		$("#registBtn").click(function(){
			$.post("/melon-admin/authorization/regist", {
				"authorizationName" : $("#authorizationName").val(),
				"parentAuthorizationId" : $("#parentAuthorizationId").val()
			}, function(response){
				
				var auth = JSON.parse(response);
				var authInfo = auth.authorization;
				
				//console.log(authorization);
				
				var authorizationDiv = $("#authorization"); // authorization Div
				var parentAuthorizaztionId = $("#parentAuthorizationId"); // select Tag
				
				var eachAuth = $("<div id='" + authInfo.authorizationId + "'></div>");
				// div의 id를 authorizationId로 해 태그 생성
				eachAuth.text(authInfo.authorizationName); // 값 설정
				eachAuth.data("parent", authInfo.parentAuthorizationId); // key, value로 설정
				
				authorizationDiv.append(eachAuth); // 붙임
				// Shadow DOM 추가하는 패턴
				
				var itemAuth = $("<option value='" + authInfo.authorizationId + "'>" + authInfo.authorizationName + "</option>");
				// 
				parentAuthorizaztionId.append(itemAuth);
			});
		});
		
	});
	
</script>
</head>
<body>
	<div id="regist">
		<form id="registForm">
			<input type="hidden" id="authorizationId" />
			<span>권한 명</span><br/>
			<input type="text" id="authorizationName" /><br/>
			<br/>
			<span>상위 권한</span>
			<select id="parentAuthorizationId"></select> <br/>
			<br/>
			<input type="button" id="registBtn" value="저장" />
		</form>
	</div>
	<div id="authorization">
	</div>
</body>
</html>