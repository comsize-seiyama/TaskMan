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
			return;
		}
		request.setCharacterEncoding("UTF-8");
		//タスクIDを取得
		int taskId;
		try {
			taskId = Integer.parseInt(request.getParameter("taskId"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			//例外フローE2のケース
			//タスク一覧表示画面に再遷移し
			//エラーメッセージ「編集するタスクを選択してください。」を表示
			forwardInputError(request, response, "編集するタスクを選択してください。");
			request.getRequestDispatcher("task-list.jsp").forward(request, response);
			return;
		}

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
			e.printStackTrace();
			//例外フローA5のケース
			//タスク一覧表示画面に再遷移し
			//エラーメッセージ「データベース情報の取得に失敗しました。」を表示
			forwardInputError(request, response, "データベース情報の取得に失敗しました。");
			request.getRequestDispatcher("task-list.jsp").forward(request, response);
			return;
		}
		//代替フローA6のケース
		if (taskBean == null) {
			//タスク一覧表示画面に再遷移、エラーメッセージ「選択されたタスクに編集権限がないか、存在していません」を表示
			forwardInputError(request, response, "選択されたタスクに編集権限がないか、存在していません");
			request.getRequestDispatcher("task-list.jsp").forward(request, response);
			return;
		}

		UserBean loginUserBean = (UserBean) session.getAttribute("userBean");

		if (!loginUserBean.getUserId().equals(taskBean.getUserId())) {
			//タスク一覧表示画面を再表示し、
			//エラーメッセージ「選択されたタスクに編集権限がないか、存在していません」を表示する。
			forwardInputError(request, response, "選択されたタスクに編集権限がないか、存在していません");
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
		
		TaskBean beforeTaskBean =(TaskBean)session.getAttribute("taskBean");
		
		//不正なアクセスによってdoPostが呼び出されたときにリストがnullになるのでNPE回避用
			//カテゴリ名、担当者、ステータスが一致しているかチェック用のListを取得
				List<CategoryBean> categoryList = (List<CategoryBean>) session.getAttribute("categoryList");

				List<UserBean> userList = (List<UserBean>) session.getAttribute("userList");

				List<StatusBean> statusList = (List<StatusBean>) session.getAttribute("statusList");

				if (categoryList == null || userList == null || statusList == null) {
					response.sendRedirect("login.jsp");
					return;
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

		int categoryIdParam;
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
		if (memoParam == null) {
			memoParam = "";
		}
		if (memoParam.length() > 100) {
			forwardInputError(request, response, "メモは１００文字以内で入力してください。");
			return;
		}
		
		int taskId = beforeTaskBean.getTaskId();
		TaskBean editTaskBean = new TaskBean();
		editTaskBean.setTaskId(taskId);
		editTaskBean.setTaskName(taskNameParam);
		editTaskBean.setCategoryId(categoryIdParam);
		editTaskBean.setLimitDate(limitDate);
		editTaskBean.setUserId(userIdParam);
		editTaskBean.setStatusCode(statusCodeParam);
		editTaskBean.setMemo(memoParam);

		//TaskDAOにタスク情報を渡してDB情報を更新する
		TaskDAO taskDAO = new TaskDAO();
		int editResult = 0;
		try {
			editResult = taskDAO.edit(editTaskBean);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			//失敗画面へ遷移する
			request.setAttribute("editTaskBean", editTaskBean);
			request.getRequestDispatcher("task-edit-success.jsp").forward(request, response);
		}
		if (editResult == 1) {
			request.setAttribute("editTaskBean", editTaskBean);
			request.getRequestDispatcher("task-edit-success.jsp").forward(request, response);

		}

	}

	//入力エラーを設定してタスク登録画面へ戻すメソッド
	private void forwardInputError(
			HttpServletRequest request,
			HttpServletResponse response,
			String message)
			throws ServletException, IOException {

		request.setAttribute("message", message);
		request.getRequestDispatcher("task-list.jsp")
				.forward(request, response);
	}

}
