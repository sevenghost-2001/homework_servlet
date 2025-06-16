package crm09.controller;

import java.io.Console;
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

import com.mysql.cj.x.protobuf.MysqlxCrud.Insert;

import crm09.config.MysqlConfig;
import crm09.entity.Role;

@WebServlet(name = "roleController", urlPatterns = {"/role"})
public class RoleController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if("edit".equals(action)) {
			int id = Integer.parseInt(req.getParameter("id"));
			
			String query = "SELECT * FROM roles WHERE id = ?";
			 Connection connection = MysqlConfig.getConnection();
			 Role role = new Role();
			 try {
				PreparedStatement preparedStatement =  connection.prepareStatement(query);
				preparedStatement.setInt(1, id);
				ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					role.setId(resultSet.getInt("id"));
					role.setName(resultSet.getString("name"));
					role.setDescription(resultSet.getString("description"));
				}
			} catch (Exception e) {
				System.out.println("Lỗi khi lấy dữ liệu để sửa: " + e.getMessage());
			}
			 req.setAttribute("role", role);
		     req.getRequestDispatcher("role-edit.jsp").forward(req, resp);
		}
		else if ("delete".equals(action)) {
			int id = Integer.parseInt(req.getParameter("id"));
			String query = "DELETE FROM roles WHERE id = ?";
	        Connection connection = MysqlConfig.getConnection();
	        try {
	            PreparedStatement ps = connection.prepareStatement(query);
	            ps.setInt(1, id);
	            ps.executeUpdate();
	        } catch (Exception e) {
	            System.out.println("Lỗi khi xóa: " + e.getMessage());
	        }
	        resp.sendRedirect("role");
		}
		else {
			// Chuẩn bị câu truy vấn
			List<Role> listRoles = new ArrayList<Role>();
			String query = "SELECT * FROM roles";
			// Mở kết nối cơ sở dữ liệu
			Connection connection = MysqlConfig.getConnection();
			try {
				//Truyền query vào connection
				PreparedStatement preparedStatement =  connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Role role = new Role();
					role.setId(resultSet.getInt("id"));
					role.setName(resultSet.getString("name"));
					role.setDescription(resultSet.getString("description"));
					listRoles.add(role);
				}
			} catch (Exception e) {
				System.out.println("Xuất hiện lỗi trong khi query "+e.getMessage());
			}
			req.setAttribute("listRoles", listRoles);
			req.getRequestDispatcher("role-table.jsp").forward(req, resp);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String name = req.getParameter("role_name");
		String description = req.getParameter("description");
		//Nếu có id tức là thực hiện update
		String idString = req.getParameter("id");
		
		String query = "INSERT INTO roles(name,description) VALUES(?,?)" ;
		String query_edit = "UPDATE roles SET name = ?, description = ? WHERE id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			if(idString != null && !idString.isEmpty()) {
				//Update
				int id = Integer.parseInt(idString);
				PreparedStatement preparedStatement = connection.prepareStatement(query_edit);
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, description);
				preparedStatement.setInt(3, id);
				preparedStatement.executeUpdate();
				
			}
			else {
				//Thêm mới
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, description);
				//gọi lệnh cập nhật dữ liệu 
				preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println("Xuất hiện lỗi trong khi query "+e.getMessage());
		}
		resp.sendRedirect("role");
	}
}
