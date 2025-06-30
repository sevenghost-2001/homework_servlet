package crm09.controller;

import java.io.IOException;
import java.security.PrivateKey;
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
import crm09.entity.Role;
import crm09.repository.RoleRepository;
import crm09.service.UserService;

@WebServlet(name = "userController", urlPatterns = {"/user-add"})
public class UserController extends HttpServlet{
	private UserService userService = new UserService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Role> listRoles = userService.getAllRoles();
		req.setAttribute("listRoles", listRoles);
		req.getRequestDispatcher("user-add.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		String fullname = req.getParameter("fullname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String phone = req.getParameter("phone");
		int roleId =Integer.parseInt(req.getParameter("roleId")) ;
		
		boolean isSuccess =  userService.insertUser(email, password, roleId, fullname, phone);
		
		resp.sendRedirect("user");
	}
}

