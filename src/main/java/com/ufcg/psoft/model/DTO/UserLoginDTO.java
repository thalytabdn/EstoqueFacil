package com.ufcg.psoft.model.DTO;

public class UserLoginDTO {
	
	private String email;
	
	private String senha;
	
	public UserLoginDTO() {}

	public UserLoginDTO(String email, String senha) {
		super();
		this.email = email;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
