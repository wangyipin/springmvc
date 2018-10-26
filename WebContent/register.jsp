<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>注册用户</title>
<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
</head>

<script type="text/javascript">

function doSubmit(){
	var username=$("#username").val();
	var pwd=$("#pwd").val();
	$.ajax({
		 url:'<%=path%>/UserAction.do?method=doRegisterAjax',
		 method:'post',
		 data: {'username':username,'pwd':pwd},
		 success:function(responseText){
			   responseText = eval("("+responseText+")")
			   if("01" == responseText.retCode){
				   alert(responseText.errMsg);
			   }
			   else{
				   alert(responseText.responseData);
			   }
			 }
		 })
}

	

</script>
<body>


<form action="http://localhost:8088/springmvc/UserAction.do?method=doRegister" method="post">
	用户姓名：<input type="text" name="username" id="username"/>
	密码：<input type="text" name="pwd" id="pwd"/>
	<input type="submit" value="注册"/>
	<input type="reset" value="重置"/>
</form>

<input type="button" value="ajax提交" onclick="doSubmit()"/>


</body>
</html>