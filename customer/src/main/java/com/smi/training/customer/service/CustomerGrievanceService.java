package com.smi.training.customer.service;

import com.smi.training.customer.domain.CustomerGrievance;
import com.smi.training.customer.repository.CustomerGrievanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CustomerGrievance}.
 */
@Service
@Transactional
public class CustomerGrievanceService {

    private final Logger log = LoggerFactory.getLogger(CustomerGrievanceService.class);

    private final CustomerGrievanceRepository customerGrievanceRepository;

    public CustomerGrievanceService(CustomerGrievanceRepository customerGrievanceRepository) {
        this.customerGrievanceRepository = customerGrievanceRepository;
    }

    /**
     * Save a customerGrievance.
     *
     * @param customerGrievance the entity to save.
     * @return the persisted entity.
     */
    public CustomerGrievance save(CustomerGrievance customerGrievance) {
        log.debug("Request to save CustomerGrievance : {}", customerGrievance);
        return customerGrievanceRepository.save(customerGrievance);
    }

    /**
     * Get all the customerGrievances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerGrievance> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerGrievances");
        return customerGrievanceRepository.findAll(pageable);
    }

    /**
     * Get one customerGrievance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomerGrievance> findOne(Long id) {
        log.debug("Request to get CustomerGrievance : {}", id);
        return customerGrievanceRepository.findById(id);
    }

    /**
     * Delete the customerGrievance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomerGrievance : {}", id);
        customerGrievanceRepository.deleteById(id);
    }
}
