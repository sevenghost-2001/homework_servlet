package crm09.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm09.entity.Project;
import crm09.entity.StatusProject;
import crm09.service.ProjectService;

@WebServlet(name = "projectController", urlPatterns = {"/project"})
public class ProjectController extends HttpServlet{
	private ProjectService projectService = new ProjectService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if("edit".equals(action)) {
			int id = Integer.parseInt(req.getParameter("id"));
			Project project = projectService.findById(id);
			req.setAttribute("project", project);
			req.getRequestDispatcher("groupwork-edit.jsp").forward(req, resp);
		}else if ("delete".equals(action)) {
			int id = Integer.parseInt(req.getParameter("id"));
			projectService.deleteById(id);
			resp.sendRedirect("project");
		}else {
			List<Project> listProjects = projectService.findAllProjects();
			req.setAttribute("listProjects", listProjects);
			req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String idString = req.getParameter("id");
		String name = req.getParameter("name");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDay = LocalDate.parse(req.getParameter("start_day"), formatter);
		LocalDate endDay = LocalDate.parse(req.getParameter("end_day"), formatter);
		
		if(idString != null && !idString.isEmpty()) {
			int id = Integer.parseInt(idString);
			projectService.updateProject(name,startDay,endDay,id);
		}else {
			projectService.insertProject(name, startDay, endDay);
		}
		
		
	}
}
