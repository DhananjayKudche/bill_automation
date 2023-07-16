package com.retail.BillAutomation.billSerivce;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	@Override
	public UserData saveInDB(UserData user) {
		user.setUserCode(UUID.randomUUID().toString());
		UserData savedUser = billRepository.save(user);
		return savedUser;
	}

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
	
	public List<UserData> fetchAllUserData() {
		  
		return billRepository.findAll();
	}
	
	public Optional<Product> getProductListById(Integer id){
		return productRepository.findById(id);
	}

	@Override
	public List<UserData> findByBillAmountGreaterThan(Integer billAmount) {
		List<UserData> result = billRepository.findByBillAmountGreaterThan(billAmount);
		return result;
	}



}
