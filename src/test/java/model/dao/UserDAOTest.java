package model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.entity.UserBean;

public class UserDAOTest {

    // テスト項目2：ログイン成功
    @Test
    public void loginSuccess() throws Exception {
        UserBean input = new UserBean();
        input.setUserId("admin");
        input.setPassword("admin");

        UserDAO dao = new UserDAO();
        UserBean actual = dao.login(input);

        assertNotNull(actual);
        assertEquals("admin", actual.getUserId());
        assertEquals("admin", actual.getPassword());
        assertNotNull(actual.getUserName());
    }

    // テスト項目3：未登録ユーザーID
    @Test
    public void loginFailure_UnregisteredUserId() throws Exception {
        UserBean input = new UserBean();
        input.setUserId("999999");
        input.setPassword("admin");

        UserDAO dao = new UserDAO();
        UserBean actual = dao.login(input);

        assertNull(actual);
    }

    // テスト項目4：パスワード不一致
    @Test
    public void loginFailure_WrongPassword() throws Exception {
        UserBean input = new UserBean();
        input.setUserId("admin");
        input.setPassword("0000");

        UserDAO dao = new UserDAO();
        UserBean actual = dao.login(input);

        assertNull(actual);
    }

    // テスト項目5：ユーザーID空欄
    @Test
    public void loginFailure_BlankUserId() throws Exception {
        UserBean input = new UserBean();
        input.setUserId("");
        input.setPassword("admin");

        UserDAO dao = new UserDAO();
        UserBean actual = dao.login(input);

        assertNull(actual);
    }

    // テスト項目6：パスワード空欄
    @Test
    public void loginFailure_BlankPassword() throws Exception {
        UserBean input = new UserBean();
        input.setUserId("admin");
        input.setPassword("");

        UserDAO dao = new UserDAO();
        UserBean actual = dao.login(input);

        assertNull(actual);
    }

    // テスト項目7：ユーザーIDとパスワード空欄
    @Test
    public void loginFailure_BlankUserIdAndPassword() throws Exception {
        UserBean input = new UserBean();
        input.setUserId("");
        input.setPassword("");

        UserDAO dao = new UserDAO();
        UserBean actual = dao.login(input);

        assertNull(actual);
    }
    
    
    //getUserList()テストメソッド
    @Test
	void getUserList_取得成功() {
		UserDAO dao = new UserDAO();
	    List<UserBean> 取得結果 = null;

	    try {
	        取得結果 = dao.getUserList();
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }

	    UserBean admin = new UserBean();
	    admin.setUserId("admin");
	    admin.setUserName("アドミン太郎");

	    List<UserBean> user = new ArrayList<>();
	    user.add(admin);

	    assertIterableEquals(取得結果, user);
	}

	@Test
	void getUserList_取得失敗() {
		UserDAO dao = new UserDAO();
	    List<UserBean> 取得結果 = null;

	    try {
	        取得結果 = dao.getUserList();
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }

	    List<UserBean> 空List = null;

	    assertIterableEquals(取得結果, 空List);
	}
}


