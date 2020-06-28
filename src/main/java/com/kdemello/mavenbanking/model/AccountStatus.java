package com.kdemello.mavenbanking.model;

public class AccountStatus {

	private int statusId;
	private String statusName;
	
	public AccountStatus() {}
	
	public AccountStatus(int statusId, String statusName) {
		this.statusId = statusId;
		this.statusName = statusName;
	}

	/**
	 * @return the statusId
	 */
	public int getStatusId() {
		return statusId;
	}
	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	/**
	 * @return the status
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatusName(String status) {
		this.statusName = status;
	}
	
	@Override
	public String toString() {
		return "AccountStatus [statusId=" + statusId + ", statusName=" + statusName + "]";
	}

}
