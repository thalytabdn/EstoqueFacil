package com.ufcg.psoft.security;

import com.ufcg.psoft.model.User;
import com.ufcg.psoft.service.user.UserBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserBean userBean;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userBean.findByEmail(email);

        if (user == null) {
            try {
                throw new Exception("Nao existe usuario com esse email");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        if (user.getPassword().equals(password)) {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
        return null;
    }
    
    // public User findUserByToken(String token){

    // }
}
