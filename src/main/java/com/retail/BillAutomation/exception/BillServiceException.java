package com.retail.BillAutomation.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class BillServiceException extends Exception{
	
	@Autowired
	private final HttpStatus httpStatus;
	
	
	public BillServiceException(Exception e, String message) {
		this.httpStatus = null;
	}

	public BillServiceException(HttpStatus notFound, String message) {
		super(message);
		this.httpStatus = notFound;
		
	}

}
