package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.entity.UserBean;

public class UserDAO {

	public UserBean login(UserBean bean) throws ClassNotFoundException, SQLException {

		String sql = "SELECT * FROM m_user WHERE user_id=? AND password=?";
		String userId = bean.getUserId();
		String password = bean.getPassword();
		UserBean returnBean = null;
		

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, userId);
			pstmt.setString(2, password);
			
			
			ResultSet rs =pstmt.executeQuery();
			if(rs.next()) {
			returnBean = new UserBean();
			returnBean.setUserName(rs.getString("user_name"));
			returnBean.setUserId(rs.getString("user_id"));
			returnBean.setPassword(rs.getString("password"));
			returnBean.setUpdateDatetime(rs.getTimestamp("update_datetime"));
			}
		}
		return returnBean;
	}

}
