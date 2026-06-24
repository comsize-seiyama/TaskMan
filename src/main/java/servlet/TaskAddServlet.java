package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
import model.dao.TaskDAO;
import model.dao.UserDAO;
import model.entity.CategoryBean;
import model.entity.StatusBean;
import model.entity.TaskBean;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ログインチェック
		HttpSession session = request.getSession();
		UserBean loginUser =(UserBean)session.getAttribute("loginUser");
		if(loginUser == null ) {
			response.sendRedirect("login.jsp");
		}

		request.setCharacterEncoding("UTF-8");

		//各DAOからリストを取得してsessionスコープに入れてフォワード

		UserDAO user = new UserDAO();
		CategoryDAO category = new CategoryDAO();
		StatusDAO status = new StatusDAO();

		List<UserBean> userList = new ArrayList<UserBean>();
		List<CategoryBean> categoryList = new ArrayList<CategoryBean>();
		List<StatusBean> statusList = new ArrayList<StatusBean>();
		try {
			userList = user.getUserList();
			categoryList = category.selectAll();
			statusList = status.selectAll();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		//各テーブルからリストを取得できたので、リクエストスコープに入れて画面に渡す。
		session.setAttribute("userList", userList);
		session.setAttribute("categoryList", categoryList);
		session.setAttribute("statusList", statusList);

		//画面にフォワードする
		request.getRequestDispatcher("task-add.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		//ログインチェック
		HttpSession session = request.getSession();
		UserBean loginUser =(UserBean)session.getAttribute("loginUser");
		if(loginUser == null ) {
			response.sendRedirect("login.jsp");
			return;
		}

		//不正なアクセスによってdoPostが呼び出されたときにリストがnullになるのでNPE回避用

		List<CategoryBean> categoryList = (List<CategoryBean>) session.getAttribute("categoryList");
		List<UserBean> userList = (List<UserBean>) session.getAttribute("userList");
		List<StatusBean> statusList = (List<StatusBean>) session.getAttribute("statusList");

		if (categoryList == null || userList == null || statusList == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		
		//バリデーションチェック①（タスク名）

		//空欄および空文字が入力されている場合のチェック
		String taskNameParam = request.getParameter("taskName");
		if (taskNameParam == null || taskNameParam.isBlank()) {
			forwardInputError(request, response, "タスク名を入力してください");
			return;
		}
		//５０文字超過チェック
		if (taskNameParam.length() > 50) {
			forwardInputError(request, response, "タスク名は５０文字以内で入力してください。");
			return;
		}

		//バリデーションチェック②（カテゴリ名）

		int categoryIdParam = 0
				;
		try {
			categoryIdParam = Integer.parseInt(request.getParameter("categoryId"));
		} catch (NumberFormatException e) {//nullチェック
			forwardInputError(request, response, "カテゴリ名はプルダウンから選択してください。");
			return;
		}
		boolean categoryIdCheck = false;
		//プルダウン用のリスト内のカテゴリID以外の入力を検知する。
		for (CategoryBean c : categoryList) {
			if (categoryIdParam == c.getCategoryId()) {
				categoryIdCheck = true;
				break;
			}
		}
		if (!categoryIdCheck) {
			forwardInputError(request, response, "カテゴリ名はプルダウンから選択してください。");
			return;
		}
		
		// バリデーションチェック③（期限）

		String dateParam = request.getParameter("date");

		// 未入力の場合はnullのまま登録する
		LocalDate limitDate = null;

		// 日付が入力されている場合のみチェックする
		if (dateParam != null && !dateParam.isBlank()) {

		    // yyyy-MM-dd形式の文字列をLocalDateへ変換する
		    try {
		        limitDate = LocalDate.parse(dateParam);
		    } catch (DateTimeParseException e) {
		        forwardInputError(request,response,"日付はカレンダーから指定してください。");
		        return;
		    }

		    // 過去日チェック
		    if (limitDate.isBefore(LocalDate.now())) {
		        forwardInputError(request,response,"期限に過去日付は指定できません。");
		        return;
		    }
		}

		//バリデーションチェック④（担当者チェック）
		String userIdParam = request.getParameter("userId");
		if (userIdParam == null || userIdParam.isBlank()) {
			forwardInputError(request, response, "担当者名はプルダウンから選択してください。");
			return;
		}
		boolean userIdCheck = false;
		//プルダウン用のリスト内のユーザID以外の入力を検知する。
		for (UserBean u : userList) {
			if (userIdParam.equals(u.getUserId())) {
				userIdCheck = true;
				break;
			}
		}
		if (!userIdCheck) {
			forwardInputError(request, response, "担当者名はプルダウンから選択してください。");
			return;
		}

		//バリデーションチェック⑤（ステータスチェック）
		String statusCodeParam = request.getParameter("statusCode");
		if (statusCodeParam == null || statusCodeParam.isBlank()) {
			forwardInputError(request, response, "ステータスはプルダウンから選択してください。");
			return;
		}
		boolean statusCodeCheck = false;
		//プルダウン用のリスト内のカテゴリID以外の入力を検知する。
		for (StatusBean s : statusList) {
			if (statusCodeParam.equals(s.getStatusCode())) {
				statusCodeCheck = true;
				break;
			}
		}
		if (!statusCodeCheck) {
			forwardInputError(request, response, "ステータスはプルダウンから選択してください。");
			return;
		}

		//バリデーションチェック⑥（１００文字超過チェック）
		String memoParam = request.getParameter("memo");
		if (memoParam == null ) {
			memoParam = "";
		}
		if (memoParam.length() > 100) {
			forwardInputError(request, response, "メモは１００文字以内で入力してください。");
			return;
		}

		//バリデーションチェックを通過したらBeanに詰める
		TaskBean inputBean = new TaskBean();

		inputBean.setTaskName(taskNameParam);//タスク名
		inputBean.setCategoryId(categoryIdParam);//カテゴリID
		inputBean.setLimitDate(limitDate);//期限
		inputBean.setUserId(userIdParam);//ユーザーID
		inputBean.setStatusCode(statusCodeParam);//ステータスコード
		inputBean.setMemo(memoParam);

		
		//jspに表示する用のデータ取得 取得したcategoryIdと一致するcategoryNameをBeanに詰める
		String categoryNameParam = null;
		for (CategoryBean c : categoryList) {
			if (c.getCategoryId() == categoryIdParam) {
				categoryNameParam = c.getCategoryName();
				break;
			}
		}
		inputBean.setCategoryName(categoryNameParam);

		String userNameParam = null;
		for (UserBean u : userList) {
			if (u.getUserId().equals(userIdParam)) {
				userNameParam = u.getUserName();
				break;
			}
		}
		inputBean.setUserName(userNameParam);

		String statusNameParam = null;
		for (StatusBean s : statusList) {
			if (s.getStatusCode().equals(statusCodeParam)) {
				statusNameParam = s.getStatusName();
				break;
			}
		}
		inputBean.setStatusName(statusNameParam);
	
		//TaskDAOにタスク情報を渡してDBに登録を行う。
		TaskDAO taskDAO = new TaskDAO();
		int insertResult = 0;
		try {
			insertResult = taskDAO.insert(inputBean);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			//失敗画面へ遷移する
			request.setAttribute("inputBean",inputBean);
			request.getRequestDispatcher("task-add-failure.jsp").forward(request, response);
			return;
		}
		//タスク登録成功。
		if(insertResult == 1) {
			request.setAttribute("inputBean",inputBean);
			request.getRequestDispatcher("task-add-success.jsp").forward(request, response);
			return;
		}//タスク登録失敗
		else{
			request.setAttribute("inputBean",inputBean);
			request.getRequestDispatcher("task-add-failure.jsp").forward(request, response);
			return;
		}
	}
	/**
	 * 入力エラーを設定してタスク登録画面へ戻す。
	 */
	private void forwardInputError(
	        HttpServletRequest request,
	        HttpServletResponse response,
	        String errorMessage)
	        throws ServletException, IOException {

	    request.setAttribute("errorMessage", errorMessage);
	    request.getRequestDispatcher("/task-add.jsp")
	            .forward(request, response);
	}
}
