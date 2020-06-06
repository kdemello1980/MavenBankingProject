package com.revature.mavenbanking.model;
import java.math.BigDecimal;

public class AccountType {
	private int typeId;
	private String type;
	BigDecimal interestRate;
	BigDecimal monthlyFee;
	
	
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



	/**
	 * @return the interestRate
	 */
	public BigDecimal getInterestRate() {
		return interestRate;
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



	@Override
	public String toString() {
		return "AccountType [typeId=" + typeId + ", type=" + type + ", interestRate=" + interestRate + ", monthlyFee="
				+ monthlyFee + "]";
	}
	
	
}
