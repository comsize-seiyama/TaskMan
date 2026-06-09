<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
</head>
<body>
<h1>ログイン画面</h1>
<hr>


<form action="login-servlet" method="POST">
ユーザID<br>
<input type="text" name="userId"required><br>
パスワード<br>
<input type="password" name="password" required><br><br>
<input type="submit" value="ログイン">
</form>
<%
String errorMessage = (String) request.getAttribute("errorMessage");
if (errorMessage != null) {
%>
    <p style="color:red;"><%= errorMessage %></p>
<%
}
%>

</body>
</html>