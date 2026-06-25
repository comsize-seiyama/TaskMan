package servlet;

import java.io.IOException;
import java.sql.SQLException;

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

		HttpSession session = request.getSession();

		// ログインチェック
		String loginUserId = (String) session.getAttribute("userId");

		if (loginUserId == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		// タスク未選択チェック
		String taskIdStr = request.getParameter("taskId");

		if (taskIdStr == null || taskIdStr.isEmpty()) {
			request.setAttribute("message", "タスクを選択してください。");

			RequestDispatcher rd = request.getRequestDispatcher("task-list-servlet");
			rd.forward(request, response);
			return;
		}

		try {
			int taskId = Integer.parseInt(taskIdStr);

			TaskDAO taskDao = new TaskDAO();

			// タスク情報取得
			TaskBean taskBean = taskDao.selectById(taskId);

			// タスク存在チェック
			if (taskBean == null) {
				request.setAttribute("message", "選択されたタスクは存在しません。");

				RequestDispatcher rd = request.getRequestDispatcher("task-list.jsp");
				rd.forward(request, response);
				return;
			}

			// 本人チェック
			if (!loginUserId.equals(taskBean.getUserId())) {
				request.setAttribute("message", "自分のタスクのみ削除できます。");

				RequestDispatcher rd = request.getRequestDispatcher("task-list.jsp");
				rd.forward(request, response);
				return;
			}

			// リクエストスコープへ格納
			request.setAttribute("taskBean", taskBean);

			// 削除確認画面へ
			RequestDispatcher rd = request.getRequestDispatcher("task-delete-confirmation.jsp");
			rd.forward(request, response);

		} catch (SQLException | ClassNotFoundException | NumberFormatException e) {
			e.printStackTrace();

			request.setAttribute("message",
					"システムエラーが発生しました。しばらくしてから再度お試し下さい。");

			RequestDispatcher rd = request.getRequestDispatcher("task-delete-failure.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		// ログインチェック
		String loginUserId = (String) session.getAttribute("userId");

		if (loginUserId == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		// タスク未選択チェック
		String taskIdStr = request.getParameter("taskId");

		if (taskIdStr == null || taskIdStr.isEmpty()) {
			request.setAttribute("message", "タスクを選択してください。");

			RequestDispatcher rd = request.getRequestDispatcher("task-delete-failure.jsp");
			rd.forward(request, response);
			return;
		}

		int taskId = Integer.parseInt(taskIdStr);

		TaskDAO taskDao = new TaskDAO();

		try {

			// 削除前にタスク情報取得
			TaskBean deleteTask = taskDao.selectById(taskId);

			// タスク存在チェック
			if (deleteTask == null) {
				request.setAttribute("message", "選択されたタスクは存在しません。");

				RequestDispatcher rd = request.getRequestDispatcher("task-delete-failure.jsp");
				rd.forward(request, response);
				return;
			}
			// 本人チェック
			if (!loginUserId.equals(deleteTask.getUserId())) {
				request.setAttribute("message", "自分のタスクのみ削除できます。");

				RequestDispatcher rd = request.getRequestDispatcher("task-delete-failure.jsp");
				rd.forward(request, response);
				return;
			}

			// 削除実行
			int count = taskDao.deleteTask(taskId);

			if (count == 1) {

				request.setAttribute("deleteTask", deleteTask);

				RequestDispatcher rd = request.getRequestDispatcher("task-delete-success.jsp");
				rd.forward(request, response);

				return;
			}else {

			request.setAttribute("message",
					"削除対象が存在しません。");

			RequestDispatcher rd = request.getRequestDispatcher("task-delete-failure.jsp");
			rd.forward(request, response);
			}
		} catch (SQLException | ClassNotFoundException |NumberFormatException e) {

			e.printStackTrace();

			request.setAttribute("message",
					"システムエラーが発生しました。しばらくしてから再度お試し下さい。");

			RequestDispatcher rd = request.getRequestDispatcher("task-delete-failure.jsp");
			rd.forward(request, response);
		}
	}
}
