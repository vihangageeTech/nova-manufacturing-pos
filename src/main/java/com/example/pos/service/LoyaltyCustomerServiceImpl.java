package com.example.pos.service;

import com.example.pos.constant.MessageConstants;
import com.example.pos.entity.loyaltyCustomer.LoyaltyCustomer;
import com.example.pos.network.ResponseData;
import com.example.pos.repository.ILoyaltyCustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoyaltyCustomerServiceImpl {

    private final ILoyaltyCustomerRepository loyaltyCustomerRepository;

    public LoyaltyCustomerServiceImpl(ILoyaltyCustomerRepository loyaltyCustomerRepository) {
        this.loyaltyCustomerRepository = loyaltyCustomerRepository;
    }

    @Transactional
    public ResponseData<LoyaltyCustomer> findOrCreateCustomer(String mobileNumber) {
        LoyaltyCustomer customer = loyaltyCustomerRepository.findByMobileNumber(mobileNumber)
                .orElseGet(() -> {
                    LoyaltyCustomer newCustomer = new LoyaltyCustomer();
                    newCustomer.setMobileNumber(mobileNumber);
                    newCustomer.setFullName("Customer " + mobileNumber);
                    newCustomer.setLoyaltyPoints(0);
                    return loyaltyCustomerRepository.save(newCustomer);
                });
        return null;
    }
}