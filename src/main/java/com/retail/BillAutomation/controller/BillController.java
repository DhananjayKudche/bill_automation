package com.retail.BillAutomation.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.retail.BillAutomation.Dto.CustomerDTO;
import com.retail.BillAutomation.Dto.ResponseDTO;
import com.retail.BillAutomation.billSerivce.BillServiceImpl;
import com.retail.BillAutomation.data.UserData;
import com.retail.BillAutomation.repository.BillRepository;
import com.retail.BillAutomation.repository.ProductRepository;

@Controller
@ResponseBody
public class BillController {
	
	@Autowired
	private BillServiceImpl billService;
	
	@Autowired
	private BillRepository billRepository;
	
	@GetMapping("/data")
	public List<UserData> getAllUserData() {
		System.out.println("Fetching UserData from DB");
		return billService.fetchAllUserData();
	}
	
	@PostMapping("/create")
	public UserData saveUser(@RequestBody UserData user) {
		if(null != user) {
			System.out.println(user);
			UserData result=  billService.saveInDB(user);
			System.out.println(result);
			return result;
		}
		return null;
	}
	
	@GetMapping("/findby/{membership}")
	public List<UserData> getUserByMembership(@PathVariable String membership) {
		return billService.findByTypeOfMembership(membership);
	}
	
	//This method is under dev
	/*
	@PostMapping("/create2")
	public UserData saveUser1(@RequestBody CustomerDTO customerDTO) {
		if(null != customerDTO) {
			return billRepository.save(customerDTO.getUserData());
		}
		return null;
	}
	*/
	
	@GetMapping("/getdata")
	public Optional<UserData> getByIdWithOutDiscount(@RequestParam Integer id) {
		System.out.println("Returning existing data");
		return billRepository.findById(id);
	}
	
	@GetMapping("/finalprice")
	public ResponseDTO getByIdWithDiscount(@RequestParam Integer id) {
		System.out.println("Returning DTO object");
		return billService.fetchUserById(id);
	}
	
	@GetMapping("/bill")
	public List<UserData> fetchUserGreaterThanBillAmount(@RequestParam Integer billAmaount) {
		return billService.findByBillAmountGreaterThan(billAmaount);
	}
}
