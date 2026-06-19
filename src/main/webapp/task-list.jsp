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
<form method="post">

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

        for(TaskBean taskbeanlist : taskBeanList){
        %>

        <tr>

            <td>

                <%
                
                if(loginUserId.equals(taskbeanlist.getUserId())){
                %>
                <input type="radio"name="taskId"
                           value="<%=taskbeanlist.getTaskId()%>">

                <%
                }else{
                %>

                    -

                <%
                }
                %>

            </td>

            <td><%=taskbeanlist.getTaskName()%></td>

            <td><%=taskbeanlist.getCategoryName()%></td>

            <td>
                 <%--期限項目のnullチェック --%>
                <%
                if(taskbeanlist.getLimitDate() == null){
                %>

                    <%= "" %>

                <%
                }else{
                %>

                    <%=taskbeanlist.getLimitDate()%>

                <%
                }
                %>

            </td>

            <td><%=taskbeanlist.getUserName()%></td>

            <td><%=taskbeanlist.getStatusName()%></td>

            <td>
                <%--メモ項目のnullチェック --%>
                <%
                if(taskbeanlist.getMemo() == null){
                %>

                    <%= "" %>

                <%
                }else{
                %>

                    <%=taskbeanlist.getMemo()%>

                <%
                }
                %>

            </td>

        </tr>

        <%
        }
        %>

    </table>

</form>

</body>
</html>