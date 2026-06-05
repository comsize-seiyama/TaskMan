package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	/**
	 * タスク登録画面での担当者欄のプルダウン用に<br>
	 * ユーザー情報テーブルからユーザーIDとユーザー名の一覧を取得します。
	 *
	 * @return ユーザー情報のリスト
	 * @throws ClassNotFoundException JDBCドライバの読み込みに失敗した場合
	 * @throws SQLException データベースアクセス時にエラーが発生した場合
	 * @author 林
	 */
	public List<UserBean> getUserList() throws ClassNotFoundException, SQLException {
		
		List<UserBean> userBeanList = new ArrayList <> ();
		
		String sql = "SELECT user_id,user_name FROM m_user" ;
		
		try (Connection con = ConnectionManager.getConnection(); 
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
						) {
			
			while(rs.next()) {
				
				UserBean userBean = new UserBean();
				
				userBean.setUserId(rs.getString("user_id"));
				userBean.setUserName(rs.getString("user_name"));
				
				userBeanList.add(userBean);
			}
		}
		return	userBeanList;	
	}
}
