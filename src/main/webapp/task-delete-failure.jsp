<%@page import="model.entity.TaskBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク削除失敗</title>
</head>
<body>
    <h2>タスク削除失敗</h2>
<%
String message = (String) request.getAttribute("message");

if (message != null) {
%>
    <p style="color:red;"><%= message %></p>
<%
}
%>

    <p>次のタスクの削除に失敗しました。</p>
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
         <%--期限項目のnullチェック --%>
         <%= taskBean.getLimitDate() == null ? "" : taskBean.getLimitDate() %>
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

    <form action="menu.jsp" method="get">
        <input type="submit" value="メニューへ">
    </form>
</body>
</html>