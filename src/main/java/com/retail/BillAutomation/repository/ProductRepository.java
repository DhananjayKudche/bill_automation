package com.retail.BillAutomation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.BillAutomation.data.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
