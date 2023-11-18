package com.retail.BillAutomation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.retail.BillAutomation.billSerivce.ProductServiceImpl;
import com.retail.BillAutomation.data.Product;

@RestController
public class ProductController {

	@Autowired
	private ProductServiceImpl productServiceImpl;

	@GetMapping("/getproducts")
	public List<Product> getProducts() {
		return productServiceImpl.fetchProductsFromDB();
	}

	@PostMapping("/save")
	public Product saveIntoDB(@RequestBody Product product) {
		return productServiceImpl.saveProduct(product);
	}

}
