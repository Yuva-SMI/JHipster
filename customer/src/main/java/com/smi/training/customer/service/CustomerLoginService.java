package com.smi.training.customer.service;

import com.smi.training.customer.domain.CustomerLogin;
import com.smi.training.customer.repository.CustomerLoginRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CustomerLogin}.
 */
@Service
@Transactional
public class CustomerLoginService {

    private final Logger log = LoggerFactory.getLogger(CustomerLoginService.class);

    private final CustomerLoginRepository customerLoginRepository;

    public CustomerLoginService(CustomerLoginRepository customerLoginRepository) {
        this.customerLoginRepository = customerLoginRepository;
    }

    /**
     * Save a customerLogin.
     *
     * @param customerLogin the entity to save.
     * @return the persisted entity.
     */
    public CustomerLogin save(CustomerLogin customerLogin) {
        log.debug("Request to save CustomerLogin : {}", customerLogin);
        return customerLoginRepository.save(customerLogin);
    }

    /**
     * Get all the customerLogins.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerLogin> findAll() {
        log.debug("Request to get all CustomerLogins");
        return customerLoginRepository.findAll();
    }

    /**
     * Get one customerLogin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomerLogin> findOne(Long id) {
        log.debug("Request to get CustomerLogin : {}", id);
        return customerLoginRepository.findById(id);
    }

    /**
     * Delete the customerLogin by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomerLogin : {}", id);
        customerLoginRepository.deleteById(id);
    }
}
