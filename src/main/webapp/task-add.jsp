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
  <%
//初回はリクエストスコープから何も取得できないのでnullになる。
String errorMessage = (String) request.getAttribute("errorMessage");
if (errorMessage != null) {
%>
    <p style="color:red;"><%= errorMessage %></p>
<%
}
%>
  <form action="task-add-servlet" method="POST">
  <span style="color:red;">*</span>がついている欄は必須入力です。
    <table border="1">
      <tr>
        <td><span style="color:red;">*</span>タスク名</td>
        <td>
          <input type="text" name="taskName" maxlength="50" required>
        </td>
      </tr>
      <tr>
        <td><span style="color:red;">*</span>カテゴリ名</td>
        
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
        <td>期限</td>
        <td><input type="date" name="date" min="<%= LocalDate.now()%>" ></td>
      </tr>
      <tr>
        <td><span style="color:red;">*</span>担当者</td>
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
        <td><span style="color:red;">*</span>ステータス</td>
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
        <td>メモ</td>
        <td>
          <input type="text" name="memo" maxlength="100">
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