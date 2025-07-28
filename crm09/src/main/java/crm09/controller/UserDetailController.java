package crm09.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm09.entity.Task;
import crm09.entity.User;
import crm09.service.TaskService;
import crm09.service.UserService;

@WebServlet(name = "userDetailController",urlPatterns = {"/userDetail"})
public class UserDetailController extends HttpServlet{
	private UserService userService = new UserService();
	private TaskService taskService = new TaskService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int userId = Integer.parseInt(req.getParameter("id"));
		User user = userService.findById(userId);
		List<Task> tasks = taskService.getTasksByUser(user);
		
		
		//Tính phần trăm theo trạng thái
		int total = tasks.size();
        int doing = (int) tasks.stream().filter(t -> "ĐANG THỰC HIỆN".equals(t.getStatus())).count();
        int notStarted = (int) tasks.stream().filter(t -> "CHƯA BẮT ĐẦU".equals(t.getStatus())).count();
        int done = (int) tasks.stream().filter(t -> "HOÀN THÀNH".equals(t.getStatus())).count();

        int percentDoing = total > 0 ? doing * 100 / total : 0;
        int percentNotStarted = total > 0 ? notStarted * 100 / total : 0;
        int percentDone = total > 0 ? done * 100 / total : 0;
		
		req.setAttribute("user", user);
		req.setAttribute("tasks", tasks);
		req.setAttribute("percentDoing", percentDoing);
        req.setAttribute("percentNotStarted", percentNotStarted);
        req.setAttribute("percentDone", percentDone);
		
		req.getRequestDispatcher("/user-details.jsp").forward(req, resp);
	}
}
