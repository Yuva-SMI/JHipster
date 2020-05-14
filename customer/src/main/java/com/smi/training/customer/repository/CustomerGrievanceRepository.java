package com.smi.training.customer.repository;

import com.smi.training.customer.domain.CustomerGrievance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerGrievance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerGrievanceRepository extends JpaRepository<CustomerGrievance, Long> {
}
