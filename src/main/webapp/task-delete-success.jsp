
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.entity.TaskBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
TaskBean deleteTask =
    (TaskBean) request.getAttribute("deleteTask");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク削除完了画面</title>
</head>
<body>

<h1>タスク削除完了画面</h1>

<p>以下のタスクを削除しました。</p>

<table border="1">
    <tr>
        <th>タスク名</th>
        <td><%= deleteTask.getTaskName() %></td>
    </tr>
    <tr>
        <th>カテゴリ情報</th>
        <td><%= deleteTask.getCategoryName() %></td>
    </tr>
    <tr>
        <th>期限</th>
        <td><%= deleteTask.getLimitDate() %></td>
    </tr>
    <tr>
        <th>担当者情報</th>
        <td><%= deleteTask.getUserName() %></td>
    </tr>
    <tr>
        <th>ステータス情報</th>
        <td><%= deleteTask.getStatusName() %></td>
    </tr>
    <tr>
        <th>メモ</th>
        <td><%= deleteTask.getMemo() %></td>
    </tr>
</table>

<br>

<form action="task-list-servlet" method="get">
    <input type="submit" value="一覧表示へ">
</form>

<form action="menu.jsp" method="get">
    <input type="submit" value="メニュー画面へ">
</form>

</body>
</html>