package crm09.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm09.DTO.UserCreationRequest;
import crm09.entity.Role;
import crm09.entity.User;
import crm09.service.UserService;

@WebServlet(name = "usertableController", urlPatterns = {"/user"})
public class UserTableController extends HttpServlet{
	private UserService userService = new UserService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if("edit".equals(action)) {
			List<Role> listRoles = userService.getAllRoles();
			int id = Integer.parseInt(req.getParameter("id"));
			User user = userService.findById(id);
			req.setAttribute("user", user);
			req.setAttribute("listRoles", listRoles);
			req.getRequestDispatcher("user-edit.jsp").forward(req, resp);
		}else if ("delete".equals(action)) {
			int id = Integer.parseInt(req.getParameter("id"));
			userService.deleteById(id);
			resp.sendRedirect("user");
		}
		else {
//			List<UserCreationRequest> listUsers = userService.findAllUserDTOs();
			List<User> listUsers = userService.findAllUsers();
			req.setAttribute("listUsers", listUsers);
			req.getRequestDispatcher("user-table.jsp").forward(req, resp);
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String email = req.getParameter("email");
		int id_role =Integer.parseInt(req.getParameter("roleId"));//role_id
		int id = Integer.parseInt(req.getParameter("id"));
		userService.updateUser(email, id_role, id);	
	}
	
}
