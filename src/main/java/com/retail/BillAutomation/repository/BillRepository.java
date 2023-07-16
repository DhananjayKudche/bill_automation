package com.retail.BillAutomation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.retail.BillAutomation.data.UserData;

@Repository
public interface BillRepository extends JpaRepository<UserData, Integer>{
	
	/**
	 * 
	 * @param billAmount
	 * @return
	 */
	List<UserData> findByBillAmountGreaterThan(Integer billAmount);
	
	/**
	 * 
	 * @param membership
	 * @return
	 */
	@Query("select u from UserData u where u.membership = ?1" )
	List<UserData> findByMembership(String membership);
}
