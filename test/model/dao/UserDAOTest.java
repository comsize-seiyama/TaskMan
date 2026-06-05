package model.dao;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import model.entity.UserBean;

public class UserDAOTest {

    @Test
    
    //テスト項目２
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
    @Test
    //テスト項目３
    public void loginFailure_未登録userID()throws Exception{
    	
    	UserBean input = new UserBean();
        input.setUserId("999999");
        input.setPassword("admin");
        
        UserDAO dao = new UserDAO();
        UserBean actual = dao.login(input);

        assertEquals(null,actual);
        
    }
    @Test
    //テスト項目４
    public void loginFailure_未登録password()throws Exception{
    	
    	UserBean input = new UserBean();
        input.setUserId("admin");
        input.setPassword("0000");
        
        UserDAO dao = new UserDAO();
        UserBean actual = dao.login(input);

        assertEquals(null,actual);
        
    }
    @Test
    //テスト項目５
    public void loginFailure_userID空欄()throws Exception{
 	
	 	UserBean input = new UserBean();
	     input.setUserId("");
	     input.setPassword("admin");
	     
	     UserDAO dao = new UserDAO();
	     UserBean actual = dao.login(input);
	
	     assertEquals(null,actual);
	     
	 }
    @Test
	//テスト項目６
	 public void loginFailure_password空欄()throws Exception{
 	
		 UserBean input = new UserBean();
	     input.setUserId("admin");
	     input.setPassword("");
	     
	     UserDAO dao = new UserDAO();
	     UserBean actual = dao.login(input);
	
	     assertEquals(null,actual);
	     
	 }
    @Test
	//テスト項目７
		 public void loginFailure_userIDとpassword空欄()throws Exception{
	 	
			 UserBean input = new UserBean();
		     input.setUserId("");
		     input.setPassword("");
		     
		     UserDAO dao = new UserDAO();
		     UserBean actual = dao.login(input);
		
		     assertEquals(null,actual);
		     
	 }
		 

}
