package com.ufcg.psoft.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.User;
import com.ufcg.psoft.service.user.UserBean;

@Service
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserBean userService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user;
		try {
			user = userService.findUserByEmail(email);
			
			boolean matchPassword = passwordEncoder.matches(password, user.getSenha());
			
			if (matchPassword) {
	            return new UsernamePasswordAuthenticationToken(user.getId(), password);
	        }
			
			return null;
		} catch (Exception e) {
			throw new BadCredentialsException("Não existe usuário com esse email");
		}
    }
}
