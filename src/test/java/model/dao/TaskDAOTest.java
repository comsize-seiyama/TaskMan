package model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
	
	@Test
	void edit_正常更新_編集項目全入力(){
		TaskDAO dao = new TaskDAO();
		
		TaskBean bean = new TaskBean();
		
		bean.setTaskId(33);
		bean.setTaskName("単体テスト");
		bean.setCategoryId(1);
		bean.setLimitDate(LocalDate.parse("2026-07-04"));
		bean.setUserId("admin");
		bean.setStatusCode("02");
		bean.setMemo("現在単体テストを行っております。");
		
		int result = 0;
		try {
			result = dao.edit(bean);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO: handle exception
		}
		
		assertEquals(1, result);
	}
	
	@Test
	void edit_正常更新_limitDateのみnull() {
		TaskDAO dao = new TaskDAO();
		
		TaskBean bean = new TaskBean();
		
		bean.setTaskId(33);
		
		bean.setTaskName("単体テスト");
		bean.setCategoryId(1);
		bean.setLimitDate(null);//← null
		bean.setUserId("admin");
		bean.setStatusCode("02");
		bean.setMemo("現在単体テストを行っております。");
		
		int result = 0;
		try {
			result = dao.edit(bean);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO: handle exception
		}
		
		assertEquals(1, result);
	}
	
	@Test
	void edit_正常更新_memoのみnull() {
		TaskDAO dao = new TaskDAO();
		
		TaskBean bean = new TaskBean();
		
		bean.setTaskId(33);
		bean.setTaskName("単体テスト");
		bean.setCategoryId(1);
		bean.setLimitDate(LocalDate.parse("2026-07-04"));
		bean.setUserId("admin");
		bean.setStatusCode("02");
		bean.setMemo(null);
		
		int result = 0;
		try {
			result = dao.edit(bean);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO: handle exception
		}		
		assertEquals(1, result);
	}
	
	@Test
	void edit_正常更新_limitDateとmemoがnull() {
		TaskDAO dao = new TaskDAO();
		
		TaskBean bean = new TaskBean();
		
		bean.setTaskId(33);
		bean.setTaskName("単体テスト");
		bean.setCategoryId(1);
		bean.setLimitDate(null);
		bean.setUserId("admin");
		bean.setStatusCode("02");
		bean.setMemo(null);
		
		int result = 0;
		try {
			result = dao.edit(bean);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO: handle exception
		}		
		assertEquals(1, result);
	}
	
	@Test
	void edit_失敗_存在しないtaskId()  {
		TaskDAO dao = new TaskDAO();
		
		TaskBean bean = new TaskBean();
		
		bean.setTaskId(9999);
		bean.setTaskName("単体テスト");
		bean.setCategoryId(1);
		bean.setLimitDate(null);
		bean.setUserId("admin");
		bean.setStatusCode("02");
		bean.setMemo(null);
		
		int result = -1;
		try {
			result = dao.edit(bean);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO: handle exception
		}		
		assertEquals(0, result);
	}
	
	@Test
	void edit_失敗_入力必須項目全てがnull() {
		TaskDAO dao = new TaskDAO();
		
		TaskBean bean = new TaskBean();
		
		bean.setTaskId(33);
		bean.setTaskName(null);
		bean.setCategoryId(1);
		bean.setLimitDate(null);
		bean.setUserId(null);
		bean.setStatusCode(null);
		bean.setMemo(null);
		
		assertThrows(SQLException.class,()->{
			int result = dao.edit(bean);
		});
	}
	
	@Test
	void edit_失敗_taskNameのみnull() {
		TaskDAO dao = new TaskDAO();
		
		TaskBean bean = new TaskBean();
		
		bean.setTaskId(33);
		bean.setTaskName(null);
		bean.setCategoryId(1);
		bean.setLimitDate(null);
		bean.setUserId("admin");
		bean.setStatusCode("02");
		bean.setMemo(null);
		
		assertThrows(SQLIntegrityConstraintViolationException.class,()->{
			int result = dao.edit(bean);
		});
	}
	
	@Test
	void edit_失敗_userIdのみnull() {
		TaskDAO dao = new TaskDAO();
		
		TaskBean bean = new TaskBean();
		
		bean.setTaskId(33);
		bean.setTaskName("単体テスト");
		bean.setCategoryId(1);
		bean.setLimitDate(null);
		bean.setUserId(null);
		bean.setStatusCode("02");
		bean.setMemo(null);
		
		assertThrows(SQLIntegrityConstraintViolationException.class,()->{
			int result = dao.edit(bean);
		});
		
		
	}
}