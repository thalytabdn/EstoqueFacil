package com.ufcg.psoft.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.ufcg.psoft.enumeration.UserRoleName;

@Entity
@DiscriminatorValue(value = "CLIENTE")
public class Cliente extends CargoSistema {
	

	public Cliente() {
		super();
	}
	
	@Override
	public UserRoleName getUserRole() {
		return UserRoleName.CLIENTE;
	}
}
