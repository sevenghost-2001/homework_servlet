package crm09.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm09.DTO.UserCreationRequest;
import crm09.entity.User;
import crm09.service.UserService;

@WebServlet(name = "usertableController", urlPatterns = {"/user"})
public class UserTableController extends HttpServlet{
	private UserService userService = new UserService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<UserCreationRequest> listUsers = userService.findAllUserDTOs();
		req.setAttribute("listUsers", listUsers);
		req.getRequestDispatcher("user-table.jsp").forward(req, resp);
	}
	
}
