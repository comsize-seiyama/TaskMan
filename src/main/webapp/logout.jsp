<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログアウト画面</title>
</head>

<body>
<h1>ログアウト画面</h1><hr>
お疲れ様でした！
<%String userName =(String)session.getAttribute("userName");%>
<%session.invalidate(); %>
<%=userName %>さん

<h1>ログアウトしました。</h1>


<form action="login.jsp">
<input type="submit" value="ログイン画面へ">

</form>
</body>
</html>