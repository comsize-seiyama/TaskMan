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
	void testSelectById() throws Exception {

	    TaskDAO dao = new TaskDAO();

	    TaskBean taskBean = dao.selectById(33);

	    assertNotNull(taskBean);

	    assertEquals(33, taskBean.getTaskId());
	    assertEquals("テスト", taskBean.getTaskName());
	    assertEquals("新商品A:開発プロジェクト", taskBean.getCategoryName());
	    assertEquals(LocalDate.of(2026, 12, 31), taskBean.getLimitDate());
	    assertEquals("1", taskBean.getUserId());
	    assertEquals("test", taskBean.getUserName());
	    assertEquals("未着手", taskBean.getStatusName());
	    assertEquals("テストデータ", taskBean.getMemo());
	    
	    
	    taskBean = dao.selectById(9999);

	    assertNull(taskBean);
	    
	    taskBean = dao.selectById(45);

	    assertNotNull(taskBean);
	    assertNull(taskBean.getLimitDate());
	    
	    
	    
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
        
        
        taskBean.setTaskName("期限なしテスト");
        taskBean.setCategoryId(1);
        taskBean.setLimitDate(null);   // ← null
        taskBean.setUserId("1");
        taskBean.setStatusCode("01");
        taskBean.setMemo("テスト");
        
        int nullresult = dao.insert(taskBean);

        assertEquals(1, nullresult);

	}
}
