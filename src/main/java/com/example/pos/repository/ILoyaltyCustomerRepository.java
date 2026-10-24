package com.example.pos.repository;

import com.example.pos.entity.loyaltyCustomer.LoyaltyCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILoyaltyCustomerRepository extends JpaRepository<LoyaltyCustomer, Long> {

    Optional<LoyaltyCustomer> findByMobileNumber(String mobileNumber);

    boolean existsByMobileNumber(String mobileNumber);
}