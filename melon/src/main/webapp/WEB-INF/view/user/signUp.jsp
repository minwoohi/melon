<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>/web-inf/view/user/signUp.jsp</title>
<script type="text/javascript" src="/melon/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="melon/static/js/json2.js"></script>
<!-- json 코드에 객체 없으면 생성하도록 되어 있다. JSON 객체 포함하고 있는 브라우저에서는 src 포함하지 않아도 됨 -->
<script type="text/javascript">
	$().ready(function(){
		$("#signUpForm").find("input[type=button]").click(function(){
			// 필수 입력 값 체크
			if($("#userId").val() == "" ){
				alert("아이디를 입력해 제발");
				$("#userId").focus();
				return; // 종료
			}
			
			if($("#userPassword").val() == "" ){
				alert("비밀번호를 입력해 제발");
				$("#userPassword").focus();
				return; // 종료
			}
			
			if($("#userName").val() == "" ){
				alert("별명을 입력해 제발");
				$("#userName").focus();
				return; // 종료
			}
			
			if($("#userPassword").val().length < 7){ // 자바스크립트에서 length는 속성에 해당.
				alert("비밀번호는 8자 이상 입력해야 한다.");
				$("#userPassword").focus();
				return;
			}
			
			$.post("/melon/user/checkDuplicate", {
				"userId" : $("#userId").val()
			}, function(response){
					
				var jsonObj = JSON.parse(response);
				
				if(jsonObj.duplicated) {
					alert("입력한 ID는 사용중이란다.\n다른 ID를 입력해봐");
					return;
				}
				else{
					$("#signUpForm").attr({
						"method" : "post",
						"action" : "/melon/user/signUp"
					});
					$("#signUpForm").submit();
				}
			});
		});
		
		$("#userId").keyup(function(){
			$.post("/melon/user/checkDuplicate",
					{
						"userId" : $("#userId").val()
					},
					function(response){
						var jsonObj = JSON.parse(response);
						console.log(jsonObj);
						
						if(jsonObj.duplicated) {
							$("#duplicated").text("중복되는 아이디란다.");
						}else{
							$("#duplicated").text("사용할 수 있는 아이디이다.");
						}
					});
		});
	});
</script>
</head>
<body>
	<c:if test="${not empty param.errorCode }" > <!--  에러코드가 있는 경우 -->
		<div>
			<c:choose>
				<c:when test="${param.errorCode == 0 }">
					ID는 필수 입력사항입니다.
				</c:when>
				<c:when test="${param.errorCode == 1 }">
					PASSWORD는 필수 입력사항입니다.
				</c:when>
				<c:when test="${param.errorCode == 2 }">
					별명은 필수 입력사항입니다.
				</c:when>
				<c:when test="${param.errorCode == 3 }">
					이미 사용중인 ID 입니다.
				</c:when>
				<c:otherwise>
					????
				</c:otherwise>
			</c:choose>
		</div>
	</c:if>
	<form id="signUpForm">
		<input type="text" name="userId" id="userId" placeholder="ID 입력하거라" />
		<span id="duplicated"></span> <!-- 키업이벤트 발생시마다 보여줌 --><br/>
		<input type="password" name="userPassword" id="userPassword" placeholder="비밀번호 입력하거라" /><br/>
		<input type="text" name="userName" id="userName" placeholder="별명을 입력하거라" /><br/>
		<input type="button" value="submit" /><br/>
	</form>
</body>
</html>