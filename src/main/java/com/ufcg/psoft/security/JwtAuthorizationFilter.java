package com.ufcg.psoft.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ufcg.psoft.model.User;
import com.ufcg.psoft.enumeration.UserRoleName;
import com.ufcg.psoft.service.user.UserBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserBean userService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        if (userService == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            userService = webApplicationContext.getBean(UserBean.class);
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
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (token != null && !token.trim().isEmpty() && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            try {
                byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

                Jws<Claims> parsedToken = Jwts.parser()
                        .setSigningKey(signingKey)
                        .parseClaimsJws(token.replace("Bearer ", ""));

                String idString = parsedToken
                        .getBody()
                        .getSubject();
                
                if (idString != null && !idString.trim().isEmpty()) {
                    long id = Long.parseLong(idString);

                	User user = userService.findUserById(id);
                	
                	UserRoleName rn = user.getCargoSistema().getUserRole();
                	List<GrantedAuthority> a = new ArrayList<GrantedAuthority>();
                	
                    GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rn.toString());
                    a.add(authority);
               

                    if (user != null)
                        return new UsernamePasswordAuthenticationToken(user, null, a);
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

