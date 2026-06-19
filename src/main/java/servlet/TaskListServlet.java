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

/**
 * Servlet implementation class TaskListServlet
 */
@WebServlet("/task-list-servlet")
public class TaskListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		

	    //タスク一覧格納用
        List<TaskBean> taskBeanList = new ArrayList<>();
        
        try {
        	//タスク一覧取得
            TaskDAO taskDao = new TaskDAO();
            taskBeanList = taskDao.selectAll();
        
        if (taskBeanList == null || taskBeanList.isEmpty()) {
            request.setAttribute("message", "登録されているタスクはありません。");
        }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        
        HttpSession session = request.getSession();
        //セッションスコープへ格納
        session.setAttribute("taskBeanList", taskBeanList);
        
        //一覧画面へ遷移
        RequestDispatcher rd = request.getRequestDispatcher("task-list.jsp");
        rd.forward(request, response);
}}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ログインチェック
				HttpSession session = request.getSession();
				if (session.getAttribute("userId") == null) {
				response.sendRedirect("login.jsp");
				return;
				}
		
		
		
}
}

