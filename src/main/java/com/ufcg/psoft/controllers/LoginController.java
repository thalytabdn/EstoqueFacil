package com.ufcg.psoft.controllers;

import com.ufcg.psoft.model.DTO.UserLoginDTO;
import com.ufcg.psoft.security.JwtTokenService;
import com.ufcg.psoft.service.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoginController {

    @Autowired
    private JwtTokenService JWTService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> autentica(@RequestBody UserLoginDTO user) {
        return new ResponseEntity<LoginResponse>(JWTService.autentica(user), HttpStatus.OK);
    };
}