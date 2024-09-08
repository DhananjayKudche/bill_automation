package com.retail.BillAutomation.billSerivce;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.retail.BillAutomation.data.UserData;
import com.retail.BillAutomation.repository.BillRepository;

@Service
public class UserService {

	@Autowired
	private BillRepository billRepository;

	    public Page<UserData> fetchInitialUserData(int pageSize) {
	        Pageable pageable = PageRequest.of(0, pageSize, Sort.by("id").ascending());
	        return billRepository.findAll(pageable);
	    }

	    public Page<UserData> fetchUserDataByToken(String token, int pageSize) {
	        Long lastId = decodeToken(token);
	        Pageable pageable = PageRequest.of(0, pageSize, Sort.by("city").ascending());
	        return billRepository.findByIdGreaterThan(lastId, pageable);
	    }

	    public String generateNextPageToken(Page<UserData> page) {
	        UserData lastUserData = page.getContent().get(page.getContent().size() - 1);
	        return encodeToken(lastUserData.getId());
	    }

	    public String encodeToken(int id) {
	        return encodeToken(Long.valueOf(id));
	    }
	    // Cache the result of encodeToken method
	    @Cacheable(value = "tokens", key = "#id")
	    private String encodeToken(Long id) {
	        // Simulate a heavy operation, such as database query or token generation
	        return generateToken(id);
//	        return Base64.getEncoder().encodeToString(id.toString().getBytes());
	    }

	    private Long decodeToken(String token) {
	        return Long.parseLong(new String(Base64.getDecoder().decode(token)));
	    }
	    
	    private String generateToken(Long id) {
	        // Simulate token generation logic
	        return "token-" + id;
	    }
	


}
