package com.smi.training.customer.repository;

import com.smi.training.customer.domain.CustomerFeedback;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerFeedback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerFeedbackRepository extends JpaRepository<CustomerFeedback, Long> {
}
