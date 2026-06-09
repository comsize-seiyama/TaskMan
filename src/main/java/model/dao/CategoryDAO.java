package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.CategoryBean;

public class CategoryDAO {

	public List<CategoryBean> selectAll() throws ClassNotFoundException, SQLException{
		
		List<CategoryBean> categoryBeanList = new ArrayList<>();
		
		String sql = "SELECT  category_id,category_name	FROM m_category "
				+ "ORDER BY category_id";
		
		try(Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			
			while(rs.next()) {
				CategoryBean categoryBean = new CategoryBean();
				
				categoryBean.setCategoryId(rs.getInt("category_id"));
				categoryBean.setCategoryName(rs.getString("category_name"));
				 
				categoryBeanList.add(categoryBean);
			}
			return categoryBeanList;
			
		}
				
	}
	
}
