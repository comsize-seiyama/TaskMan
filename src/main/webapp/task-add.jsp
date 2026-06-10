<%@page import="model.entity.StatusBean"%>
<%@page import="model.entity.UserBean"%>
<%@page import="model.entity.CategoryBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% List<CategoryBean> categoryList = (List<CategoryBean>)request.getAttribute("categoryList"); %>
<% List<UserBean> userList = (List<UserBean>)request.getAttribute("userList"); %>
<% List<StatusBean> statusList = (List<StatusBean>)request.getAttribute("statusList"); %>

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
          <input type="text" name="taskName">
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
        	</select>>
        	</td>
      </tr>
      <tr>
        <th>期限</th>
        <td><input type="date"></td>
      </tr>
      <tr>
        <th>担当者</th>
        <td></td>
      </tr>
      <tr>
        <th>ステータス</th>
        <td></td>
      </tr>
      <tr>
        <th>メモ</th>
        <td>
          <input type="text" name="memo">
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