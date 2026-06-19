<%@page import="model.entity.TaskBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>削除確認画面</title>
</head>
<body>
<h1>削除確認画面</h1>
次のタスクを削除します。よろしいですか？
<% TaskBean taskBean = (TaskBean)request.getAttribute("taskBean");%>

<table border="1">
 <tr>
        <th>タスク名</th>
        <td><%=taskBean.getTaskName()%></td>
    </tr>
      <tr>
        <th>カテゴリ情報</th>
        <td><%=taskBean.getCategoryName()%></td>
    </tr>

    <tr>
        <th>期限</th>
        <td><%=taskBean.getLimitDate()%></td>
    </tr>

    <tr>
        <th>担当者情報</th>
        <td><%=taskBean.getUserName()%></td>
    </tr>

    <tr>
        <th>ステータス情報</th>
        <td><%=taskBean.getStatusName()%></td>
    </tr>

    <tr>
        <th>メモ</th>
        <td><%=taskBean.getMemo()%></td>
    </tr>
</table>

<br>

<form action="TaskDeleteCompleteServlet" method="post">

    <input type="hidden"
           name="taskId"
           value="<%=taskBean.getTaskId()%>">

    <input type="submit"
           value="削除">

</form>

<form action="task-list-servlet" method="get">

    <input type="submit"
           value="一覧表示へ">
    


</form>
</body>
</html>