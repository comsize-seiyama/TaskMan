package model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.entity.TaskBean;

class TaskDAOTest {

	@Test
	void testSelectAll() throws ClassNotFoundException, SQLException {
		TaskDAO dao = new TaskDAO();
	    List<TaskBean> taskList = dao.selectAll();
	    
	    assertNotNull(taskList);
	    
	    assertFalse(taskList.isEmpty());
	    
	    assertNotNull(taskList.get(0));
	    
	    assertNotNull(taskList.get(0).getTaskName());
	    
	    
	}
	@Test
	void selectById()  throws ClassNotFoundException, SQLException {
		TaskDAO dao = new TaskDAO();
		 int taskId =22;
		 TaskBean taskBean = dao.selectById(taskId);
		 
		 assertNotNull(taskBean);
		 
		 assertEquals(22, taskBean.getTaskId());
		
}
	@Test
	void insert() throws ClassNotFoundException, SQLException {
		TaskDAO dao = new TaskDAO();

        TaskBean taskBean = new TaskBean();
        taskBean.setTaskName("テスト");
        taskBean.setCategoryId(1);
        taskBean.setLimitDate(LocalDate.parse("2026-12-31"));
        taskBean.setUserId("1");
        taskBean.setStatusCode("01");
        taskBean.setMemo("テストデータ");

        int result = dao.insert(taskBean);

        assertEquals(1, result);

	}
}
