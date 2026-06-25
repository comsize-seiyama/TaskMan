package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
		}
		request.setCharacterEncoding("UTF-8");
		//タスクIDを取得
		int taskId = 0;
		try {
			taskId = Integer.parseInt(request.getParameter("taskId"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			//例外フローE2のケース
			//タスク一覧表示画面に再遷移し
			//エラーメッセージ「編集するタスクを選択してください。」を表示
			request.setAttribute("message", "編集するタスクを選択してください。");
			request.getRequestDispatcher("task-list.jsp").forward(request, response);					}

		//各DAOを取得
		TaskDAO taskDAO = new TaskDAO();
		UserDAO userDAO = new UserDAO();
		CategoryDAO categoryDAO = new CategoryDAO();
		StatusDAO statusDAO = new StatusDAO();

		//各Bean,BeanListを宣言
		TaskBean beforeTaskBean = null;
		List<UserBean> userList = null;
		List<CategoryBean> categoryList = null;
		List<StatusBean> statusList = null;

		//タスク編集画面のプルダウン表示に必要な以下のマスタ情報を取得
		try {
			beforeTaskBean = taskDAO.selectById(taskId);
			userList = userDAO.getUserList();
			categoryList = categoryDAO.selectAll();
			statusList = statusDAO.selectAll();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			//例外フローA5のケース
			//タスク一覧表示画面に再遷移し
			//エラーメッセージ「データベース情報の取得に失敗しました。」を表示する。
			request.setAttribute("message", "データベース情報の取得に失敗しました。");
			request.getRequestDispatcher("task-list.jsp").forward(request, response);
			return;
		}
		//代替フローA6のケース
		if (beforeTaskBean == null) {
			//タスク一覧表示画面に再遷移、エラーメッセージ「選択されたタスクに編集権限がないか、存在していません」を表示
			request.setAttribute("message", "選択されたタスクに編集権限がないか、存在していません");
			request.getRequestDispatcher("task-list.jsp").forward(request, response);
			return;
			
		}

		UserBean loginUserBean = (UserBean) session.getAttribute("loginUser");

		if (!loginUserBean.getUserId().equals(beforeTaskBean.getUserId())) {
			//タスク一覧表示画面を再表示し、
			//エラーメッセージ「選択されたタスクに編集権限がないか、存在していません」を表示する。
			request.setAttribute("message", "選択されたタスクに編集権限がないか、存在していません");
			request.getRequestDispatcher("task-list.jsp").forward(request, response);
			return;
		}
		//タスク情報、編集前の担当者IDをセッションスコープに詰める
		session.setAttribute("beforeTaskBean", beforeTaskBean);
		session.setAttribute("userList", userList);
		session.setAttribute("categoryList", categoryList);
		session.setAttribute("statusList", statusList);

		request.getRequestDispatcher("task-edit-form.jsp").forward(request, response);
		
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
			
		}
		request.setCharacterEncoding("UTF-8");
		
		TaskBean beforeTaskBean =(TaskBean)session.getAttribute("beforeTaskBean");
		
		//不正なアクセスによってdoPostが呼び出されたときにリストがnullになるのでNPE回避用
			//カテゴリ名、担当者、ステータスが一致しているかチェック用のListを取得
				List<CategoryBean> categoryList = (List<CategoryBean>) session.getAttribute("categoryList");

				List<UserBean> userList = (List<UserBean>) session.getAttribute("userList");

				List<StatusBean> statusList = (List<StatusBean>) session.getAttribute("statusList");

				if (categoryList == null || userList == null || statusList == null) {
					response.sendRedirect("login.jsp");
					
				}
		//タスク編集画面で入力されたパラメータ取得
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

		int categoryIdParam = 0;
		try {
			categoryIdParam = Integer.parseInt(request.getParameter("categoryId"));
		} catch (NumberFormatException e) {
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
				forwardInputError(request, response, "日付はカレンダーから指定してください。");
				return;
			}

			// 過去日チェック
			if (limitDate.isBefore(LocalDate.now())) {
				forwardInputError(request, response, "期限に過去日付は指定できません。");
				return;
			}
		}

		//バリデーションチェック④（担当者チェック）
		String userIdParam = request.getParameter("userId");
		if (userIdParam == null || userIdParam.isBlank()) {
			forwardInputError(request, response, "担当者名はプルダウンから選択してください。");
			
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
		if (memoParam == null) {
			memoParam = "";
		}
		if (memoParam.length() > 100) {
			forwardInputError(request, response, "メモは１００文字以内で入力してください。");
			return;
		}
		
		//編集後のタスク情報を新しくインスタンス化したBeanに詰める
		TaskBean editTaskBean = new TaskBean();
		editTaskBean.setTaskId(beforeTaskBean.getTaskId());
		editTaskBean.setTaskName(taskNameParam);
		editTaskBean.setCategoryId(categoryIdParam);
		editTaskBean.setLimitDate(limitDate);
		editTaskBean.setUserId(userIdParam);
		editTaskBean.setStatusCode(statusCodeParam);
		editTaskBean.setMemo(memoParam);
		//jspに表示する用のデータ取得 取得したcategoryIdと一致するcategoryNameをBeanに詰める
				String categoryNameParam = null;
				for (CategoryBean c : categoryList) {
					if (c.getCategoryId() == categoryIdParam) {
						categoryNameParam = c.getCategoryName();
						break;
					}
				}
				editTaskBean.setCategoryName(categoryNameParam);

				String userNameParam = null;
				for (UserBean u : userList) {
					if (u.getUserId().equals(userIdParam)) {
						userNameParam = u.getUserName();
						break;
					}
				}
				editTaskBean.setUserName(userNameParam);

				String statusNameParam = null;
				for (StatusBean s : statusList) {
					if (s.getStatusCode().equals(statusCodeParam)) {
						statusNameParam = s.getStatusName();
						break;
					}
				}
				editTaskBean.setStatusName(statusNameParam);

			/*
			 * A7.基本フロー8において、編集対象タスクが存在しない、
			 * または編集対象タスクの更新前担当者とログインユーザが一致しない場合
			 */
			TaskDAO taskDAO = new TaskDAO();
			TaskBean latestTaskBean;
			try {
				//タスク情報をデータベースから再度取得する
				latestTaskBean = taskDAO.selectById(beforeTaskBean.getTaskId());
			} catch (ClassNotFoundException | SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
					//失敗画面へ遷移する
					request.setAttribute("editTaskBean", editTaskBean);
					request.getRequestDispatcher("task-edit-failure.jsp").forward(request, response);
					return;
			}
			//ログインユーザ情報を取得する
			UserBean loginUserBean = (UserBean)session.getAttribute("loginUser");
			
			/*
			 * ログインユーザのユーザIDと
			 * データベースから再度取得したタスクの担当者IDが一致しているか確認
			 */
			if(!loginUserBean.getUserId().equals(latestTaskBean.getUserId())) {
				//失敗画面へ遷移する
				request.setAttribute("editTaskBean", editTaskBean);
				request.setAttribute("message", "選択されたタスクに編集権限がないか、存在していません");
				request.getRequestDispatcher("task-edit-failure.jsp").forward(request, response);
				return;
			
			}
		//TaskDAOにタスク情報を渡してDB情報を更新する
		int editResult = 0;
		try {
			editResult = taskDAO.edit(editTaskBean);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			//失敗画面へ遷移する
			request.setAttribute("editTaskBean", editTaskBean);
			request.getRequestDispatcher("task-edit-failure.jsp").forward(request, response);
			return;
		}
		if (editResult == 1) {
			request.setAttribute("editTaskBean", editTaskBean);
			request.getRequestDispatcher("task-edit-success.jsp").forward(request, response);

		}

	}

	//入力エラーを設定してタスク編集画面へ戻すメソッド
	private void forwardInputError(
			HttpServletRequest request,
			HttpServletResponse response,
			String message)
			throws ServletException, IOException {

		request.setAttribute("message", message);
		request.getRequestDispatcher("task-edit-form.jsp").forward(request, response);
	}

}
