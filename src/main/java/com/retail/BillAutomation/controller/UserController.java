package com.retail.BillAutomation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.BillAutomation.billSerivce.BillServiceImpl;
import com.retail.BillAutomation.billSerivce.UserService;
import com.retail.BillAutomation.data.UserData;
import com.retail.BillAutomation.exception.BillServiceException;
import com.retail.BillAutomation.repository.BillRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	BillRepository billRepository;

	@Autowired
	private UserService userService;

	@GetMapping("/getUser")
	public ResponseEntity<Boolean> isUserPresentInRecord(@RequestParam String userName) throws BillServiceException {

		try {
			String userNameFromRecord = billRepository.findUserNameByName(userName);
			return ResponseEntity.ok(userNameFromRecord != null);
		} catch (Exception e) {
			// Handle exceptions here, e.g., log, return specific error code
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/getonebyone")
	public ResponseEntity<List<UserData>> getUserData(
			@RequestHeader(value = "X-Next-Page-Token", required = false) String nextPageToken) {

		log.info("Fetching UserData from DB with pagination using token");

		int pageSize = 10; // Fixed page size
		Page<UserData> userDataPage;

		if (nextPageToken == null) {
			userDataPage = userService.fetchInitialUserData(pageSize);
		} else {
			userDataPage = userService.fetchUserDataByToken(nextPageToken, pageSize);
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		if (userDataPage.hasNext()) {
			String newNextPageToken = userService.generateNextPageToken(userDataPage);
			responseHeaders.set("X-Next-Page-Token", newNextPageToken);
		}

		return ResponseEntity.ok().headers(responseHeaders).body(userDataPage.getContent());
	}

}
