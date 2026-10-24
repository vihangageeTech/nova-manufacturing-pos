package com.example.pos.service;

import com.example.pos.entity.loyaltyCustomer.LoyaltyCustomer;
import com.example.pos.network.ResponseData;

public interface ILoyaltyCustomerService {

    ResponseData<LoyaltyCustomer> findOrCreateCustomer(String mobileNumber);

}