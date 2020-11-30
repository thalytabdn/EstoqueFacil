package com.ufcg.psoft.controllers;

import com.ufcg.psoft.model.User;
import com.ufcg.psoft.model.DTO.UserDTO;
import com.ufcg.psoft.service.user.UserBean;
import com.ufcg.psoft.util.CustomErrorType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {
    
    @Autowired
    private UserBean userBean;

    public UserController(UserBean userBean){
        super();
		this.userBean = userBean;
	}


    // -------------------Criar Usuario-------------------
    @PostMapping("/user")
    public ResponseEntity<?> criaUser(@RequestBody User user){

        if (userBean.doesUserExist(user)){
            return new ResponseEntity(new CustomErrorType("Email ja cadastrado!"), HttpStatus.CONFLICT);
        }

        UserDTO userDTO = new UserDTO(user);
        this.userBean.createUser(user);

        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
    }

    // -------------------Consulta Usuario-------------------
    @GetMapping("/user/{email}")
    public ResponseEntity<?> consultaUsuario(@PathVariable("email") String email) {
        
		try {
            User userAux = userBean.findByEmail(email);
            UserDTO userDTO = new UserDTO(userAux);
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("User with email " + email + " was not found"),
				HttpStatus.NOT_FOUND);
		}
    }
}