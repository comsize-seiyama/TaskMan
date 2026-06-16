package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.CategoryDAO;
import model.dao.StatusDAO;
import model.dao.UserDAO;
import model.entity.CategoryBean;
import model.entity.StatusBean;
import model.entity.UserBean;

/**
 * Servlet implementation class TaskAddServlet
 */
@WebServlet("/task-add-servlet")
public class TaskAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskAddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ログインチェック
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") == null) {
		response.sendRedirect("login.jsp");
		return;
		}
		
		request.setCharacterEncoding("UTF-8");
		
		//各DAOからリストを取得してsessionスコープに入れてフォワード
		
		UserDAO user = new UserDAO();
		CategoryDAO category = new CategoryDAO();
		StatusDAO status = new StatusDAO();
		
		List<UserBean> userList = new ArrayList<UserBean>();
		try {
			userList = user.getUserList();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		List<CategoryBean>categoryList = new ArrayList<CategoryBean>();
		try {
			categoryList = category.selectAll();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		List<StatusBean>statusList = new ArrayList<StatusBean>();
		try {
			statusList = status.selectAll();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		//各テーブルからリストを取得できたので、リクエストスコープに入れて画面に渡す。
		session.setAttribute("userList",userList);
		session.setAttribute("categoryList",categoryList);
		session.setAttribute("statusList",statusList);
		
		//画面にフォワードする
		request.getRequestDispatcher("task-add.jsp").forward(request, response);
		
	}

	

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
		request.setCharacterEncoding("UTF-8");
		
		//バリデーションチェック（タスク名）
		String taskName =(String)request.getParameter("taskName");
		if(taskName == null || taskName.isBlank()){
			request.setAttribute("errorMessage", "タスク名を入力してください");
			request.getRequestDispatcher("task-add.jsp").forward(request, response);
			return;
		}
		
		//バリデーションチェック（
		
	
		
		
	}
	
	

}
