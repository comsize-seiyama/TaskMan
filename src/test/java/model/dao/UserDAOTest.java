package model.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.entity.UserBean;

public class UserDAOTest {

    // テスト項目1：ログイン成功
    @Test
    public void loginSuccess() throws Exception {
        UserBean input = new UserBean();
        input.setUserId("admin");
        input.setPassword("admin");

        UserDAO dao = new UserDAO();
        UserBean actual = dao.login(input);

        assertNotNull(actual);
        assertEquals("admin", actual.getUserId());
        //assertEquals("admin", actual.getPassword());
        assertNotNull(actual.getUserName());
    }

    // テスト項目2：ユーザID不一致
    @Test
    public void loginFailure_WrongUserId() throws Exception {
        UserBean input = new UserBean();
        input.setUserId("999999");
        input.setPassword("admin");

        UserDAO dao = new UserDAO();
        UserBean actual = dao.login(input);

        assertNull(actual);
    }

    // テスト項目3：パスワード不一致
    @Test
    public void loginFailure_WrongPassword() throws Exception {
        UserBean input = new UserBean();
        input.setUserId("admin");
        input.setPassword("0000");

        UserDAO dao = new UserDAO();
        UserBean actual = dao.login(input);

        assertNull(actual);
    }

    // テスト項目4：ユーザーID空欄
    @Test
    public void loginFailure_BlankUserId() throws Exception {
        UserBean input = new UserBean();
        input.setUserId("");
        input.setPassword("admin");

        UserDAO dao = new UserDAO();
        UserBean actual = dao.login(input);

        assertNull(actual);
    }

    // テスト項目5：パスワード空欄
    @Test
    public void loginFailure_BlankPassword() throws Exception {
        UserBean input = new UserBean();
        input.setUserId("admin");
        input.setPassword("");

        UserDAO dao = new UserDAO();
        UserBean actual = dao.login(input);

        assertNull(actual);
    }

    
}
