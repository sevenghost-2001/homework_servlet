package crm09.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm09.config.MysqlConfig;
import crm09.entity.User;

@WebServlet(name = "loginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String emailString = req.getParameter("email");
		String passwordString = req.getParameter("password");
		
		//Chuẩn bị câu truy vấn
		String query = "SELECT * \r\n"
				+ "FROM users u\r\n"
				+ "WHERE u.email = ? AND u.password = ? " ;
		
		//Bước 3: Mở kết nối cơ sở dữ liệu
		Connection connection = MysqlConfig.getConnection();
		
		//Bước 4: Truyền câu query của mình vào connection mới vừa mở để truyền xuống cơ sở dữ liệu
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			//Truyền tham số vào dấu chấm hỏi tham số
			preparedStatement.setString(1, emailString);
			preparedStatement.setString(2, passwordString);
			
			// Thực thi câu truy vấn
			/*
			 * executeQuery : Khi câu truy vấn là SELECT
			 * executeUpdate: không phải là câu SELECT 
			 */
			//ResultSet không hỗ trợ lấy 1 dòng dữ liệu
			ResultSet resultSet = preparedStatement.executeQuery();
			List<User> listUsers = new ArrayList<User>();
			//Map từng dòng dữ liệu
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setEmail(resultSet.getString("email"));
				
				listUsers.add(user);
			}
			//Kiểm tra 
			if(listUsers.size() > 0) {
				System.out.println("Đăng nhập thành công");
			}else {
				System.out.println("Đăng nhập thất bại");
			}
				
		} catch (Exception e) {
			System.out.println("Lỗi truyền query xuống database "+ e.getMessage());
			e.printStackTrace();
		}
		
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
}
