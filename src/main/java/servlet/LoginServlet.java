package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.UserDAO;
import model.entity.UserBean;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
   
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *  予期しないアクセスに対して戻す
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

	/**

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        UserBean userBean = new UserBean();
        userBean.setUserId(userId);
        userBean.setPassword(password);

        UserDAO userDao = new UserDAO();

        try {
            UserBean loginUser = userDao.login(userBean);

            // ログイン失敗時
            if (loginUser == null) {
                request.setAttribute("errorMessage", "ユーザーIDまたはパスワードが違います。");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            // ログイン成功時
            HttpSession session = request.getSession();
            session.setAttribute("userName", loginUser.getUserName());
            session.setAttribute("userId", loginUser.getUserId());

            request.getRequestDispatcher("menu.jsp").forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

            request.setAttribute("errorMessage", "システムエラーが発生しました。");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}