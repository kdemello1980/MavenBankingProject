package com.kdemello.mavenbanking.model;
import java.math.BigDecimal;

public class AccountType {
	private int typeId;
	private String type;
	private BigDecimal interestRate;
	private BigDecimal monthlyFee;
	private int compoundMonths;
	private int permissionId;
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the permissionId
	 */
	public int getPermissionId() {
		return permissionId;
	}
	/**
	 * @param permissionId the permissionId to set
	 */
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}
	private Permission permission;


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

	/**
	 * @return the interestRate
	 */
	public BigDecimal getInterestRate() {
		return interestRate;
	}

	/**
	 * @return the permission
	 */
	public Permission getPermission() {
		return permission;
	}
	/**
	 * @param permission the permission to set
	 */
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	/**
	 * @param interestRate the interestRate to set
	 */
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * @return the monthlyFee
	 */
	public BigDecimal getMonthlyFee() {
		return monthlyFee;
	}

	/**
	 * @param monthlyFee the monthlyFee to set
	 */
	public void setMonthlyFee(BigDecimal monthlyFee) {
		this.monthlyFee = monthlyFee;
	}
	
	public int getCompoundMonths() {
		return compoundMonths;
	}
	
	public void setCompoundMonths(int compoundMonths) {
		this.compoundMonths = compoundMonths;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AccountType [typeId=" + typeId + ", type=" + type + ", interestRate=" + interestRate + ", monthlyFee="
				+ monthlyFee + ", compoundMonths=" + compoundMonths + ", permission=" + permission + "]";
	}

	
}
