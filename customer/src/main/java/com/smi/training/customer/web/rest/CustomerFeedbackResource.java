package com.smi.training.customer.web.rest;

import com.smi.training.customer.domain.CustomerFeedback;
import com.smi.training.customer.service.CustomerFeedbackService;
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
 * REST controller for managing {@link com.smi.training.customer.domain.CustomerFeedback}.
 */
@RestController
@RequestMapping("/api")
public class CustomerFeedbackResource {

    private final Logger log = LoggerFactory.getLogger(CustomerFeedbackResource.class);

    private static final String ENTITY_NAME = "customerCustomerFeedback";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerFeedbackService customerFeedbackService;

    public CustomerFeedbackResource(CustomerFeedbackService customerFeedbackService) {
        this.customerFeedbackService = customerFeedbackService;
    }

    /**
     * {@code POST  /customer-feedbacks} : Create a new customerFeedback.
     *
     * @param customerFeedback the customerFeedback to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerFeedback, or with status {@code 400 (Bad Request)} if the customerFeedback has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-feedbacks")
    public ResponseEntity<CustomerFeedback> createCustomerFeedback(@Valid @RequestBody CustomerFeedback customerFeedback) throws URISyntaxException {
        log.debug("REST request to save CustomerFeedback : {}", customerFeedback);
        if (customerFeedback.getId() != null) {
            throw new BadRequestAlertException("A new customerFeedback cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerFeedback result = customerFeedbackService.save(customerFeedback);
        return ResponseEntity.created(new URI("/api/customer-feedbacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-feedbacks} : Updates an existing customerFeedback.
     *
     * @param customerFeedback the customerFeedback to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerFeedback,
     * or with status {@code 400 (Bad Request)} if the customerFeedback is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerFeedback couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-feedbacks")
    public ResponseEntity<CustomerFeedback> updateCustomerFeedback(@Valid @RequestBody CustomerFeedback customerFeedback) throws URISyntaxException {
        log.debug("REST request to update CustomerFeedback : {}", customerFeedback);
        if (customerFeedback.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerFeedback result = customerFeedbackService.save(customerFeedback);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerFeedback.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-feedbacks} : get all the customerFeedbacks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerFeedbacks in body.
     */
    @GetMapping("/customer-feedbacks")
    public ResponseEntity<List<CustomerFeedback>> getAllCustomerFeedbacks(Pageable pageable) {
        log.debug("REST request to get a page of CustomerFeedbacks");
        Page<CustomerFeedback> page = customerFeedbackService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customer-feedbacks/:id} : get the "id" customerFeedback.
     *
     * @param id the id of the customerFeedback to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerFeedback, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-feedbacks/{id}")
    public ResponseEntity<CustomerFeedback> getCustomerFeedback(@PathVariable Long id) {
        log.debug("REST request to get CustomerFeedback : {}", id);
        Optional<CustomerFeedback> customerFeedback = customerFeedbackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerFeedback);
    }

    /**
     * {@code DELETE  /customer-feedbacks/:id} : delete the "id" customerFeedback.
     *
     * @param id the id of the customerFeedback to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-feedbacks/{id}")
    public ResponseEntity<Void> deleteCustomerFeedback(@PathVariable Long id) {
        log.debug("REST request to delete CustomerFeedback : {}", id);
        customerFeedbackService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
