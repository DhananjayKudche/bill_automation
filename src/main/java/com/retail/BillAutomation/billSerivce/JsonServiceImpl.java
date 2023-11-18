package com.retail.BillAutomation.billSerivce;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.BillAutomation.data.UserData;

@Service
public class JsonServiceImpl {

	/**
	 * @param name
	 * @return
	 */
	public UserData fetchUserFromJsonByUserName(String name) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String inputJsonFilePath = "D:" + File.separator + "My Projects" + File.separator + "json_location"
				+ File.separator + "User_Jsons" + File.separator + "user_" + name + ".json";
		File inputJsonFile = null;
		UserData userdataFromjson = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			inputJsonFile = new File(inputJsonFilePath);
			userdataFromjson = objectMapper.readValue(inputJsonFile, UserData.class);
			System.out.println(userdataFromjson);
			stopWatch.stop();
			System.out.println("Time Taken to fetch dat from Json is " + stopWatch.getTotalTimeMillis() + " ms");

		} catch (Exception e) {
			e.getMessage();
			// TODO: handle exception
		}
		return userdataFromjson;
	}

	public List<UserData> fetchAllUserFromJson() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String inputJsonFilePath = "D:" + File.separator + "My Projects" + File.separator + "json_location"
				+ File.separator + "User_Jsons";
		File file = new File(inputJsonFilePath);
		List<UserData> resultantListOfUsersFromJson = new ArrayList<>();
		UserData userdataFromjson = null;
		File[] listOfJsons = file.listFiles();
		try {
			for (File eachFile : listOfJsons) {
				System.out.println("Json file is " + eachFile);

				ObjectMapper objectMapper = new ObjectMapper();
				userdataFromjson = objectMapper.readValue(eachFile, UserData.class);
				resultantListOfUsersFromJson.add(userdataFromjson);

			}
			stopWatch.stop();
			System.out.println("Time Taken to fetch all data from Jsons is " + stopWatch.getTotalTimeMillis() + " ms");

		} catch (Exception e) {
			e.getMessage();
		}
		return resultantListOfUsersFromJson;
	}

}
