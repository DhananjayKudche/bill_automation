package com.retail.BillAutomation.Dto;

import com.retail.BillAutomation.data.UserData;

public class CustomerDTO {

	private UserData userData;

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public CustomerDTO(UserData userData) {
		super();
		this.userData = userData;
	}

	public CustomerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CustomerDTO [userData=" + userData + "]";
	}

}
