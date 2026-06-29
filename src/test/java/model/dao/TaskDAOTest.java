package model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.entity.TaskBean;

class TaskDAOTest {

    private final TaskDAO taskDao = new TaskDAO();

    private int testTaskId;
    private int otherTaskId;
    private int insertedTaskId;
    private String insertedTaskName;

    @BeforeEach
    void setUp() throws Exception {
        testTaskId = insertTestTask("JUnit削除テスト用タスク");
    }

    @AfterEach
    void tearDown() throws Exception {
        deleteTestTaskIfExists(testTaskId);
        deleteTestTaskIfExists(otherTaskId);
        deleteTestTaskIfExists(insertedTaskId);
        deleteTestTaskByNameIfExists(insertedTaskName);
    }

    @Test
    void insertWithTaskInfoReturnsOneAndRegistersTask() throws Exception {
        insertedTaskName = "JUnit insert task " + System.nanoTime();
        TaskBean input = insertInput(insertedTaskName, LocalDate.of(2026, 12, 31), "JUnit insert memo");

        int actual = taskDao.insert(input);
        insertedTaskId = findLatestTaskIdByName(insertedTaskName);
        TaskBean insertedTask = taskDao.selectById(insertedTaskId);

        assertEquals(1, actual);
        assertTrue(insertedTaskId > 0);
        assertNotNull(insertedTask);
        assertEquals(insertedTaskName, insertedTask.getTaskName());
        assertEquals(LocalDate.of(2026, 12, 31), insertedTask.getLimitDate());
        assertEquals("admin", insertedTask.getUserId());
        assertEquals("JUnit insert memo", insertedTask.getMemo());
    }

    @Test
    void insertWithNullLimitDateReturnsOneAndRegistersTask() throws Exception {
        insertedTaskName = "JUnit insert no limit date task " + System.nanoTime();
        TaskBean input = insertInput(insertedTaskName, null, "JUnit insert null limit date memo");

        int actual = taskDao.insert(input);
        insertedTaskId = findLatestTaskIdByName(insertedTaskName);
        TaskBean insertedTask = taskDao.selectById(insertedTaskId);

        assertEquals(1, actual);
        assertTrue(insertedTaskId > 0);
        assertNotNull(insertedTask);
        assertEquals(insertedTaskName, insertedTask.getTaskName());
        assertNull(insertedTask.getLimitDate());
        assertEquals("JUnit insert null limit date memo", insertedTask.getMemo());
    }

    @Test
    void 存在するタスクIDを指定すると1件削除される() throws Exception {
        int actual = taskDao.deleteTask(testTaskId);

        assertEquals(1, actual);
        assertNull(taskDao.selectById(testTaskId));
    }

    @Test
    void 存在するタスクIDを削除しても他のタスクは削除されない() throws Exception {
        otherTaskId = insertTestTask("JUnit削除テスト用_残るタスク");

        int actual = taskDao.deleteTask(testTaskId);

        assertEquals(1, actual);
        assertNull(taskDao.selectById(testTaskId));
        assertNotNull(taskDao.selectById(otherTaskId));
    }

    @Test
    void 存在しないタスクIDを指定すると0件削除される() throws Exception {
        int actual = taskDao.deleteTask(Integer.MAX_VALUE);

        assertEquals(0, actual);
        assertNotNull(taskDao.selectById(testTaskId));
    }

    @Test
    void taskIdに0を指定すると0件削除される() throws Exception {
        int actual = taskDao.deleteTask(0);

        assertEquals(0, actual);
        assertNotNull(taskDao.selectById(testTaskId));
    }

    @Test
    void taskIdに負の値を指定すると0件削除される() throws Exception {
        int actual = taskDao.deleteTask(-1);

        assertEquals(0, actual);
        assertNotNull(taskDao.selectById(testTaskId));
    }

    @Test
    void 同じレコードを2回削除すると2回目は0件削除される() throws Exception {
        int firstActual = taskDao.deleteTask(testTaskId);
        int secondActual = taskDao.deleteTask(testTaskId);

        assertEquals(1, firstActual);
        assertEquals(0, secondActual);
        assertNull(taskDao.selectById(testTaskId));
    }

    private int insertTestTask(String taskName) throws ClassNotFoundException, SQLException {
        String sql = """
                INSERT INTO t_task
                    (task_name, category_id, limit_date, user_id, status_code, memo)
                VALUES
                    (?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, taskName);
            pstmt.setInt(2, 1);
            pstmt.setDate(3, java.sql.Date.valueOf("2026-12-31"));
            pstmt.setString(4, "admin");
            pstmt.setString(5, "01");
            pstmt.setString(6, "削除テスト用データ");

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        throw new SQLException("タスクIDの取得に失敗しました。");
    }

    private TaskBean insertInput(String taskName, LocalDate limitDate, String memo) {
        TaskBean input = new TaskBean();
        input.setTaskName(taskName);
        input.setCategoryId(1);
        input.setLimitDate(limitDate);
        input.setUserId("admin");
        input.setStatusCode("01");
        input.setMemo(memo);
        return input;
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


@Test
void insertに新規タスク情報を指定すると1件登録される() throws Exception {
    insertedTaskName = "JUnit insert task " + System.nanoTime();
    TaskBean input = insertInput(insertedTaskName, LocalDate.of(2026, 12, 31), "JUnit insert memo");

    int actual = taskDao.insert(input);
    insertedTaskId = findLatestTaskIdByName(insertedTaskName);
    TaskBean insertedTask = taskDao.selectById(insertedTaskId);

    assertEquals(1, actual);
    assertTrue(insertedTaskId > 0);
    assertNotNull(insertedTask);
    assertEquals(insertedTaskName, insertedTask.getTaskName());
    assertEquals(LocalDate.of(2026, 12, 31), insertedTask.getLimitDate());
    assertEquals("admin", insertedTask.getUserId());
    assertEquals("JUnit insert memo", insertedTask.getMemo());
}

@Test
void insertに期限日なしのタスク情報を指定すると1件登録される() throws Exception {
    insertedTaskName = "JUnit insert no limit date task " + System.nanoTime();
    TaskBean input = insertInput(insertedTaskName, null, "JUnit insert null limit date memo");

    int actual = taskDao.insert(input);
    insertedTaskId = findLatestTaskIdByName(insertedTaskName);
    TaskBean insertedTask = taskDao.selectById(insertedTaskId);

    assertEquals(1, actual);
    assertTrue(insertedTaskId > 0);
    assertNotNull(insertedTask);
    assertEquals(insertedTaskName, insertedTask.getTaskName());
    assertNull(insertedTask.getLimitDate());
    assertEquals("JUnit insert null limit date memo", insertedTask.getMemo());
	}
}