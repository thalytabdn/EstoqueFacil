package com.ufcg.psoft.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Admin;
import com.ufcg.psoft.model.CargoSistema;
import com.ufcg.psoft.model.Cliente;
import com.ufcg.psoft.model.User;
import com.ufcg.psoft.repositories.UserRepository;

@Service("userService")
public class UserBean implements UserService {

    @Autowired
    private static UserRepository userDAO;

    @Override
    public List<User> findAllUsers() {
        return this.userDAO.findAll();
    }

    @Override
    public User findUserById(long id) throws Exception {
        try {
            return this.userDAO.findById(id).get();
        } catch (Exception e) {
            throw new Exception("Usuário de id: " + id + " não encontrado.");
        }
    }

    @Override
    public void updateUser(User user) throws Exception {
        long id = user.getId();

        if (this.userDAO.existsById(id)) {
            this.userDAO.save(user);
        } else {
            throw new Exception(
                    "Erro ao atualizar usuário: usuário com id: " + id + ", não está contido no banco de dados");
        }
    }

    @Override
    public void deleteUserById(long id) throws Exception {
        if (this.userDAO.existsById(id)) {
            this.userDAO.deleteById(id);
        } else {
            throw new Exception(
                    "Erro ao deletar usuário: usuário com id: " + id + ", não está contido no banco de dados");
        }
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        try {
            return this.userDAO.findByEmail(email).get();
        } catch (Exception e) {
            throw new Exception("Usuário de email: " + email + " não encontrado.");
        }
    }

    @Bean
    static
    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public User createAdmin(User user) {
        CargoSistema cargo = new Admin();
        user.setCargoSistema(cargo);
        cargo.setUser(user);

        user.setSenha(getEncoder().encode(user.getSenha()));
        return this.userDAO.save(user);
    }

    @Override
    public User createCliente(User user) {

        CargoSistema cargo = new Cliente();
        user.setCargoSistema(cargo);
        cargo.setUser(user);

        user.setSenha(getEncoder().encode(user.getSenha()));
        return this.userDAO.save(user);
    }

    @Override
    public boolean validaSenhaUsuario(User user) {
        Optional<User> optUser = userDAO.findByEmail(user.getEmail());
        if (optUser.isPresent() && optUser.get().getSenha().equals(user.getSenha())) {
            return true;
        }

        return false;
    }
}