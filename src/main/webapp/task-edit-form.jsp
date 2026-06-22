<%@page import="model.entity.TaskBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.List,java.time.LocalDate,model.entity.CategoryBean,
	model.entity.UserBean,model.entity.StatusBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク編集フォーム</title>
</head>
<body>
	<%
	TaskBean taskBean = (TaskBean)session.getAttribute("taskBean");
	List<CategoryBean> categoryBeanList = (List<CategoryBean>)session.getAttribute("categoryBeanList");
	List<UserBean> userBeanList = (List<UserBean>)session.getAttribute("userBeanList");
	List<StatusBean> statusBeanList = (List<StatusBean>)session.getAttribute("statusBeanList");
	%>
	<h1>タスク編集フォーム画面</h1>
	<hr>
	<p style="red">*</p>がついている欄は必須入力です
	<%-- エラーメッセージを表示 --%>
<%
String message = (String) request.getAttribute("message");

if (message != null) {
%>
    <p style="color:red;"><%= message %></p>
<%
}
%>
	<form action="task-edit-servlet" method="POST">
		<table border="1">
			<tr>
				<td>タスク名</td>
				<td><input type="text" name="taskName" required></td>
			</tr>
			<tr>
				<td>カテゴリ名</td>
				<td><select name="categoryName">
						<%
						for (CategoryBean categoryBean : categoryBeanList) {
						%>

						<option value="<%=categoryBean.getCategoryId()%>">
							<%=categoryBean.getCategoryName()%>
						</option>
						<%
						}
						%>
				</select></td>
			</tr>
			<tr>
				<td>期限</td>
				<td><input type="date" name="date" min="<%= LocalDate.now()%>" required></td>
			</tr>
			<tr>
				<td>担当者</td>
				<td><select name="userName">
						<%
						for (UserBean userBean : userBeanList) {
						%>

						<option value="<%=userBean.getUserId()%>">
							<%=userBean.getUserName()%>
						</option>
						<%
						}
						%>
				</select></td>
			</tr>
			<tr>
				<td>ステータス</td>
				<td><select name="statusCode">
						<%
						for (StatusBean statusBean : statusBeanList) {
						%>

						<option value="<%= statusBean.getStatusCode()%>">
							<%= statusBean.getStatusName()%>
						</option>
						<%
						}
						%>
				</select></td>
			</tr>
			<tr>
				<td>メモ</td>
				<td><input type="text" name="memo" required></td>
			</tr>
		</table>
		<input type="submit" value="編集実行"> 
		<input type="reset" value="クリア"> <br>
	</form>
	<br>
	<form action="task-list-servlet" method="POST">
		<input type="submit" value="一覧表示画面へ">
	</form>

</body>
</html>