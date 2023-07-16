package com.retail.BillAutomation.Dto;

import java.util.Optional;

import com.retail.BillAutomation.data.Product;

import jakarta.persistence.Column;

public class ResponseDTO {
	private int id;

	private String name;

	private String contact;

	private String city;

//	private String membership;

	private float billAmount;
	
	private float discountedAmount;
	
	private Optional<Product> product;
	
	

	public Optional<Product> getProduct() {
		return product;
	}

	public void setProduct(Optional<Product> fetchedProductFromDBById) {
		this.product = fetchedProductFromDBById;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
//
//	public String getMembership() {
//		return membership;
//	}
//
//	public void setMembership(String membership) {
//		this.membership = membership;
//	}

	public float getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(float billAmount) {
		this.billAmount = billAmount;
	}

	public float getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(float discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	@Override
	public String toString() {
		return "ResponseDTO [id=" + id + ", name=" + name + ", contact=" + contact + ", city=" + city 
				 + ", billAmount=" + billAmount + ", discountedAmount=" + discountedAmount + "]";
	}
//
//	public ResponseDTO(int id, String name, String contact, String city, String membership, float billAmount,
//			float discountedAmount) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.contact = contact;
//		this.city = city;
//		this.membership = membership;
//		this.billAmount = billAmount;
//		this.discountedAmount = discountedAmount;
//	}
	
	
}
