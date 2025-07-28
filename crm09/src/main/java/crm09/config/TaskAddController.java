package crm09.config;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm09.entity.Project;
import crm09.entity.User;
import crm09.service.ProjectService;
import crm09.service.TaskService;
import crm09.service.UserService;

@WebServlet(name = "taskAddController",urlPatterns = {"/task-add"})
public class TaskAddController extends HttpServlet {
	private TaskService taskService = new TaskService();
	private UserService userService = new UserService();
	private ProjectService projectService = new ProjectService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<User> listUsers = userService.findAllUsers();
		List<Project> listProjects = projectService.findAllProjects();
		req.setAttribute("listUsers", listUsers);
		req.setAttribute("listProjects", listProjects);
		req.getRequestDispatcher("task-add.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String idString = req.getParameter("id");
		String nameTask = req.getParameter("name");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startTask =  LocalDate.parse(req.getParameter("start_task"),formatter);
		LocalDate endTask = LocalDate.parse(req.getParameter("end_task"),formatter);
//		String status = req.getParameter("status");
		String status = "Đang thực hiện";
		int id_project = Integer.parseInt(req.getParameter("id_project"));
		int id_user = Integer.parseInt(req.getParameter("id_user"));
		boolean isSuccess = taskService.insertTask(nameTask, startTask, endTask, status,id_project,id_user);
		resp.sendRedirect("task");
	}
}
