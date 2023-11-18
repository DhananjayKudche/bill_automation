package com.retail.BillAutomation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.retail.BillAutomation.billSerivce.JsonServiceImpl;
import com.retail.BillAutomation.data.UserData;

@Controller
@ResponseBody
public class JsonController {

	@Autowired
	private JsonServiceImpl jsonServiceImpl;

	@GetMapping("/jsondata")
	public UserData getUserDataForJsonByName(@RequestParam String name) {

		return jsonServiceImpl.fetchUserFromJsonByUserName(name);
	}

	@GetMapping("/alljsondata")
	public List<UserData> fetchAllUserFromJson() {

		return jsonServiceImpl.fetchAllUserFromJson();
	}
}
