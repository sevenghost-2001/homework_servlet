package crm09.service;

import java.util.ArrayList;
import java.util.List;

import crm09.DTO.UserCreationRequest;
import crm09.entity.Role;
import crm09.entity.User;
import crm09.repository.RoleRepository;
import crm09.repository.UserRepository;

public class UserService {
	private RoleRepository roleRepository = new RoleRepository();
	private UserRepository userRepository = new UserRepository();
	public List<Role> getAllRoles(){
		return roleRepository.findAll();
	}
	
	public boolean insertUser(String email, String password, int roleId, String fullName, String phone) {
		return userRepository.save(email, password, roleId, fullName, phone) > 0;
	}
	
	public List<UserCreationRequest> findAllUserDTOs() {
	    List<UserCreationRequest> dtos = new ArrayList<>();
	    for (User user : userRepository.findAll()) {
	    	UserCreationRequest dto = new UserCreationRequest();
	        dto.setId(user.getId());
	        dto.setEmail(user.getEmail());
	        if (user.getRoles() != null) {
	            dto.setRoleName(user.getRoles().getName());
	        }
	        dtos.add(dto);
	    }
	    return dtos;
	}
}
