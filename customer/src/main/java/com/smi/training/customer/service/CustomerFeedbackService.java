package com.smi.training.customer.service;

import com.smi.training.customer.domain.CustomerFeedback;
import com.smi.training.customer.repository.CustomerFeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CustomerFeedback}.
 */
@Service
@Transactional
public class CustomerFeedbackService {

    private final Logger log = LoggerFactory.getLogger(CustomerFeedbackService.class);

    private final CustomerFeedbackRepository customerFeedbackRepository;

    public CustomerFeedbackService(CustomerFeedbackRepository customerFeedbackRepository) {
        this.customerFeedbackRepository = customerFeedbackRepository;
    }

    /**
     * Save a customerFeedback.
     *
     * @param customerFeedback the entity to save.
     * @return the persisted entity.
     */
    public CustomerFeedback save(CustomerFeedback customerFeedback) {
        log.debug("Request to save CustomerFeedback : {}", customerFeedback);
        return customerFeedbackRepository.save(customerFeedback);
    }

    /**
     * Get all the customerFeedbacks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerFeedback> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerFeedbacks");
        return customerFeedbackRepository.findAll(pageable);
    }

    /**
     * Get one customerFeedback by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomerFeedback> findOne(Long id) {
        log.debug("Request to get CustomerFeedback : {}", id);
        return customerFeedbackRepository.findById(id);
    }

    /**
     * Delete the customerFeedback by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomerFeedback : {}", id);
        customerFeedbackRepository.deleteById(id);
    }
}
