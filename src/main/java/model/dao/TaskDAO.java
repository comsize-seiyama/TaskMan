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

		String sql = """
			    SELECT
			        t.task_id,
			        t.task_name,
			        c.category_name,
			        t.limit_date,
			        t.user_id,
			        u.user_name,
			        s.status_name,
			        t.memo
			    FROM t_task t
			    INNER JOIN m_category c
			        ON t.category_id = c.category_id
			    INNER JOIN m_user u
			        ON t.user_id = u.user_id
			    INNER JOIN m_status s
			        ON t.status_code = s.status_code
			    ORDER BY t.task_id
			    """;

	    List<TaskBean> taskBeanList = new ArrayList<TaskBean>();

	    try (Connection con = ConnectionManager.getConnection();
	            Statement stmt = con.createStatement();
	            ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	            TaskBean taskBean = new TaskBean();

	            taskBean.setTaskId(rs.getInt("task_id"));
	            taskBean.setTaskName(rs.getString("task_name"));
	            taskBean.setCategoryName(rs.getString("category_name"));
	            
	            java.sql.Date sqlDate = rs.getDate("limit_date") ;
	            taskBean.setLimitDate(sqlDate != null ? sqlDate.toLocalDate() : null);
	            
	            taskBean.setUserId(rs.getString("user_id"));
	            taskBean.setUserName(rs.getString("user_name"));
	            taskBean.setStatusName(rs.getString("status_name"));
	            taskBean.setMemo(rs.getString("memo"));

	            taskBeanList.add(taskBean);
	        }
	    }

	    return taskBeanList;
	}
	

	public TaskBean selectById(int taskId) throws ClassNotFoundException, SQLException {

		String sql = """
			    SELECT
			        t.task_id,
			        t.task_name,
			        t.user_id,
			        c.category_name,
			        t.limit_date,
			        u.user_name,
			        s.status_name,
			        t.memo
			    FROM t_task t
			    INNER JOIN m_category c
			        ON t.category_id = c.category_id
			    INNER JOIN m_user u
			        ON t.user_id = u.user_id
			    INNER JOIN m_status s
			        ON t.status_code = s.status_code
			    WHERE t.task_id = ?
			    """;

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
					
					java.sql.Date sqlDate = rs.getDate("limit_date") ;
		            taskBean.setLimitDate(sqlDate != null ? sqlDate.toLocalDate() : null);
		            taskBean.setUserId(rs.getString("user_id"));
					taskBean.setUserName(rs.getString("user_name"));
					taskBean.setStatusName(rs.getString("status_name"));
					taskBean.setMemo(rs.getString("memo"));
				}
			}
		}

		return taskBean;
	}

	
	public int insert(TaskBean inputBean) throws ClassNotFoundException, SQLException {
		
		String sql = """
			    INSERT INTO t_task
			        (task_name,category_id,limit_date,user_id,status_code,memo)
			    VALUES(?, ?, ?, ?, ?, ?)
			    """;
		
		int insertResult = 0;
		
		try (Connection con = ConnectionManager.getConnection();
	            PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1,inputBean.getTaskName() );
			pstmt.setInt(2,inputBean.getCategoryId());
			//期限がnullだった場合NPEにならないように分岐
			if (inputBean.getLimitDate() == null) {
			    pstmt.setNull(3, java.sql.Types.DATE);//JDBCにDATE型のnullであることを明示
			} else {
			    pstmt.setDate(3,java.sql.Date.valueOf(inputBean.getLimitDate()));
			}
			pstmt.setString(4,inputBean.getUserId());
			pstmt.setString(5,inputBean.getStatusCode());
			pstmt.setString(6, inputBean.getMemo());
			
			insertResult = pstmt.executeUpdate();
		}
		return insertResult;
	}	

	/**
	 * すでに存在しているタスクを編集するためのメソッドです
	 * @author 清山
	 * @param editTask
	 * 編集後のタスク情報が格納されたBeanです
	 * @return 更新されたレコードの件数
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * 
	 */
	public int edit(TaskBean editTask) throws SQLException, ClassNotFoundException {
		String sql = """
			    UPDATE t_task
			    SET
			        task_name = ?,
			        category_id = ?,
			        limit_date = ?,
			        user_id = ?,
			        status_code = ?,
			        memo = ?
			    WHERE task_id = ?
			    """;
		
		int editResult = 0;
		
		try (Connection con = ConnectionManager.getConnection();
		         PreparedStatement pstmt = con.prepareStatement(sql)) {

		        pstmt.setString(1, editTask.getTaskName());
		        pstmt.setInt(2, editTask.getCategoryId());
		        if (!(editTask.getLimitDate() == null)) {
		        	//期限が入力されている場合
					pstmt.setDate(3, java.sql.Date.valueOf(editTask.getLimitDate()));
				}else {
					//期限が入力されていない場合
					pstmt.setNull(3, java.sql.Types.DATE);//JDBCにDATE型のnullであることを明示pstmt.setDate(3,null);
				}
				pstmt.setString(4, editTask.getUserId());
		        pstmt.setString(5, editTask.getStatusCode());
		        pstmt.setString(6, editTask.getMemo());
		        pstmt.setInt(7, editTask.getTaskId());

		        editResult  = pstmt.executeUpdate();
		    }
		return editResult;
	}

	public int deleteTask(int taskId)
	        throws ClassNotFoundException, SQLException {

	    String sql ="""
	            DELETE FROM t_task 
	            WHERE task_id = ?
	            
	    		""";

	    int count = 0;

	    try (Connection con = ConnectionManager.getConnection();
	         PreparedStatement pstmt = con.prepareStatement(sql)) {

	        pstmt.setInt(1, taskId);

	        count = pstmt.executeUpdate();
	    }

	    return count;
	}
}

