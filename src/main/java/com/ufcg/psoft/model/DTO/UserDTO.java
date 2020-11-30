package com.ufcg.psoft.model.DTO;

import javax.persistence.Column;

public class UserDTO {
	
private String nome;

	private Long id;
	
	private String sobrenome;
	
	private String senha;
	
	@Column(unique = true)
	private String email;
	
	private String cargoUser;
	
	public UserDTO() {}
	
	
	public UserDTO(String nome, Long id, String sobrenome, String senha, String email, String cargoUser) {
		super();
		this.nome = nome;
		this.id = id;
		this.sobrenome = sobrenome;
		this.senha = senha;
		this.email = email;
		this.cargoUser = cargoUser;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getCargoUser() {
		return cargoUser;
	}


	public void setCargoUser(String cargoUser) {
		this.cargoUser = cargoUser;
	}
}