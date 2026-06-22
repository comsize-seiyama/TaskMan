package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.CategoryDAO;
import model.dao.StatusDAO;
import model.dao.TaskDAO;
import model.dao.UserDAO;
import model.entity.CategoryBean;
import model.entity.StatusBean;
import model.entity.TaskBean;
import model.entity.UserBean;

/**
 * Servlet implementation class TaskEditServlet
 */
@WebServlet("/task-edit-servlet")
public class TaskEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//ログインチェック
				HttpSession session = request.getSession();
				if (session.getAttribute("userId") == null) {
					response.sendRedirect("login.jsp");
					return;
				}
		request.setCharacterEncoding("UTF-8");
		//タスクIDを取得
		int taskId = Integer.parseInt(request.getParameter("taskId"));
		
		//各DAOを取得
		TaskDAO taskDAO = new TaskDAO();
		UserDAO userDAO = new UserDAO();
		CategoryDAO categoryDAO = new CategoryDAO();
		StatusDAO statusDAO = new StatusDAO();
		
		//各Bean,BeanListを宣言
		TaskBean taskBean = null;
		List<UserBean> userBeanList = null;
		List<CategoryBean> categoryBeanList = null;
		List<StatusBean> statusBeanList = null;
		
		//タスク編集画面のプルダウン表示に必要な以下のマスタ情報を取得
		try {
			taskBean = taskDAO.selectById(taskId);
			userBeanList = userDAO.getUserList();
			categoryBeanList = categoryDAO.selectAll();
			statusBeanList = statusDAO.selectAll();
		} catch (ClassNotFoundException | SQLException e) {
			//例外フローE2のケース
			//タスク一覧表示画面に再遷移し
			//エラーメッセージ「データベース情報の取得に失敗しました。」を表示
			request.setAttribute("message", "データベース情報の取得に失敗しました。");
			request.getRequestDispatcher("task-list.jsp").forward(request, response);
			return;
		}
		
		//代替フローA6のケース
		if(taskBean == null) {
			//タスク一覧表示画面に再遷移、エラーメッセージ「選択されたタスクに編集権限がないか、存在していません」を表示
			request.setAttribute("message", "選択されたタスクに編集権限がないか、存在していません");
			request.getRequestDispatcher("task-list.jsp").forward(request, response);
			return;
		}
		UserBean loginUserBean = (UserBean)session.getAttribute("userBean");
		
		if(!loginUserBean.getUserId().equals(taskBean.getUserId())) {
			//タスク一覧表示画面を再表示し、
			//エラーメッセージ「選択されたタスクに編集権限がないか、存在していません」を表示する。
			request.setAttribute("message", "選択されたタスクに編集権限がないか、存在していません");
			request.getRequestDispatcher("task-list.jsp").forward(request, response);
			return;
		}
		//タスク情報をセッションスコープに詰める
		session.setAttribute("taskBean", taskBean);
		session.setAttribute("userBeanList", userBeanList);
		session.setAttribute("categoryBeanList", categoryBeanList);
		session.setAttribute("statusBeanList", statusBeanList);
		
		request.getRequestDispatcher("task-edit-form.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//ログインチェック
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		request.setCharacterEncoding("UTF-8");
		
		
	}

}
