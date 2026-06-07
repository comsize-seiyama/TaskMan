<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String userName = (String) session.getAttribute("userName");
if (userName == null) {
	request.getRequestDispatcher("login.jsp").forward(request, response);
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー画面</title>
</head>
<body>
	<h1>メニュー画面</h1>
	<hr>
	ようこそ！

	<%=userName%>さん
	<br>

	<form action="itemListServlet" method="POST">
		<input type="submit" value="タスク登録">
	</form>
	<br>

	<form action="itemAddServlet" method="GET">
		<input type="submit" value="タスク一覧表示">
	</form>
	<br>
	
	<form action="login.jsp" method="POST">
		<input type="submit" value="ログアウト">
	</form>


</body>
</html>