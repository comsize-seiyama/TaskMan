package model.dao;

import static org.junit.jupiter.api.Assertions.*;

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
}