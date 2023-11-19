package com.retail.BillAutomation.billSerivce;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.BillAutomation.Dto.ResponseDTO;
import com.retail.BillAutomation.data.Product;
import com.retail.BillAutomation.data.UserData;
import com.retail.BillAutomation.repository.BillRepository;
import com.retail.BillAutomation.repository.ProductRepository;

@Service
public class BillServiceImpl implements BillService {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private Logger log = LoggerFactory.getLogger(BillServiceImpl.class);

	/**
	 * @param user
	 * @return
	 * @throws IOException
	 */
	public List<UserData> saveInDB(List<UserData> user) throws IOException {
		List<UserData> latestAddedEntries = new ArrayList<>();
		for (UserData eachUser : user) {
			eachUser.setUserCode(UUID.randomUUID().toString());
			UserData savedUser = billRepository.save(eachUser);
			latestAddedEntries.add(savedUser);

		}
		createJsonFromRepoData(latestAddedEntries);

		// call here

		return latestAddedEntries;
	}

	/**
	 * @param id
	 * @return
	 */
	public ResponseDTO fetchUserById(Integer id) {
		ResponseDTO discountedUser = new ResponseDTO();
		Optional<UserData> fetchedUserFromDB = billRepository.findById(id);
		Optional<Product> fetchedProductFromDBById = getProductListById(id);
		float discountedAmount = discount(fetchedUserFromDB);
		discountedUser.setId(fetchedUserFromDB.get().getId());
		discountedUser.setName(fetchedUserFromDB.get().getName());
		discountedUser.setBillAmount((fetchedUserFromDB.get().getBillAmount()));
		discountedUser.setCity(fetchedUserFromDB.get().getCity());
		discountedUser.setContact(fetchedUserFromDB.get().getContact());
		discountedUser.setDiscountedAmount(discountedAmount);
		discountedUser.setProduct(fetchedProductFromDBById);
		return discountedUser;
	}

	/**
	 * @param fetchedUserFromDB
	 * @return
	 */
	public float discount(Optional<UserData> fetchedUserFromDB) {
		if (fetchedUserFromDB.get().getMembership().equals("Silver")) {
			System.out.println("10% discount on Silver membership");
			return fetchedUserFromDB.get().getBillAmount() * 0.9f;
		} else if (fetchedUserFromDB.get().getMembership().equals("Gold")) {
			System.out.println("15% discount on Gold membership");
			return fetchedUserFromDB.get().getBillAmount() * 0.85f;
		} else if (fetchedUserFromDB.get().getMembership().equals("Platinum")) {
			System.out.println("20% discount on Platinum membership");
			return fetchedUserFromDB.get().getBillAmount() * 0.80f;
		}
		return 0f;
	}

	/**
	 * @return
	 */
	public List<UserData> fetchAllUserData() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		List<UserData> fetchedData = billRepository.findAll();
		stopWatch.stop();
		System.out.println("Time Taken to fetch all data from DB is " + stopWatch.getTotalTimeMillis() + " ms");

