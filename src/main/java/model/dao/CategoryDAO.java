package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.CategoryBean;

public class CategoryDAO {

	List<CategoryBean> selectAll() throws ClassNotFoundException, SQLException{
		
		List<CategoryBean> categoryList = new ArrayList<>();
		
		String sql = "SELECT  category_id,category_name	FROM m_category "
				+ "ORDER BY category_id";
		
		try(Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			
			while(rs.next()) {
				CategoryBean categorybean = new CategoryBean();
				
				 categorybean.setCategoryId(rs.getInt("category_id"));
				 categorybean.setCategoryName(rs.getString("category_name"));
				 
				 categoryList.add(categorybean);
			}
			return categoryList;
			
		}
				
	}
	
}
