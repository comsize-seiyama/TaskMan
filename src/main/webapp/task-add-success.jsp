<%@page import="model.entity.TaskBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<% TaskBean resultBean = (TaskBean)request.getAttribute("inputBean"); %>

<head>
<meta charset="UTF-8">
<title>タスク登録失敗画面</title>
</head>
<body>
<h1>タスク登録画面</h1>
<h2>次のタスクを登録しました。</h2>

<table border="1">
      <tr>
        <th>タスク名</th>
        <td>
          <%= resultBean.getTaskName() %>
        </td>
      </tr>
      <tr>
        <th>カテゴリ名</th>
        <td>
         <%= resultBean.getCategoryName() %>
        </td>
      </tr>
      <tr>
        <th>期限</th>
        <td>
         <%= resultBean.getLimitDate() == null ? "" : resultBean.getLimitDate() %>
        </td>
      </tr>
      <tr>
        <th>担当者</th>
        <td>
         <%= resultBean.getUserName() %>
        </td>
      </tr>
      <tr>
        <th>ステータス名</th>
        <td>
         <%= resultBean.getStatusName() %>
        </td>
      </tr>
      <tr>
        <th>メモ</th>
        <td>
         <%= resultBean.getMemo() %>
        </td>
      </tr>
      
	</table>
	<form action="menu.jsp" method="POST">
	<input type="submit" value ="メニュー画面へ">
	</form>
</body>

</html>