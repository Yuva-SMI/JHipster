package com.smi.training.customer.repository;

import com.smi.training.customer.domain.CustomerDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long> {
}
