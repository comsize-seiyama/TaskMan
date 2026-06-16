<%@page import="java.time.LocalDate"%>
<%@page import="model.entity.StatusBean"%>
<%@page import="model.entity.UserBean"%>
<%@page import="model.entity.CategoryBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% List<CategoryBean> categoryList = (List<CategoryBean>)session.getAttribute("categoryList"); %>
<% List<UserBean> userList = (List<UserBean>)session.getAttribute("userList"); %>
<% List<StatusBean> statusList = (List<StatusBean>)session.getAttribute("statusList"); %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>タスク登録画面</title>
</head>
<body>

  <h1>タスク登録画面</h1>
  <hr>

  <form action="task-add-servlet" method="POST">
    <table>
      <tr>
        <th>タスク名</th>
        <td>
          <input type="text" name="taskName"required>
        </td>
      </tr>
      <tr>
        <th>カテゴリ名</th>
        
        <td>
        	<select name ="categoryId">
        		<%
        		for(CategoryBean c: categoryList){
        		%>
        		<option value =<%=c.getCategoryId() %>><%=c.getCategoryName() %></option>
        		<% }%>
        	</select>
        	</td>
      </tr>
      <tr>
        <th>期限</th>
        <td><input type="date" name="date" min="<%= LocalDate.now()%>" required></td>
      </tr>
      <tr>
        <th>担当者</th>
        <td><select name ="userId">
        		<%
        		for(UserBean u: userList){
        		%>
        		<option value =<%=u.getUserId()%>><%=u.getUserName()%></option>
        		<% }%>
        	</select>
        	</td>
      </tr>
      <tr>
        <th>ステータス</th>
        <td>
        <select name ="statusCode">
        		<%
        		for(StatusBean b: statusList){
        		%>
        		<option value =<%=b.getStatusCode()%>><%=b.getStatusName()%></option>
        		<% }%>
        	</select>
        </td>
      </tr>
      <tr>
        <th>メモ</th>
        <td>
          <input type="text" name="memo"required>
        </td>
      </tr>
    </table>
    <br>

    <input type="submit" value="登録">
    <input type="reset" value="クリア">
    <br>
  </form>

  <form action="menu.jsp" method="post">
    <input type="submit" value="メニュー画面へ">
  </form>

</body>
</html>