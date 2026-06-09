<%@page import="model.entity.TaskBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.List, model.entity.CategoryBean,
	model.entity.UserBean,model.entity.StatusBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク編集フォーム</title>
</head>
<body>
	<%List<TaskBean> taskBeanList = (List<TaskBean>)session.getAttribute("taskBeanList"); %>
	<h1>タスク編集フォーム画面</h1>
	<hr>
	<form action="task-edit-servlet" method="POST">
		<table border="1">
			<tr>
				<td>タスク名</td>
				<td><input type="text" name="price" required>円</td>
			</tr>
			<tr>
				<td>カテゴリ名</td>
				<td><select name="categoryCode">
						<%
						for (CategoryBean CategoryBean : CategoryBeanList) {
						%>

						<option value="<%=CategoryBean.getCategoryId()%>">
							<%=CategoryBean.getCategoryId()%>
						</option>
						<%
						}
						%>
				</select></td>
			</tr>
			<tr>
				<td>期限</td>
				<td><input type="text" name="price" required>円</td>
			</tr>
			<tr>
				<td>担当者</td>
				<td><select name="categoryCode">
						<%
						for (UserBean CategoryBean : UserBeanList) {
						%>

						<option value="<%=UserBean.getUserId()%>">
							<%=UserBean.getUserName()%>
						</option>
						<%
						}
						%>
				</select></td>
			</tr>
			<tr>
				<td>ステータス</td>
				<td><select name="categoryCode">
						<%
						for (StatusBean CategoryBean : StatusBeanList) {
						%>

						<option value="<%=StatusBean.getStatusCode()%>">
							<%=StatusBean.getStatusName()%>
						</option>
						<%
						}
						%>
				</select></td>
			</tr>
			<tr>
				<td>メモ</td>
				<td><input type="text" name="price" required>円</td>
			</tr>
		</table>
		<input type="submit" value="登録"> 
		<input type="reset" value="クリア"> <br>
	</form>
	<br>
	<form action="menu.jsp" method="POST">
		<input type="submit" value="メニュー画面へ">
	</form>

</body>
</html>