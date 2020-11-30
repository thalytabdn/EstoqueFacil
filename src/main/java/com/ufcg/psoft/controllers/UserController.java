package com.ufcg.psoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.model.User;
import com.ufcg.psoft.model.DTO.UserDTO;
import com.ufcg.psoft.service.user.UserBean;
import com.ufcg.psoft.service.user.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api("API REST Users")
@CrossOrigin()
public class UserController {
	
	@Autowired
	UserService userService = new UserBean();
	
	@RequestMapping(value = "/user/admin", method = RequestMethod.POST)
	@ApiOperation(value="Criar usuario admin")
	public ResponseEntity<?> criarUserAdmin(@RequestBody UserDTO userDTO) {
		
		String nome = userDTO.getNome();
		String sobrenome = userDTO.getSobrenome();
		String senha = userDTO.getSenha();
		String email = userDTO.getEmail();
		
		User user = new User(nome, sobrenome, senha, email);
		
		User userCreated = this.userService.createAdmin(user);
		
		UserDTO resposta = new UserDTO();
		resposta.setId(userCreated.getId());
		resposta.setNome(userCreated.getNome());
		resposta.setSobrenome(userCreated.getSobrenome());
		resposta.setEmail(userCreated.getEmail());
		resposta.setCargoUser(userCreated.getCargoSistema().getUserRole().toString());
		

		return new ResponseEntity<>(resposta, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/public/user/cliente", method = RequestMethod.POST)
	@ApiOperation(value="Criar cliente")
	public ResponseEntity<?> criarUserCliente(@RequestBody UserDTO userDTO) {
		
		String nome = userDTO.getNome();
		String sobrenome = userDTO.getSobrenome();
		String senha = userDTO.getSenha();
		String email = userDTO.getEmail();
		
		User user = new User(nome, sobrenome, senha, email);
		
		User userCreated = this.userService.createCliente(user);
		
		UserDTO resposta = new UserDTO();
		resposta.setId(userCreated.getId());
		resposta.setNome(userCreated.getNome());
		resposta.setSobrenome(userCreated.getSobrenome());
		resposta.setEmail(userCreated.getEmail());
		resposta.setCargoUser(userCreated.getCargoSistema().getUserRole().toString());
		

		return new ResponseEntity<>(resposta, HttpStatus.CREATED);
	}

}