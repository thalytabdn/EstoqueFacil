package com.ufcg.psoft.security;

import java.util.Date;
import java.util.Optional;

import com.ufcg.psoft.model.User;
import com.ufcg.psoft.model.DTO.UserLoginDTO;
import com.ufcg.psoft.repositories.UserRepository;
import com.ufcg.psoft.service.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenService {

        @Autowired
        private UserRepository userDAO;
        
        private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public String getUserEmailFromJWTToken(String token) throws Exception {
	
        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

        Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token.replace("Bearer ", ""));

        String username = parsedToken
                .getBody()
                .getSubject();

        return username;
        }
        
        public static final String TOKEN_KEY = "p3swhJQ2rzLbwPIdfmSr2w";

        public LoginResponse autentica(UserLoginDTO user) {
                String msgErro = "Email e/ou senha invalido(s). Login nao realizado";
                
                Optional<User> optUsuario = userDAO.findByEmail(user.getEmail());
 
                boolean isPasswordMatch = passwordEncoder.matches(user.getSenha(), optUsuario.get().getSenha());

		if (optUsuario.isPresent() && isPasswordMatch ){
                        return new LoginResponse(geraToken(user));
                }

		return new LoginResponse(msgErro);

	}

        private String geraToken(UserLoginDTO user) {
                String token = Jwts.builder().setSubject(user.getEmail()).signWith(SignatureAlgorithm.HS256, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 1000)).compact();
		return token;
	}

}
