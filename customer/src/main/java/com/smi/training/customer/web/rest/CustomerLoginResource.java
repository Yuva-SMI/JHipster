package com.smi.training.customer.web.rest;

import com.smi.training.customer.domain.CustomerLogin;
import com.smi.training.customer.service.CustomerLoginService;
import com.smi.training.customer.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.smi.training.customer.domain.CustomerLogin}.
 */
@RestController
@RequestMapping("/api")
public class CustomerLoginResource {

    private final Logger log = LoggerFactory.getLogger(CustomerLoginResource.class);

    private static final String ENTITY_NAME = "customerCustomerLogin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerLoginService customerLoginService;

    public CustomerLoginResource(CustomerLoginService customerLoginService) {
        this.customerLoginService = customerLoginService;
    }

    /**
     * {@code POST  /customer-logins} : Create a new customerLogin.
     *
     * @param customerLogin the customerLogin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerLogin, or with status {@code 400 (Bad Request)} if the customerLogin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-logins")
    public ResponseEntity<CustomerLogin> createCustomerLogin(@Valid @RequestBody CustomerLogin customerLogin) throws URISyntaxException {
        log.debug("REST request to save CustomerLogin : {}", customerLogin);
        if (customerLogin.getId() != null) {
            throw new BadRequestAlertException("A new customerLogin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerLogin result = customerLoginService.save(customerLogin);
        return ResponseEntity.created(new URI("/api/customer-logins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-logins} : Updates an existing customerLogin.
     *
     * @param customerLogin the customerLogin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerLogin,
     * or with status {@code 400 (Bad Request)} if the customerLogin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerLogin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-logins")
    public ResponseEntity<CustomerLogin> updateCustomerLogin(@Valid @RequestBody CustomerLogin customerLogin) throws URISyntaxException {
        log.debug("REST request to update CustomerLogin : {}", customerLogin);
        if (customerLogin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerLogin result = customerLoginService.save(customerLogin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerLogin.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-logins} : get all the customerLogins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerLogins in body.
     */
    @GetMapping("/customer-logins")
    public List<CustomerLogin> getAllCustomerLogins() {
        log.debug("REST request to get all CustomerLogins");
        return customerLoginService.findAll();
    }

    /**
     * {@code GET  /customer-logins/:id} : get the "id" customerLogin.
     *
     * @param id the id of the customerLogin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerLogin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-logins/{id}")
    public ResponseEntity<CustomerLogin> getCustomerLogin(@PathVariable Long id) {
        log.debug("REST request to get CustomerLogin : {}", id);
        Optional<CustomerLogin> customerLogin = customerLoginService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerLogin);
    }

    /**
     * {@code DELETE  /customer-logins/:id} : delete the "id" customerLogin.
     *
     * @param id the id of the customerLogin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-logins/{id}")
    public ResponseEntity<Void> deleteCustomerLogin(@PathVariable Long id) {
        log.debug("REST request to delete CustomerLogin : {}", id);
        customerLoginService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
