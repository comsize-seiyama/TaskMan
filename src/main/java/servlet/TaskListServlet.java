package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.TaskDAO;
import model.entity.TaskBean;

@WebServlet("/task-list-servlet")
public class TaskListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TaskListServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        // ログインチェック
        if (session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<TaskBean> taskBeanList = new ArrayList<>();

        try {
        	//TaskDAOをインスタンス化
            TaskDAO taskDao = new TaskDAO();
            //タスク情報をリストに代入
            taskBeanList = taskDao.selectAll();
            //タスクが存在するかチェック
            if (taskBeanList == null || taskBeanList.isEmpty()) {
                request.setAttribute("message", "登録されているタスクはありません。");
            }
           //DB,nullエラーチェック
        } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("message", "システムエラーが発生しました。");
        }
          //一覧表示に使用するリストをセッションにセット
        session.setAttribute("taskBeanList", taskBeanList);
          //一覧表示画面へフォワード
        RequestDispatcher rd = request.getRequestDispatcher("task-list.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}