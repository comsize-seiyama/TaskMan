
package model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.entity.TaskBean;

class TaskDAOTest {

    private final TaskDAO taskDao = new TaskDAO();

    private int testCategoryId;
    private String testUserId;
    private String testStatusCode;
    private int insertedTaskId;
    private String insertedTaskName;

    @BeforeEach
    void setUpInsertTest() throws Exception {
        testCategoryId = findExistingCategoryId();
        testUserId = findExistingUserId();
        testStatusCode = findExistingStatusCode();
        insertedTaskId = 0;
        insertedTaskName = null;
    }

    @AfterEach
    void tearDownInsertTest() throws Exception {
        deleteTestTaskIfExists(insertedTaskId);
        deleteTestTaskByNameIfExists(insertedTaskName);
    }
    @Test
    void edit_正常更新_編集項目全入力() {
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
        bean.setLimitDate(null);
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
    void edit_失敗_存在しないtaskId() {
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

        assertThrows(SQLException.class, () -> {
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

        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
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

        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            int result = dao.edit(bean);
        });
    }

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
        taskBean.setLimitDate(null);
        taskBean.setUserId("1");
        taskBean.setStatusCode("01");
        taskBean.setMemo("テスト");

        int nullresult = dao.insert(taskBean);

        assertEquals(1, nullresult);
    }

    @Test
    void insertに新規タスク情報を指定すると1件登録される() throws Exception {
        insertedTaskName = "JUnit insert task " + System.nanoTime();
        LocalDate limitDate = LocalDate.of(2026, 12, 31);
        String memo = "JUnit insert memo";
        TaskBean input = insertInput(insertedTaskName, limitDate, memo);

        int actual = taskDao.insert(input);
        insertedTaskId = findLatestTaskIdByName(insertedTaskName);

        assertEquals(1, actual);
        assertTrue(insertedTaskId > 0);
        assertTaskRow(insertedTaskId, insertedTaskName, limitDate, memo);
    }

    @Test
    void insertに期限日なしのタスク情報を指定すると1件登録される() throws Exception {
        insertedTaskName = "JUnit insert no limit date task " + System.nanoTime();
        String memo = "JUnit insert null limit date memo";
        TaskBean input = insertInput(insertedTaskName, null, memo);

        int actual = taskDao.insert(input);
        insertedTaskId = findLatestTaskIdByName(insertedTaskName);

        assertEquals(1, actual);
        assertTrue(insertedTaskId > 0);
        assertTaskRow(insertedTaskId, insertedTaskName, null, memo);
    }

    @Test
    void insertに存在しないカテゴリIDを指定するとSQLExceptionが発生し登録されない() throws Exception {
        insertedTaskName = "JUnit insert invalid category task " + System.nanoTime();
        TaskBean input = insertInput(insertedTaskName, LocalDate.of(2026, 12, 31), "JUnit insert error memo");
        input.setCategoryId(Integer.MAX_VALUE);

        assertThrows(SQLException.class, () -> taskDao.insert(input));
        assertEquals(0, findLatestTaskIdByName(insertedTaskName));
    }

    @Test
    void insertにnullのTaskBeanを指定するとNullPointerExceptionが発生する() {
        assertThrows(NullPointerException.class, () -> taskDao.insert(null));
    }

    @Test
    void insertにタスク名nullのタスク情報を指定するとSQLExceptionが発生し登録されない() throws Exception {
        String memo = "JUnit insert null task name memo " + System.nanoTime();
        TaskBean input = insertInput(null, LocalDate.of(2026, 12, 31), memo);

        assertThrows(SQLException.class, () -> taskDao.insert(input));
        assertEquals(0, countTaskRowsByMemo(memo));
    }

    @Test
    void insertに存在しないユーザーIDを指定するとSQLExceptionが発生し登録されない() throws Exception {
        insertedTaskName = "JUnit insert invalid user task " + System.nanoTime();
        TaskBean input = insertInput(insertedTaskName, LocalDate.of(2026, 12, 31), "JUnit insert invalid user memo");
        input.setUserId("unknown_user");

        assertThrows(SQLException.class, () -> taskDao.insert(input));
        assertEquals(0, findLatestTaskIdByName(insertedTaskName));
    }

    @Test
    void insertにユーザーIDnullのタスク情報を指定するとSQLExceptionが発生し登録されない() throws Exception {
        insertedTaskName = "JUnit insert null user task " + System.nanoTime();
        TaskBean input = insertInput(insertedTaskName, LocalDate.of(2026, 12, 31), "JUnit insert null user memo");
        input.setUserId(null);

        assertThrows(SQLException.class, () -> taskDao.insert(input));
        assertEquals(0, findLatestTaskIdByName(insertedTaskName));
    }

