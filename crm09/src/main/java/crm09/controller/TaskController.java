package crm09.controller;

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
import crm09.entity.Task;
import crm09.entity.User;
import crm09.service.ProjectService;
import crm09.service.TaskService;
import crm09.service.UserService;

@WebServlet(name = "taskController",urlPatterns = {"/task"})
public class TaskController extends HttpServlet{
private TaskService taskService = new TaskService();
private UserService userService = new UserService();
private ProjectService projectService = new ProjectService();
@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if("edit".equals(action)) {
			List<User> listUsers = userService.findAllUsers();
			List<Project> listProjects = projectService.findAllProjects();
			int id = Integer.parseInt(req.getParameter("id"));
			Task task = taskService.findById(id);
			req.setAttribute("listUsers", listUsers);
			req.setAttribute("listProjects", listProjects);
			req.setAttribute("task", task);
			req.getRequestDispatcher("task-edit.jsp").forward(req, resp);
		}
		else if ("delete".equals(action)) {
			int id = Integer.parseInt(req.getParameter("id"));
			taskService.deleteById(id);
			resp.sendRedirect("task");
		}else {
			List<Task> lisTasks = taskService.findAllTasks();
			req.setAttribute("listTasks", lisTasks);
			req.getRequestDispatcher("task.jsp").forward(req, resp);
		}
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
//			String status = req.getParameter("status");
			String status = "Đang thực hiện";
			int id_project = Integer.parseInt(req.getParameter("id_project"));
			int id_user = Integer.parseInt(req.getParameter("id_user"));
			
			int id = Integer.parseInt(idString);
			taskService.updateTask(nameTask, startTask, endTask,status,id_project,id_user,id);

		}
}
