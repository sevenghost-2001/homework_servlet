package crm09.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import crm09.entity.Project;
import crm09.entity.StatusProject;
import crm09.entity.User;
import crm09.repository.ProjectRepository;

public class ProjectService {
	private ProjectRepository projectRepository = new ProjectRepository();
	
	public boolean insertProject(String name, LocalDate start_day, LocalDate end_day) {
		return projectRepository.save(name, start_day, end_day) > 0;
	}
	
	public Project findById(int id) {
		return projectRepository.findId(id);
	}
	
	public void updateProject(String name, LocalDate start_day, LocalDate end_day, int id) {
		projectRepository.update(name, start_day, end_day, id);
	}
	
	public List<Project>findAllProjects(){
		return projectRepository.findAll();
	}
	
	public void deleteById(int id) {
		projectRepository.deleteById(id);
	}
}
