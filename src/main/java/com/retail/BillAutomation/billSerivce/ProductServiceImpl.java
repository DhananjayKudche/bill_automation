package com.retail.BillAutomation.billSerivce;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.BillAutomation.data.Product;
import com.retail.BillAutomation.data.UserData;
import com.retail.BillAutomation.repository.ProductRepository;

@Service
public class ProductServiceImpl {

	enum Membership {
		GOLD, PLATINUM, SILVER, COMMON

	}

	@Autowired
	private ProductRepository productRepository;

	public Product saveProduct(Product product) {
		Product savedProduct = productRepository.save(product);
		return savedProduct;
	}

	public List<Product> fetchProductsFromDB() {
		return productRepository.findAll();
	}

	public String determineMembership(UserData user) {

		List<Product> productList = user.getProducts();
		Double productSum = productList.stream().mapToDouble(Product::getProductPrice).sum();
		Membership membership = Membership.COMMON;
		switch (membership) {
		case GOLD:
			if (productSum >= 3000) {
				user.setMembership(Membership.GOLD.toString());
				break;
			}
		case PLATINUM:
			if (productSum >= 2000 && productSum < 3000) {
				user.setMembership(Membership.PLATINUM.toString());
			}
			break;
		case SILVER:
			if (productSum >= 1000 && productSum < 2000) {
				user.setMembership(Membership.SILVER.toString());
			}
			break;
		}

		// Print the result
		System.out.println("User Tag: " + membership);
		return membership.toString();

	}
	
	/*
	 * public void eachSumOfProductOfEachUser(List<UserData> userData) {
	 * 
	 * // Assuming you have a list of users and each user has a list of products
	 * List<UserData> users = userData.stream().collect(Collectors.toList());
	 * 
	 * // Calculate the sum of product prices for each user using Java 8 streams
	 * Map<String, Object> userProductSum = users.stream()
	 * .collect(Collectors.toMap( UserData::getName, user) ->
	 * user.getProducts().stream() .mapToDouble(Product::getProductPrice) .sum() ));
	 * 
	 * Map<String, Double> userProductSum = users.stream()
	 * .collect(Collectors.toMap( UserData::getName, user ->
	 * user.getProducts().stream() .mapToDouble(Product::getPrice) .sum() )); //
	 * Print the results userProductSum.forEach((username, sum) -> {
	 * System.out.println("User: " + username + ", Product Sum: " + sum); });
	 * 
	 * } }
	 */
	 

}
