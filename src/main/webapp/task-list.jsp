<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク一覧表示</title>
</head>
<body>
	<h1>タスク一覧表示</h1>
	<form action="task-edit-Servlet" method="post">
	<input type="submit" name="action" value="編集" style="display: inline;">
	<input type="submit"  name="action" value="編集" style="display: inline;">

	<table border="1">
		<tr>
			<th>選択</th>
			<th>タスク名</th>
			<th>カテゴリ情報</th>
			<th>期限</th>
			<th>担当者情報</th>
			<th>ステータス情報</th>
			<th>メモ</th>

		</tr>
		<%
		List<TaskBean> taskBeanList = (List<TaskBean>) session.getAttribute("taskBeanList");
		for (TaskBean taskbeanlist : taskBeanList) {
		%>
		<tr>
			<td><input type="radio" name="taskId"
				value="<%=taskbeanlist.getTaskId()%>"></td>
			<td><%=taskbeanlist.getTaskName()%></td>
			<td><%=taskbeanlist.getCategoryName()%></td>
			<td><%=taskbeanlist.getLimitDate()%></td>
			<td><%=taskbeanlist.getUserName()%></td>
			<td><%=taskbeanlist.getStatusName()%></td>
			<td><%=taskbeanlist.getMemo()%></td>

		</tr>
		<%
		}
		%>



	</table>
	</form>
</body>
</html>