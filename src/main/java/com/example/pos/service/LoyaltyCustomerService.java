package com.example.pos.service;

import com.example.pos.entity.loyaltyCustomer.LoyaltyCustomer;
import com.example.pos.network.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyCustomerService implements ILoyaltyCustomerService {

    private final LoyaltyCustomerServiceImpl loyaltyCustomerServiceImpl;

    public LoyaltyCustomerService(LoyaltyCustomerServiceImpl loyaltyCustomerServiceImpl) {
        this.loyaltyCustomerServiceImpl = loyaltyCustomerServiceImpl;
    }

    @Override
    public ResponseData<LoyaltyCustomer> findOrCreateCustomer(String mobileNumber) {
        try {
            return loyaltyCustomerServiceImpl.findOrCreateCustomer(mobileNumber);
        } catch (Exception exception) {
            return ResponseData.error(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to process customer");
        }
    }

    @Override
    public ResponseData<LoyaltyCustomer> findCustomerByMobile(String mobileNumber) {
        try {
            return loyaltyCustomerServiceImpl.findCustomerByMobile(mobileNumber);
        } catch (Exception exception) {
            return ResponseData.error(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve customer");
        }
    }
}