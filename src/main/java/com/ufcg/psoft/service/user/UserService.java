package com.ufcg.psoft.service.user;

import com.ufcg.psoft.model.User;

public interface UserService {

    boolean doesUserExist(User user);

    boolean validaSenhaUsuario(User user);

    User createUser(User user);

    User findByEmail(String email);

}
