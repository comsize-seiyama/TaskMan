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
	TaskBean editTaskBean = (TaskBean)session.getAttribute("taskBean");
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
			<td><%=editTaskBean.getLimitDate()%></td>
		</tr>
		<tr>
			<td>担当者</td>
			<td><%=%></td>
		</tr>
		<tr>
			<td>ステータス</td>
			<td><%=%></td>
		</tr>
		<tr>
			<td>メモ</td>
			<td><%=%></td>
		</tr>
	</table>
	<form action="menu.jsp" method="POST"></form>
</body>
</html>