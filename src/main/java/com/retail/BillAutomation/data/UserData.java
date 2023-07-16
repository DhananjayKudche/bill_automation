package com.retail.BillAutomation.data;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="user_data")
public class UserData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	@Column
	private String name;
	@Column
	private String contact;
	@Column
	private String city;
	@Column
	private String membership;
	@Column
	private float billAmount;
	@Column
	private String userCode;
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@OneToMany(cascade = CascadeType.ALL, targetEntity = Product.class, fetch = FetchType.EAGER)
	@JoinColumn(name= "up_fk", referencedColumnName = "id")
	private List<Product> products;

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

	public String getMembership() {
		return membership;
	}

	public void setMembership(String membership) {
		this.membership = membership;
	}

	public float getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(float billAmount) {
		this.billAmount = billAmount;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}



	@Override
	public String toString() {
		return "UserData [id=" + id + ", name=" + name + ", contact=" + contact + ", city=" + city + ", membership="
				+ membership + ", billAmount=" + billAmount + ", userCode=" + userCode + ", products=" + products + "]";
	}

	public UserData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
