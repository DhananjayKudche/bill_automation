package com.retail.BillAutomation.billSerivce;

import java.util.List;

import com.retail.BillAutomation.data.UserData;

public interface BillService {
	
	public UserData saveInDB(UserData user);
	
	List<UserData> findByBillAmountGreaterThan(Integer billAmount);
}
