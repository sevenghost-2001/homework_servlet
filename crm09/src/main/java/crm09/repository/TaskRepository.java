package crm09.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import crm09.config.MysqlConfig;
import crm09.entity.Project;
import crm09.entity.Role;
import crm09.entity.Task;
import crm09.entity.User;

public class TaskRepository {
	public int save(String nameTask,LocalDate startTask,LocalDate endTask,String status,int id_project, int id_user) {
		int count = 0;
		String query = "INSERT INTO tasks(name_task,start_task,end_task,status,id_project,id_user) VALUES (?,?,?,?,?,?)";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, nameTask);
			preparedStatement.setDate(2, Date.valueOf(startTask));
			preparedStatement.setDate(3, Date.valueOf(endTask));
			preparedStatement.setString(4, status);
			preparedStatement.setInt(5, id_project);
			preparedStatement.setInt(6, id_user);
			count = preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Lỗi save: "+e.getLocalizedMessage());
		}
		return count;
	}
	
	public Task findId(int id) {
		Task task = new Task();
		String query = "SELECT *\r\n"
				+ "FROM tasks t\r\n"
				+ "JOIN users u ON t.id_user = u.id\r\n"
				+ "JOIN projects p ON t.id_project = p.id\r\n"
				+ "WHERE t.id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				task.setId(resultSet.getInt("id"));
				task.setNameTask(resultSet.getString("name_task"));
				task.setStartTask(resultSet.getDate("start_task").toLocalDate());
				task.setEndTask(resultSet.getDate("end_task").toLocalDate());
				task.setStatus(resultSet.getString("status"));
				User user = new User();
				user.setFullName(resultSet.getString("fullname"));
				task.setUser(user);
				Project project = new Project();
				project.setName(resultSet.getString("name"));
				task.setProject(project);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return task;
	}
	
	public void update(String nameTask,LocalDate startTask,LocalDate endTask,String status,int id_project,int id_user, int id) {
		String query = "UPDATE tasks SET name_task = ?, start_task = ?, end_task = ?, status = ?,id_project = ?,id_user = ? WHERE id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, nameTask);
			preparedStatement.setDate(2, Date.valueOf(startTask));
			preparedStatement.setDate(3, Date.valueOf(endTask));
			preparedStatement.setString(4, status);
			preparedStatement.setInt(5, id_project);
			preparedStatement.setInt(6, id_user);
			preparedStatement.setInt(7, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Lỗi Update: " + e.getLocalizedMessage());
		}
	}
	
	public List<Task> findAll(){
		List<Task> listTasks = new ArrayList<Task>();
		String query = "SELECT *\r\n"
				+ "FROM tasks t\r\n"
				+ "JOIN users u ON t.id_user = u.id\r\n"
				+ "JOIN projects p ON t.id_project = p.id";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Task task = new Task();
				task.setId(resultSet.getInt("id"));
				task.setNameTask(resultSet.getString("name_task"));
				task.setStartTask(resultSet.getDate("start_task").toLocalDate());
				task.setEndTask(resultSet.getDate("end_task").toLocalDate());
				task.setStatus(resultSet.getString("status"));
				User user = new User();
				user.setFullName(resultSet.getString("fullname"));
				task.setUser(user);
				Project project = new Project();
				project.setName(resultSet.getString("name"));
				task.setProject(project);
				listTasks.add(task);
			}
		} catch (Exception e) {
			System.out.println("Lỗi findAll: "+e.getLocalizedMessage());
		}
		return listTasks;
	}
	
	public void deleteById(int id) {
		String query = "DELETE FROM tasks WHERE id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Lỗi xóa: "+e.getLocalizedMessage());
		}
	}

	public List<Task> findByUser(User user){
		List<Task> tasks = new ArrayList<Task>();
			
		 String query = "SELECT t.*, u.fullname, p.name as project_name " +
                 "FROM tasks t " +
                 "JOIN users u ON t.id_user = u.id " +
                 "JOIN projects p ON t.id_project = p.id " +
                 "WHERE u.id = ?";
		 
		 Connection connection = MysqlConfig.getConnection();
		 try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, user.getId());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Task task = new Task();
				task.setId(resultSet.getInt("id"));
				task.setNameTask(resultSet.getString("name_task"));
				task.setStartTask(resultSet.getDate("start_task").toLocalDate());
				task.setEndTask(resultSet.getDate("end_task").toLocalDate());
				task.setStatus(resultSet.getString("status"));
				
				//Gán User
				task.setUser(user);
				
				//Gán Project
				Project project = new Project();
				project.setId(resultSet.getInt("id_project"));
				project.setName(resultSet.getString("project_name"));
				task.setProject(project);
				
				tasks.add(task);
			}
		} catch (Exception e) {
			System.out.println("Lỗi findByUser: " + e.getLocalizedMessage());
		}
		return tasks;
	}
}
