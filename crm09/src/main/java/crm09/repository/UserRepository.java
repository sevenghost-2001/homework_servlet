package crm09.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxCrud.Update;

import crm09.config.MysqlConfig;
import crm09.entity.Role;
import crm09.entity.User;
import crm09.utils.MD5Helper;

public class UserRepository {
	public int save(String email, String password, int roleId, String fullName, String phone){
		int count = 0;
		String query = "INSERT INTO users(email, password,id_role,fullname,phone) VALUES (?,?,?,?,?)";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,email);
			preparedStatement.setString(2, MD5Helper.getMd5(password));
			preparedStatement.setInt(3,roleId);
			preparedStatement.setString(4, fullName);
			preparedStatement.setString(5, phone);
			count =  preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println("lỗi save: " + e.getLocalizedMessage());
		}
		return count;
	}
	public User findId(int id) {
		User user = new User();
		String query = "SELECT * \r\n"
				+ "FROM users u \r\n"
				+ "JOIN roles r ON r.id = u.id_role\r\n"
				+ "WHERE u.id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				user.setId(resultSet.getInt("id"));
				user.setFullName(resultSet.getString("fullname"));
				user.setEmail(resultSet.getString("email"));
				Role role = new Role();
				role.setName(resultSet.getString("name"));
				user.setRoles(role);
			}
		} catch (Exception e) {
			System.out.println("lỗi find id: "+e.getLocalizedMessage());
		}
		return user;
	}
	
	public void update(String email,int id_role,int id) {
		String query_edit = "UPDATE users SET email = ?, id_role = ? WHERE id = ?";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query_edit);
			preparedStatement.setString(1, email);
			preparedStatement.setInt(2, id_role);
			preparedStatement.setInt(3, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Lỗi Update; "+ e.getLocalizedMessage());
		}
	}
	
	public List<User> findAll(){
		List<User> listUsers = new ArrayList<User>();
		String query = "SELECT * \r\n"
				+ "FROM users u \r\n"
				+ "JOIN roles r ON r.id = u.id_role";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setEmail(resultSet.getString("email"));
				user.setFullName(resultSet.getString("fullname"));
				Role role = new Role();
				role.setName(resultSet.getString("name"));
				user.setRoles(role);
				listUsers.add(user);
			}
		} catch (Exception e) {
			System.out.println("Lỗi findAll: "+ e.getLocalizedMessage());
		}
		return listUsers;
	}
	
	public void deleteById(int id) {
		String query = "DELETE FROM users WHERE id = ?";
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