    @Test
    void insertに存在しないステータスコードを指定するとSQLExceptionが発生し登録されない() throws Exception {
        insertedTaskName = "JUnit insert invalid status task " + System.nanoTime();
        TaskBean input = insertInput(insertedTaskName, LocalDate.of(2026, 12, 31), "JUnit insert invalid status memo");
        input.setStatusCode("99");

        assertThrows(SQLException.class, () -> taskDao.insert(input));
        assertEquals(0, findLatestTaskIdByName(insertedTaskName));
    }

    @Test
    void insertにステータスコードnullのタスク情報を指定するとSQLExceptionが発生し登録されない() throws Exception {
        insertedTaskName = "JUnit insert null status task " + System.nanoTime();
        TaskBean input = insertInput(insertedTaskName, LocalDate.of(2026, 12, 31), "JUnit insert null status memo");
        input.setStatusCode(null);

        assertThrows(SQLException.class, () -> taskDao.insert(input));
        assertEquals(0, findLatestTaskIdByName(insertedTaskName));
    }

    

    private TaskBean insertInput(String taskName, LocalDate limitDate, String memo) {
        TaskBean input = new TaskBean();
        input.setTaskName(taskName);
        input.setCategoryId(testCategoryId);
        input.setLimitDate(limitDate);
        input.setUserId(testUserId);
        input.setStatusCode(testStatusCode);
        input.setMemo(memo);
        return input;
    }

    private int findExistingCategoryId() throws ClassNotFoundException, SQLException {
        String sql = "SELECT category_id FROM m_category ORDER BY category_id LIMIT 1";

        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("category_id");
            }
        }

        throw new SQLException("テスト用カテゴリが存在しません。");
    }

    private String findExistingUserId() throws ClassNotFoundException, SQLException {
        String sql = "SELECT user_id FROM m_user ORDER BY user_id LIMIT 1";

        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getString("user_id");
            }
        }

        throw new SQLException("テスト用ユーザーが存在しません。");
    }

    private String findExistingStatusCode() throws ClassNotFoundException, SQLException {
        String sql = "SELECT status_code FROM m_status ORDER BY status_code LIMIT 1";

        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getString("status_code");
            }
        }

        throw new SQLException("テスト用ステータスが存在しません。");
    }

    private int findLatestTaskIdByName(String taskName) throws ClassNotFoundException, SQLException {
        String sql = """
                SELECT task_id
                FROM t_task
                WHERE task_name = ?
                ORDER BY task_id DESC
                LIMIT 1
                """;

        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, taskName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("task_id");
                }
            }
        }

        return 0;
    }

    private int countTaskRowsByMemo(String memo) throws ClassNotFoundException, SQLException {
        String sql = """
                SELECT COUNT(*) AS count
                FROM t_task
                WHERE memo = ?
                """;

        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, memo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        }

        return 0;
    }

    private void assertTaskRow(int taskId, String taskName, LocalDate limitDate, String memo)
            throws ClassNotFoundException, SQLException {
        String sql = """
                SELECT task_name, category_id, limit_date, user_id, status_code, memo
                FROM t_task
                WHERE task_id = ?
                """;

        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, taskId);

            try (ResultSet rs = pstmt.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(taskName, rs.getString("task_name"));
                assertEquals(testCategoryId, rs.getInt("category_id"));
                assertEquals(testUserId, rs.getString("user_id"));
                assertEquals(testStatusCode, rs.getString("status_code"));
                assertEquals(memo, rs.getString("memo"));

                java.sql.Date actualLimitDate = rs.getDate("limit_date");
                if (limitDate == null) {
                    assertNull(actualLimitDate);
                } else {
                    assertEquals(limitDate, actualLimitDate.toLocalDate());
                }

                assertFalse(rs.next());
            }
        }
    }

    private void deleteTestTaskIfExists(int taskId) throws ClassNotFoundException, SQLException {
        if (taskId <= 0) {
            return;
        }

        String sql = "DELETE FROM t_task WHERE task_id = ?";

        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, taskId);
            pstmt.executeUpdate();
        }
    }

    private void deleteTestTaskByNameIfExists(String taskName) throws ClassNotFoundException, SQLException {
        if (taskName == null) {
            return;
        }

        String sql = "DELETE FROM t_task WHERE task_name = ?";

        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, taskName);
            pstmt.executeUpdate();
        }
    }
}
