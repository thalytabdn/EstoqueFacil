package com.ufcg.psoft.security;

public class ConstantesSeguranca {
    public static final String AUTH_LOGIN_URL = "/auth/login";

    public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";

    private ConstantesSeguranca() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}