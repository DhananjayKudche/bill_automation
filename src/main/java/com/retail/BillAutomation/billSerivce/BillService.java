package com.retail.BillAutomation.billSerivce;

import java.util.Map;

import com.retail.BillAutomation.data.UserData;

public interface BillService {
	
	public UserData saveInDB(UserData user);
	
	Map<String, String> findByBillAmountGreaterThan(Integer billAmount);
}
