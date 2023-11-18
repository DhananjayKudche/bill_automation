package com.retail.BillAutomation.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.retail.BillAutomation.Dto.ResponseDTO;
import com.retail.BillAutomation.billSerivce.BillServiceImpl;
import com.retail.BillAutomation.data.UserData;
import com.retail.BillAutomation.repository.BillRepository;

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
	public List<UserData> saveUser(@RequestBody List<UserData> user) throws IOException {
		if (null != user) {
			System.out.println(user);
			List<UserData> result = billService.saveInDB(user);
			System.out.println(result);
			return result;
		}
		return null;
	}

	@GetMapping("/findby/{membership}")
	public List<UserData> getUserByMembership(@PathVariable String membership) {
		return billService.findByTypeOfMembership(membership);
	}

	// This method is under dev
	/*
	 * @PostMapping("/create2") public UserData saveUser1(@RequestBody CustomerDTO
	 * customerDTO) { if(null != customerDTO) { return
	 * billRepository.save(customerDTO.getUserData()); } return null; }
	 */

	@GetMapping("/getdata")
	public Optional<UserData> getByIdWithOutDiscount(@RequestParam Integer id) {
		System.out.println("Returning existing data");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Optional<UserData> result = billRepository.findById(id);
		stopWatch.stop();
		System.out.println("Time Taken to fetch data from DB is " + stopWatch.getTotalTimeMillis() + " ms");

		return result;
	}

	@GetMapping("/finalprice")
	public ResponseDTO getByIdWithDiscount(@RequestParam Integer id) {
		System.out.println("Returning DTO object");
		return billService.fetchUserById(id);
	}

	@GetMapping("/bill")
	public Map<String, String> fetchUserGreaterThanBillAmount(@RequestParam Integer billAmaount) {
		return billService.findByBillAmountGreaterThan(billAmaount);
	}

	@GetMapping("/userData")
	public List<UserData> getUserByNameAndCity(@RequestParam String name, String city) {
		return billService.getByNameAndCity(name, city);
	}

	@PostMapping("/userDataFromJson")
	public UserData fetchDataFromJson(@RequestParam String jsonPath) throws IOException {
		return billService.fetchDataFromJson(jsonPath);
	}
	
	@PostMapping("/saveUserDataToJsonByUserName")
	public List<UserData> saveUserDataToJsonByUserName() throws IOException {
		return billService.saveUserDataToJsonByUserName();
	}
	
	@PostMapping("/saveUserDataToJsonByUserNameAfterCreatingUser")
	public List<UserData> saveUserDataToJsonByUserNameAfterCreatingUser() throws IOException {
		return billService.saveUserDataToJsonByUserName();
	}
	
	@DeleteMapping("/deleteById")
	public void deleteEntryByIdFromRepo(@RequestParam Integer id) {
		billRepository.deleteById(id);
	}
}
