package com.example.pos.entity.loyaltyCustomer;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "loyalty_customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "mobile_number", nullable = false, unique = true)
    private String mobileNumber;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Email(message = "Invalid email format")
    @Column(name = "email")
    private String email;

    @Min(value = 0, message = "Loyalty points must be non-negative")
    @Column(name = "loyalty_points", nullable = false)
    private Integer loyaltyPoints = 0;

    @CreationTimestamp
    @Column(name = "registered_at", nullable = false, updatable = false)
    private LocalDateTime registeredAt;
}