		return fetchedData;
	}
	
	/**
	 * @return
	 */
	public List<UserData> fetchAllUserDataFromJsonService() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		// localhost:8082/json/alljsondata
		ArrayList<UserData> res = restTemplate.getForObject("http://localhost:8082/json/alljsondata",
				ArrayList.class);
		log.info("fetched data is {}", res);
		stopWatch.stop();
		System.out.println("Time Taken to fetch all data from DB is " + stopWatch.getTotalTimeMillis() + " ms");

		return res;
	}

	/**
	 * @param id
	 * @return
	 */
	public Optional<Product> getProductListById(Integer id) {
		return productRepository.findById(id);
	}

	/**
	 *
	 */
	@Override
	public Map<String, String> findByBillAmountGreaterThan(Integer billAmount) {
		List<UserData> result = billRepository.findByBillAmountGreaterThan(billAmount);
//		List<String> resultList = new ArrayList<>();
		Map<String, String> hashMap = new HashMap<>();
		for (UserData user : result) {
			if (user.getProducts().size() != 0) {
				getCostlyProductFromUser(hashMap, user);
			}

		}
		return hashMap;
	}

	/**
	 * @param hashMap
	 * @param user
	 */
	private void getCostlyProductFromUser(Map<String, String> hashMap, UserData user) {
		List<Product> filteredList = user.getProducts().stream().filter(e -> e.getProductPrice() > 9)
				.collect(Collectors.toList());
		int prevPrice = 0;
		for (Product costlyPorduct : filteredList) {

			if (hashMap.containsKey(user.getName())) {
				int currentPrice = costlyPorduct.getProductPrice();
				if (currentPrice > prevPrice) {
					hashMap.put(user.getName(), costlyPorduct.getProductName());
					prevPrice = currentPrice;
				}
			} else {
				prevPrice = costlyPorduct.getProductPrice();
				hashMap.put(user.getName(), costlyPorduct.getProductName());
			}
		}
	}

	/**
	 * @param membership
	 * @return
	 */
	public List<UserData> findByTypeOfMembership(String membership) {
		return billRepository.findByMembership(membership);
	}

	/**
	 * @param name
	 * @param city
	 * @return
	 */
	public List<UserData> getByNameAndCity(String name, String city) {
		List<UserData> fetchedUserData = billRepository.findUserDataByNameAndCity(name, city);
		try {
			for (UserData user : fetchedUserData) {
				ObjectMapper object = new ObjectMapper();
				object.writeValue(new File("D:/My Projects/json_location/user_product.json"), user);
				System.out.println("Data is written in json file, please check");
			}

		} catch (Exception e) {
			e.getMessage();
		}
		return fetchedUserData;
	}

	/**
	 * @param jsonPath
	 * @throws IOException
	 */
	public UserData fetchDataFromJson(String jsonPath) throws IOException {
		UserData savedEntity = null;
		File file = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			file = new File(jsonPath);
			UserData userdataFromjson = objectMapper.readValue(file, UserData.class);
			System.out.println(userdataFromjson);
			savedEntity = saveInDB(userdataFromjson);
			String filePath = "D:/My Projects/json_location/user_product_write3.json";

			objectMapper.writeValue(new File(filePath), savedEntity);

		} catch (Exception e) {
			e.getMessage();
		} finally {
			FileUtils.forceDelete(file);
			System.out.println("File deleted " + file.getName());
		}
		return savedEntity;
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public List<UserData> saveUserDataToJsonByUserName() throws IOException {
		List<UserData> listOfUserData = fetchAllUserData();
		createJsonFromRepoData(listOfUserData);
		return listOfUserData;
	}

	/**
	 * @param listOfUserData
	 * @throws IOException
	 * @throws StreamWriteException
	 * @throws DatabindException
	 */
	private void createJsonFromRepoData(List<UserData> listOfUserData)
			throws IOException, StreamWriteException, DatabindException {
		ObjectMapper objectMapper = new ObjectMapper();
		File userJson = null;
		for (UserData eachUserData : listOfUserData) {
			String filePath = "D:" + File.separator + "My Projects" + File.separator + "json_location" + File.separator
					+ "User_Jsons" + File.separator + "user_" + eachUserData.getName() + ".json";
			userJson = new File(filePath);
			System.out.println("Each User is being written is: " + eachUserData);
			objectMapper.writeValue(userJson, eachUserData);

		}
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public UserData saveUserDataToJsonByUserNameAfterCreatingUser(UserData createdUser) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		File userJson = null;
		String filePath = "D:" + File.separator + "My Projects" + File.separator + "json_location" + File.separator
				+ "User_Jsons" + File.separator + "user_" + createdUser.getName() + ".json";
		userJson = new File(filePath);
		System.out.println("Each User is being written is: " + createdUser);
		objectMapper.writeValue(userJson, createdUser);

		return createdUser;
	}

	@Override
	public UserData saveInDB(UserData user) {
		user.setUserCode(UUID.randomUUID().toString());
		UserData savedUser = billRepository.save(user);
		return savedUser;
	}
}
