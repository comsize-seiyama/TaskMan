package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.TaskDAO;
import model.entity.TaskBean;

/**
 * Servlet implementation class TaskDeleteServlet
 */
@WebServlet("/task-delete-servlet")
public class TaskDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaskDeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String taskIdstr = request.getParameter("taskId");

		if (taskIdstr == null) {
			request.setAttribute("message", "タスクを選択してください。");

			RequestDispatcher rd = request.getRequestDispatcher("task-list.jsp");
			rd.forward(request, response);
			return;
		}

		try {
			int taskId = Integer.parseInt(taskIdstr);

			// タスク情報取得
			TaskDAO taskDao = new TaskDAO();
			TaskBean taskBean = taskDao.selectById(taskId);

			// リクエストスコープへ格納
			request.setAttribute("taskBean", taskBean);
			// 削除確認画面へ
			RequestDispatcher rd = request.getRequestDispatcher("task-delete-confirmation.jsp");
			rd.forward(request, response);

		}

		catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();

			request.setAttribute("message", "システムエラーが発生しました。"
					+ "しばらくしてから再度お試し下さい。");
			RequestDispatcher rd =
					request.getRequestDispatcher("task-delete-failed.jsp");
			rd.forward(request, response);
		}
	}
	
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

}
