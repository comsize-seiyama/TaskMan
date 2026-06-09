package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.TaskBean;

public class TaskDAO {
	
	public List<TaskBean> selectAll() throws ClassNotFoundException, SQLException {
		String sql = "SELECT t1.task_id,t1.task_name,t3.category_name,"
				+ "t1.limit_date,t2.user_name,t4.status_name,t1.memo"
				+"FROM t_task t1" 
				+"JOIN m_user t2"
			 	+"JOIN m_category t3" 
				+"JOIN m_status t4";
			List<TaskBean> taskBeanList = new ArrayList<TaskBean>();
		try (Connection con = ConnectionManager.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			while (rs.next()) {
				TaskBean taskbean = new TaskBean();
				taskbean.setTaskId(rs.getInt("task_id"));
				taskbean.setTaskName(rs.getString("task_name"));
				taskbean.setCategoryName(rs.getString("category_name"));
				taskbean.setLimitDate(rs.getDate("limit_date"));
				taskbean.setUserName(rs.getString("user_name"));
				taskbean.setStatusName(rs.getString("status_name"));
				taskbean.setMemo(rs.getString("memo"));
				taskBeanList.add(taskbean);
			}
		}
		return taskBeanList;
	}
}
