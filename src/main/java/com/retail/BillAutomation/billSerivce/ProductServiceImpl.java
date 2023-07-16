package com.retail.BillAutomation.billSerivce;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.BillAutomation.data.Product;
import com.retail.BillAutomation.repository.ProductRepository;

@Service
public class ProductServiceImpl {
	
	@Autowired
	private ProductRepository productRepository;
	
	public Product saveProduct(Product product) {
		Product savedProduct = productRepository.save(product);
		return savedProduct;
	}
	
	public List<Product> fetchProductsFromDB(){
		return productRepository.findAll();
	}

}
