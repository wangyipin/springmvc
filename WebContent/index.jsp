<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>首页</title>
</head>

<%

	

%>

<body>


<p>this is index file</p>



<form action="http://localhost:8088/springmvc/LoginAction.do?method=doLogin" method="post">
	username：<input name="username" type="text"/>
	password：<input name="pwd" type="password"/>
	<input type="submit" value="login"/>
</form>

<a href="register.jsp" style="font-size:12px;">没有账户?点击注册</a>

</body>
</html>