 package com.retail.BillAutomation.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.BillAutomation.Dto.ResponseDTO;
import com.retail.BillAutomation.billSerivce.BillServiceImpl;
import com.retail.BillAutomation.data.UserData;
import com.retail.BillAutomation.rabbitmq.MessageSender;
import com.retail.BillAutomation.repository.BillRepository;

@RestController
@RequestMapping("/billdata")
public class BillController {
	
    private static final Logger log = LoggerFactory.getLogger(BillController.class);

	@Autowired
	private BillServiceImpl billService;

	@Autowired
	private BillRepository billRepository;
	
	  
	@GetMapping("/data")
	public List<UserData> getAllUserData() {
		log.info("Fetching UserData from DB");
		// Sending a message 
		return billService.fetchAllUserData();
	}
	
	
	@GetMapping("/dataBasedOnResponse")
	public List<UserData> getDataBasedOnResponse() {
		log.info("Fetching UserData from DB");
		// Sending a message 
		return billService.fetchAllUserData();
	}
	
	@GetMapping("/datajson")
	public List<UserData> getAllUserDataFromJsonService() {
		System.out.println("Fetching UserData from JsonService");
		return billService.fetchAllUserDataFromJsonService();
	}

	@PostMapping("/create")
	public List<UserData> saveUser(@RequestBody List<UserData> user) throws IOException {
		if (null != user) {
			System.out.println(user);
			List<UserData> result = this.billRepository.save(null);
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
