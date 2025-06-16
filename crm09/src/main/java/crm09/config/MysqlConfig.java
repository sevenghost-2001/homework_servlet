package crm09.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConfig {
	public static Connection getConnection() {
		Connection connection = null;
		try {
			String url = "jdbc:mysql://localhost:3307/crmapp";
			String username = "root";
			String password = "admin123"; 
			//Khai báo Driver để sử dụng
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			System.out.println("Lỗi kết nối cơ sở dữ liệu" +e.getMessage());
		}
		return connection;
	}
}
