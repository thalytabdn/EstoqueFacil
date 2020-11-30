package com.ufcg.psoft.service.user;

import java.util.Optional;

import com.ufcg.psoft.model.User;
import com.ufcg.psoft.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBean implements UserService{

    @Autowired
    private UserRepository<User, String> usersDAO;

    @Override
    public User createUser(User user){
        return this.usersDAO.save(user);
    }

    @Override
    public User findByEmail(String email){
        Optional<User> optUser = usersDAO.findByEmail(email);

        if(optUser.isEmpty()){
            throw new IllegalArgumentException(); //usuario nao existe
        }

        return optUser.get();
    }

    @Override
    public boolean validaSenhaUsuario(User user){
        Optional<User> optUser = usersDAO.findByEmail(user.getEmail());
        if (optUser.isPresent() && optUser.get().getPassword().equals(user.getPassword())){
            return true;
        }

        return false;
    }

    @Override
	public boolean doesUserExist(User user) {
		return usersDAO.existsById(user.getEmail());
	}
}
