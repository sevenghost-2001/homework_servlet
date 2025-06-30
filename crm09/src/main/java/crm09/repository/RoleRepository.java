package crm09.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import crm09.config.MysqlConfig;
import crm09.entity.Role;

public class RoleRepository {
	public List<Role> findAll(){
		List<Role> listRoles = new ArrayList<Role>();
		
		String query = "SELECT * FROM roles";
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Role role = new Role();
				role.setId(resultSet.getInt("id"));
				role.setName(resultSet.getString("name"));
				role.setDescription(resultSet.getString("description"));
				listRoles.add(role);
			}
		} catch (Exception e) {
			System.out.println("lỗi findAll" + e.getLocalizedMessage());
		}
		return listRoles;
	}
}
