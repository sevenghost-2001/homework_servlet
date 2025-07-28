package crm09.service;
import java.time.LocalDate;
import java.util.List;

import crm09.entity.Task;
import crm09.entity.User;
import crm09.repository.TaskRepository;

public class TaskService {
	private TaskRepository taskRepository = new TaskRepository();
	
	public boolean insertTask(String name_task,LocalDate start_task,LocalDate end_task,String status,int id_project, int id_user) {
		return taskRepository.save(name_task, start_task, end_task, status,id_project,id_user) > 0;
	}
	
	public Task findById(int id) {
		return taskRepository.findId(id);
	}
	
	public void updateTask(String name_task,LocalDate start_task, LocalDate end_task,String status,int id_project,int id_user,int id) {
		taskRepository.update(name_task, start_task, end_task, status,id_project,id_user, id);
	}
	
	public List<Task>findAllTasks(){
		return taskRepository.findAll();
	}
	
	public void deleteById(int id) {
		taskRepository.deleteById(id);
	}
	
	public List<Task> getTasksByUser(User user){
		return taskRepository.findByUser(user);
	}

}
