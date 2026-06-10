package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.TaskBean;

public class TaskDAO {

	public List<TaskBean> selectAll() throws ClassNotFoundException, SQLException {

		String sql =
				"SELECT " +
				" t.task_id, " +
				" t.task_name, " +
				" c.category_name, " +
				" t.limit_date, " +
				" u.user_name, " +
				" s.status_name, " +
				" t.memo " +
				"FROM t_task t " +
				"INNER JOIN m_category c ON t.category_id = c.category_id " +
				"INNER JOIN m_user u ON t.user_id = u.user_id " +
				"INNER JOIN m_status s ON t.status_code = s.status_code " +
				"ORDER BY t.task_id";

		List<TaskBean> taskBeanList = new ArrayList<TaskBean>();

		try (Connection con = ConnectionManager.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				TaskBean taskBean = new TaskBean();
				taskBean.setTaskId(rs.getInt("task_id"));
				taskBean.setTaskName(rs.getString("task_name"));
				taskBean.setCategoryName(rs.getString("category_name"));
				taskBean.setLimitDate(rs.getDate("limit_date"));
				taskBean.setUserName(rs.getString("user_name"));
				taskBean.setStatusName(rs.getString("status_name"));
				taskBean.setMemo(rs.getString("memo"));

				taskBeanList.add(taskBean);
			}
		}

		return taskBeanList;
	}

	public TaskBean selectById(int taskId) throws ClassNotFoundException, SQLException {

		String sql =
				"SELECT " +
				" t.task_id, " +
				" t.task_name, " +
				" c.category_name, " +
				" t.limit_date, " +
				" u.user_name, " +
				" s.status_name, " +
				" t.memo " +
				"FROM t_task t " +
				"INNER JOIN m_category c ON t.category_id = c.category_id " +
				"INNER JOIN m_user u ON t.user_id = u.user_id " +
				"INNER JOIN m_status s ON t.status_code = s.status_code " +
				"WHERE t.task_id = ?";

		TaskBean taskBean = null;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, taskId);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					taskBean = new TaskBean();
					taskBean.setTaskId(rs.getInt("task_id"));
					taskBean.setTaskName(rs.getString("task_name"));
					taskBean.setCategoryName(rs.getString("category_name"));
					taskBean.setLimitDate(rs.getDate("limit_date"));
					taskBean.setUserName(rs.getString("user_name"));
					taskBean.setStatusName(rs.getString("status_name"));
					taskBean.setMemo(rs.getString("memo"));
				}
			}
		}

		return taskBean;
	}
}