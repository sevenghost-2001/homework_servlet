package crm09.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import crm09.config.MysqlConfig;
import crm09.entity.Project;
import crm09.entity.Role;
import crm09.entity.StatusProject;
import crm09.entity.User;
import crm09.utils.MD5Helper;

public class ProjectRepository {
	public int save(String name, LocalDate start_day, LocalDate end_day){
		int count = 0;
		String query = "INSERT INTO projects(name, start_day,end_day) VALUES (?,?,?)";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,name);
			preparedStatement.setDate(2, Date.valueOf(start_day));
			preparedStatement.setDate(3, Date.valueOf(end_day)); 
//			preparedStatement.setString(4, status);
			count =  preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println("lỗi save: " + e.getLocalizedMessage());
		}
		return count;
	}
	public Project findId(int id) {
		Project project = new Project();
		String query = "SELECT * FROM projects WHERE id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				project.setId(resultSet.getInt("id"));
				project.setName(resultSet.getString("name"));
				project.setStartDay(resultSet.getDate("start_day").toLocalDate()); 
		        project.setEndDay(resultSet.getDate("end_day").toLocalDate());  
//		        project.setStatus(StatusProject.valueOf(resultSet.getString("status")));
			}
		} catch (Exception e) {
			System.out.println("lỗi find id: "+e.getLocalizedMessage());
		}
		return project;
	}
	
	public void update(String name, LocalDate start_day, LocalDate end_day,int id) {
		String query_edit = "UPDATE projects SET name = ?, start_day = ?, end_day = ? WHERE id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query_edit);
			preparedStatement.setString(1, name);
			preparedStatement.setDate(2, Date.valueOf(start_day));
			preparedStatement.setDate(3, Date.valueOf(end_day));
			preparedStatement.setInt(4, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Lỗi Update: "+ e.getLocalizedMessage());
		}
	}
	
	public List<Project> findAll(){
		List<Project> listProjects = new ArrayList<Project>();
		String query = "SELECT * FROM projects";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Project project = new Project();
				project.setId(resultSet.getInt("id"));
				project.setName(resultSet.getString("name"));
				project.setStartDay(resultSet.getDate("start_day").toLocalDate()); 
		        project.setEndDay(resultSet.getDate("end_day").toLocalDate());  
//		        project.setStatus(StatusProject.valueOf(resultSet.getString("status")));
		        listProjects.add(project);
			}
		} catch (Exception e) {
			System.out.println("Lỗi findAll: "+ e.getLocalizedMessage());
		}
		return listProjects;
	}
	
	public void deleteById(int id) {
		String query = "DELETE FROM projects WHERE id = ?";
        Connection connection = MysqlConfig.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa: " + e.getMessage());
        }
	}
}
