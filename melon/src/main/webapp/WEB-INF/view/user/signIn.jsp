<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>/WEB-INF/view/user/signIn.jsp</title>
<script type="text/javascript" src="/melon/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	$().ready(function(){
		$("#signInForm").find("input[type=button]").click(function(){
			$("#signInForm").attr({
				"method" : "post",
				"action" : "/melon/user/signIn"
			});
			$("#signInForm").submit();
		});
		
		
		
	});
</script>
</head>
<body>
<form id="signInForm">
		<input type="text" name="userId" id="userId" placeholder="ID 입력하거라" /><br/>
		<input type="password" name="userPassword" placeholder="비밀번호 입력하거라" /><br/>
		<input type="button" value="submit" /><br/>
	</form>

</body>
</html>