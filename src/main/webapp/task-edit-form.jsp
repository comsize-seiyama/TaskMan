<%@page import="model.entity.TaskBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.List,java.time.LocalDate,model.entity.CategoryBean,
	model.entity.UserBean,model.entity.StatusBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク編集画面</title>
</head>
<body>
	<%
	TaskBean beforeTaskBean = (TaskBean)session.getAttribute("beforeTaskBean");
	List<CategoryBean> categoryList = (List<CategoryBean>)session.getAttribute("categoryList");
	List<UserBean> userList = (List<UserBean>)session.getAttribute("userList");
	List<StatusBean> statusList = (List<StatusBean>)session.getAttribute("statusList");
	%>
	<h1>タスク編集画面</h1>
	<hr>
	<span style="color: red;">*</span>がついている欄は必須入力です
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
				<td><span style="color: red;">*</span>タスク名</td>
				<td><input type="text" name="taskName" value="<%=beforeTaskBean.getTaskName()%>" required></td>
			</tr>
			<tr>
				<td><span style="color: red;">*</span>カテゴリ名</td>
				<td><select name="categoryId">
						<%
						for (CategoryBean categoryBean : categoryList) {
						%>

						<option value="<%=categoryBean.getCategoryId()%>"
						<%
						//編集前のカテゴリ情報とカテゴリマスタの情報を照らし合わせて
						//一致したコードに対応する分類名がデフォルトで入力される
						if(categoryBean.getCategoryName().equals(beforeTaskBean.getCategoryName())){
						%>
							selected 
						<%
						}
						%>>
							<%=categoryBean.getCategoryName()%>
						</option>
						<%
						}
						%>
				</select></td>
			</tr>
			<tr>
				<td>期限</td>
				<td>
				
				<input type="date" name="date" min="<%= LocalDate.now()%>"
				<%
				if(!(beforeTaskBean.getLimitDate() == null)){
				%>
				value="<%=beforeTaskBean.getLimitDate()%>"
				<%
				}
				%>
				>
				</td>
			</tr>
			<tr>
				<td><span style="color: red;">*</span>担当者</td>
				<td><select name="userId">
						<%
						for (UserBean userBean : userList) {
						%>

						<option value="<%=userBean.getUserId()%>"
						<%
						//編集前のユーザ情報とユーザマスタの情報を照らし合わせて
						//一致したコードに対応する分類名がデフォルトで入力される
						if(userBean.getUserName().equals(beforeTaskBean.getUserName())){
						%>
							selected 
						<%
						}
						%>>
							<%=userBean.getUserName()%>
						</option>
						<%
						}
						%>
				</select></td>
			</tr>
			<tr>
				<td><span style="color: red;">*</span>ステータス</td>
				<td><select name="statusCode">
						<%
						for (StatusBean statusBean : statusList) {
						%>

						<option value="<%= statusBean.getStatusCode()%>"
						<%
						//編集前のステータス情報とステータスマスタの情報を照らし合わせて
						//一致したコードに対応する分類名がデフォルトで入力される
						if(statusBean.getStatusName().equals(beforeTaskBean.getStatusName())){
						%>
							selected 
						<%
						}
						%>>
							<%=statusBean.getStatusName()%>
						</option>
						<%
						}
						%>
				</select></td>
			</tr>
			<tr>
				<td>メモ</td>
				<td><input type="text" name="memo" value="<%=beforeTaskBean.getMemo()%>" ></td>
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