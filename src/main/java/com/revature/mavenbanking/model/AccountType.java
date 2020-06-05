package com.revature.mavenbanking.model;

public class AccountType {
	private int typeId;
	private String type;
	
	public AccountType() {}
	
	

	public AccountType(int typeId, String type) {
		this.typeId = typeId;
		this.type = type;
	}



	/**
	 * @return the typeId
	 */
	public int getTypeId() {
		return typeId;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	/**
	 * @return the type
	 */
	
	public String getAccountType() {
		return this.type;
	}
	/**
	 * @param type the type to set
	 */
	public void setAccountType(String type) {
		this.type = type;
	}
	
	
}
