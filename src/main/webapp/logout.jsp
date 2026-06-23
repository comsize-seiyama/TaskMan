<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.entity.UserBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログアウト画面</title>
</head>
<body>
<%
UserBean loginUser =(UserBean)session.getAttribute("loginUser");
session.invalidate();
%>
<h1>ログアウト画面</h1>
<hr>
<h2>お疲れ様でした！<%=loginUser.getUserName()%>さん！</h2>

<h2>ログアウトしました。</h2>
<form action="login.jsp"method="POST">
<input type="submit" value="ログイン画面へ">
</form>
</body>
</html>