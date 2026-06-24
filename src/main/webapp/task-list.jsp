<%@page import="java.util.List"%>
<%@page import="model.entity.TaskBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク一覧表示</title>
</head>
<body>

<h1>タスク一覧表示</h1>

<%

String loginUserId =(String)session.getAttribute("userId");
%>
<%-- エラーメッセージを表示 --%>
<%
String message = (String) request.getAttribute("message");

if (message != null) {
%>
    <p style="color:red;"><%= message %></p>
<%
}
%>
<form method="GET">

    <input type="submit"
           value="編集"
           formaction="task-edit-servlet"
           style="display:inline;">

    <input type="submit"
           value="削除"
           formaction="task-delete-servlet"
           style="display:inline;">

    <input type="submit"
           value="メニューへ"
           formaction="menu.jsp"
           style="display:inline;">

    <table border="1">

        <tr>
            <th>選択</th>
            <th>タスク名</th>
            <th>カテゴリ情報</th>
            <th>期限</th>
            <th>担当者情報</th>
            <th>ステータス情報</th>
            <th>メモ</th>
        </tr>

        <%
        List<TaskBean> taskBeanList =
            (List<TaskBean>)session.getAttribute("taskBeanList");

        for(TaskBean taskBean : taskBeanList){
        %>

        <tr>

            <td>

                <%
                
                if(loginUserId.equals(taskBean.getUserId())){
                %>
                <input type="radio" name="taskId"
                           value="<%=taskBean.getTaskId()%>">

                <%
                }else{
                %>

                    -

                <%
                }
                %>

            </td>

            <td><%=taskBean.getTaskName()%></td>

            <td><%=taskBean.getCategoryName()%></td>

            <td>
                 <%--期限項目のnullチェック --%>
                <%= taskBean.getLimitDate() == null ? "" : taskBean.getLimitDate() %>

            </td>

            <td><%=taskBean.getUserName()%></td>

            <td><%=taskBean.getStatusName()%></td>

            <td>
                <%=taskBean.getMemo() %>
            </td>

        </tr>

        <%
        }
        %>

    </table>

</form>

</body>
</html>