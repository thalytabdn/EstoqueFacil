package com.ufcg.psoft.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ufcg.psoft.model.User;
import com.ufcg.psoft.security.ConstantesSeguranca;
import com.ufcg.psoft.service.user.UserBean;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserBean userBean;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        if (userBean == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            userBean = webApplicationContext.getBean(UserBean.class);
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(ConstantesSeguranca.TOKEN_HEADER);
        if (token != null && !token.trim().isEmpty() && token.startsWith(ConstantesSeguranca.TOKEN_PREFIX)) {
            try {
                byte[] signingKey = ConstantesSeguranca.JWT_SECRET.getBytes();

                Jws<Claims> parsedToken = Jwts.parser()
                        .setSigningKey(signingKey)
                        .parseClaimsJws(token.replace("Bearer ", ""));

                String username = parsedToken
                        .getBody()
                        .getSubject();

                if (username != null && !username.trim().isEmpty()) {
                    User user = userBean.findByEmail(username);
                    if (user != null)
                        return new UsernamePasswordAuthenticationToken(user, null, null);
                    else
                        throw new Exception("Usuário não cadastrado");
                }
            } catch (Exception exception) {
                System.err.println(exception.getMessage());

            }

        }
        return null;
    }
}