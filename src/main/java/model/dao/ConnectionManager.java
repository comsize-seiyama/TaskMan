package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
		
		private static final String URL = "jdbc:mysql://localhost:3306/task_db";
		private static final String USER ="root";
		private static final String PASSWORD ="root";

		
		public static Connection getConnection()throws ClassNotFoundException,SQLException {
				//JDBCマネージャを検索
				Class.forName("com.mysql.cj.jdbc.Driver");
				//
				return DriverManager.getConnection(URL,USER,PASSWORD);
				}
				


}
