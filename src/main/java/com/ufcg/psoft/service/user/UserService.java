package com.ufcg.psoft.service.user;

import java.util.List;

import com.ufcg.psoft.model.User;

public interface UserService {
	
	List<User> findAllUsers();

	User findUserById(long id) throws Exception;
	
	User findUserByEmail(String email) throws Exception;

	void updateUser(User user) throws Exception;

	void deleteUserById(long id) throws Exception;
	
	User createAdmin(User user);
	
	User createCliente(User user);

	boolean validaSenhaUsuario(User user);
}