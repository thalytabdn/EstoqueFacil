package com.ufcg.psoft.enumeration;

public enum UserRoleName {
	
	ADMIN ("ADMIN"),
	CLIENTE ("CLIENTE");
	
	private final String roleName;
	
	UserRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public UserRoleName fromString(String roleName) {
		return UserRoleName.valueOf(roleName);
	}
	
	public String toString() {
		return roleName;
	}
}
