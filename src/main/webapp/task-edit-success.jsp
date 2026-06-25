<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.entity.TaskBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク編集完了</title>
</head>
<body>
	<%
	TaskBean editTaskBean = (TaskBean)request.getAttribute("editTaskBean");
	%>
	<h1>タスク編集完了画面</h1>
	<hr>
	<br> 次のタスクを編集しました。
	<table border="1">
		<tr>
			<td>タスク名</td>
			<td><%=editTaskBean.getTaskName()%></td>
		</tr>
		<tr>
			<td>カテゴリ名</td>
			<td><%=editTaskBean.getCategoryName()%></td>
		</tr>
		<tr>
			<td>期限</td>
			<td><%=editTaskBean.getLimitDate()== null ? "" : editTaskBean.getLimitDate()%></td>
		</tr>
		<tr>
			<td>担当者</td>
			<td><%=editTaskBean.getUserName()%></td>
		</tr>
		<tr>
			<td>ステータス</td>
			<td><%=editTaskBean.getStatusName()%></td>
		</tr>
		<tr>
			<td>メモ</td>
			<td><%=editTaskBean.getMemo()%></td>
		</tr>
	</table>
	<form action="menu.jsp" method="POST">
	<input type="submit" value ="メニュー画面へ">
	</form>
</body>
</html>