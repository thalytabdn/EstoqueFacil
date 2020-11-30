package com.ufcg.psoft.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.model.User;
import com.ufcg.psoft.model.DTO.TokenDTO;
import com.ufcg.psoft.security.ConstantesSeguranca;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl(ConstantesSeguranca.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = "";
        String password = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(request.getInputStream(), User.class);
            username = user.getEmail();
            password = user.getPassword();
        } catch (Exception e) {

        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {
        String user = (String) authentication.getPrincipal();

        byte[] signingKey = ConstantesSeguranca.JWT_SECRET.getBytes();

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", ConstantesSeguranca.TOKEN_TYPE)
                .setIssuer(ConstantesSeguranca.TOKEN_ISSUER)
                .setAudience(ConstantesSeguranca.TOKEN_AUDIENCE)
                .setSubject(user)
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .compact();

        response.addHeader(ConstantesSeguranca.TOKEN_HEADER, ConstantesSeguranca.TOKEN_PREFIX + token);
        try {
            TokenDTO tokenDTO = new TokenDTO(ConstantesSeguranca.TOKEN_PREFIX + token);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(tokenDTO);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception e){

        }
    }
}
