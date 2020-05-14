package com.smi.training.customer.web.rest;

import com.smi.training.customer.domain.CustomerGrievance;
import com.smi.training.customer.service.CustomerGrievanceService;
import com.smi.training.customer.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.smi.training.customer.domain.CustomerGrievance}.
 */
@RestController
@RequestMapping("/api")
public class CustomerGrievanceResource {

    private final Logger log = LoggerFactory.getLogger(CustomerGrievanceResource.class);

    private static final String ENTITY_NAME = "customerCustomerGrievance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerGrievanceService customerGrievanceService;

    public CustomerGrievanceResource(CustomerGrievanceService customerGrievanceService) {
        this.customerGrievanceService = customerGrievanceService;
    }

    /**
     * {@code POST  /customer-grievances} : Create a new customerGrievance.
     *
     * @param customerGrievance the customerGrievance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerGrievance, or with status {@code 400 (Bad Request)} if the customerGrievance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-grievances")
    public ResponseEntity<CustomerGrievance> createCustomerGrievance(@Valid @RequestBody CustomerGrievance customerGrievance) throws URISyntaxException {
        log.debug("REST request to save CustomerGrievance : {}", customerGrievance);
        if (customerGrievance.getId() != null) {
            throw new BadRequestAlertException("A new customerGrievance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerGrievance result = customerGrievanceService.save(customerGrievance);
        return ResponseEntity.created(new URI("/api/customer-grievances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-grievances} : Updates an existing customerGrievance.
     *
     * @param customerGrievance the customerGrievance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerGrievance,
     * or with status {@code 400 (Bad Request)} if the customerGrievance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerGrievance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-grievances")
    public ResponseEntity<CustomerGrievance> updateCustomerGrievance(@Valid @RequestBody CustomerGrievance customerGrievance) throws URISyntaxException {
        log.debug("REST request to update CustomerGrievance : {}", customerGrievance);
        if (customerGrievance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerGrievance result = customerGrievanceService.save(customerGrievance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerGrievance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-grievances} : get all the customerGrievances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerGrievances in body.
     */
    @GetMapping("/customer-grievances")
    public ResponseEntity<List<CustomerGrievance>> getAllCustomerGrievances(Pageable pageable) {
        log.debug("REST request to get a page of CustomerGrievances");
        Page<CustomerGrievance> page = customerGrievanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customer-grievances/:id} : get the "id" customerGrievance.
     *
     * @param id the id of the customerGrievance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerGrievance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-grievances/{id}")
    public ResponseEntity<CustomerGrievance> getCustomerGrievance(@PathVariable Long id) {
        log.debug("REST request to get CustomerGrievance : {}", id);
        Optional<CustomerGrievance> customerGrievance = customerGrievanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerGrievance);
    }

    /**
     * {@code DELETE  /customer-grievances/:id} : delete the "id" customerGrievance.
     *
     * @param id the id of the customerGrievance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-grievances/{id}")
    public ResponseEntity<Void> deleteCustomerGrievance(@PathVariable Long id) {
        log.debug("REST request to delete CustomerGrievance : {}", id);
        customerGrievanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
