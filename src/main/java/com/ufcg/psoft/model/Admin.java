package com.ufcg.psoft.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import com.ufcg.psoft.enumeration.UserRoleName;

@Entity
@DiscriminatorValue(value = "ADMIN")
public class Admin extends CargoSistema {
	
	public Admin() {
		super();
	}

	@Override
	public UserRoleName getUserRole() {
		return UserRoleName.ADMIN;
	}
}
