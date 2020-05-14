package com.smi.training.customer.repository;

import com.smi.training.customer.domain.CustomerLogin;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerLogin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerLoginRepository extends JpaRepository<CustomerLogin, Long> {
}
