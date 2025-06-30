package crm09.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm09.config.MysqlConfig;
import crm09.entity.Role;
import crm09.entity.User;
import crm09.utils.MD5Helper;

@WebServlet(name = "loginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie[] cookies;
		String username = " ";
		String password = " "; 
		
		if(req.getCookies() != null) {
			cookies = req.getCookies();
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				String value = cookie.getValue(); 
				System.out.println(name + " - " + value);
				if(name.equals("sUserName")) {
					username = value;
				}
				if(name.equals("sPassword")) {
					password = value;
				}
			}
			req.setAttribute("email", username);
			req.setAttribute("password", password);
		}
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String emailString = req.getParameter("email");
		String passwordString = req.getParameter("password");
		String roleString = req.getParameter("role");
		
		//Chuẩn bị câu truy vấn
		String query = "SELECT * \r\n"
				+ "FROM users u\r\n"
				+ "JOIN roles r ON r.id = u.id_role\r\n"
				+ "WHERE u.email = ? AND u.password = ?" ;
		
		//Bước 3: Mở kết nối cơ sở dữ liệu
		Connection connection = MysqlConfig.getConnection();
		
		//Bước 4: Truyền câu query của mình vào connection mới vừa mở để truyền xuống cơ sở dữ liệu
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			//Truyền tham số vào dấu chấm hỏi tham số
			preparedStatement.setString(1, emailString);
			preparedStatement.setString(2, MD5Helper.getMd5(passwordString));
			
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
				Role role = new Role();
				role.setName(resultSet.getString("name"));
				user.setRoles(role);
				listUsers.add(user);
			}
			//Kiểm tra 
			if(listUsers.size() > 0) {
				//Tạo cookie
				Cookie cookie = new Cookie("sUserName",emailString);
				cookie.setMaxAge(5*60);// tồn tại trong 5 giây
				Cookie cookiePassword = new Cookie("sPassword",passwordString);
				
				String roleNameString = listUsers.get(0).getRoles().getName();
				Cookie cookieRole = new Cookie("sRole", roleNameString);
				
				resp.addCookie(cookie);
				resp.addCookie(cookiePassword);
				resp.addCookie(cookieRole);
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
