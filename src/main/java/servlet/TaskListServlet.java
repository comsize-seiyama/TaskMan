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

import model.dao.CategoryDAO;
import model.dao.StatusDAO;
import model.dao.TaskDAO;
import model.entity.CategoryBean;
import model.entity.StatusBean;
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
		
		    doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		

		    //タスク一覧格納用
	        List<TaskBean> taskBeanList = new ArrayList<>();
	        //カテゴリー一覧格納用
	        List<CategoryBean> categoryBeanList = new ArrayList<>();
	        //ステータス格納用
	        List<StatusBean>statusBeanList = new ArrayList<>();

	        try {
	        	//タスク一覧取得
	            TaskDAO taskDao = new TaskDAO();
	            taskBeanList = taskDao.selectAll();
                //カテゴリー一覧取得
	            CategoryDAO categoryDao = new CategoryDAO();
	            categoryBeanList = categoryDao.selectAll();
	            //ステータス一覧取得
	            StatusDAO statusDao = new StatusDAO();
	            statusBeanList = statusDao.selectAll();

	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }

	        HttpSession session = request.getSession();
	        //セッションスコープへ格納
	        session.setAttribute("taskBeanList", taskBeanList);
	        session.setAttribute("categoryBeanList", categoryBeanList);
	        session.setAttribute("statusBeanList", statusBeanList);
            //一覧画面へ遷移
	        RequestDispatcher rd = request.getRequestDispatcher("task-list.jsp");
	        rd.forward(request, response);
	}
}

