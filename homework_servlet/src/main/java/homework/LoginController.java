package homework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "loginController" ,urlPatterns = {"/login"})
public class LoginController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String usernameString = req.getParameter("name");
		String passwordString = req.getParameter("password");
		if("admin".equals(usernameString) && "123456".equals(passwordString)) {
			req.getRequestDispatcher("trangchu.jsp").forward(req, resp);
		}else {
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
		System.out.println(usernameString + " - " + passwordString);
	}
}
