package crm09.entity;

import java.time.LocalDate;

public class Task {
	int id;
	String nameTask;
	LocalDate startTask;
	LocalDate endTask;
	String status;
	Project project;
	User user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNameTask() {
		return nameTask;
	}
	public void setNameTask(String nameTask) {
		this.nameTask = nameTask;
	}
	public LocalDate getStartTask() {
		return startTask;
	}
	public void setStartTask(LocalDate startTask) {
		this.startTask = startTask;
	}
	public LocalDate getEndTask() {
		return endTask;
	}
	public void setEndTask(LocalDate endTask) {
		this.endTask = endTask;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
