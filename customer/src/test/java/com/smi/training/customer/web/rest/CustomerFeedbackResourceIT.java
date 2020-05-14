package com.smi.training.customer.web.rest;

import com.smi.training.customer.CustomerApp;
import com.smi.training.customer.domain.CustomerFeedback;
import com.smi.training.customer.repository.CustomerFeedbackRepository;
import com.smi.training.customer.service.CustomerFeedbackService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CustomerFeedbackResource} REST controller.
 */
@SpringBootTest(classes = CustomerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CustomerFeedbackResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CustomerFeedbackRepository customerFeedbackRepository;

    @Autowired
    private CustomerFeedbackService customerFeedbackService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerFeedbackMockMvc;

    private CustomerFeedback customerFeedback;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerFeedback createEntity(EntityManager em) {
        CustomerFeedback customerFeedback = new CustomerFeedback()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return customerFeedback;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerFeedback createUpdatedEntity(EntityManager em) {
        CustomerFeedback customerFeedback = new CustomerFeedback()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return customerFeedback;
    }

    @BeforeEach
    public void initTest() {
        customerFeedback = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerFeedback() throws Exception {
        int databaseSizeBeforeCreate = customerFeedbackRepository.findAll().size();

        // Create the CustomerFeedback
        restCustomerFeedbackMockMvc.perform(post("/api/customer-feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerFeedback)))
            .andExpect(status().isCreated());

        // Validate the CustomerFeedback in the database
        List<CustomerFeedback> customerFeedbackList = customerFeedbackRepository.findAll();
        assertThat(customerFeedbackList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerFeedback testCustomerFeedback = customerFeedbackList.get(customerFeedbackList.size() - 1);
        assertThat(testCustomerFeedback.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerFeedback.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCustomerFeedbackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerFeedbackRepository.findAll().size();

        // Create the CustomerFeedback with an existing ID
        customerFeedback.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerFeedbackMockMvc.perform(post("/api/customer-feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerFeedback)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerFeedback in the database
        List<CustomerFeedback> customerFeedbackList = customerFeedbackRepository.findAll();
        assertThat(customerFeedbackList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerFeedbackRepository.findAll().size();
        // set the field null
        customerFeedback.setName(null);

        // Create the CustomerFeedback, which fails.

        restCustomerFeedbackMockMvc.perform(post("/api/customer-feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerFeedback)))
            .andExpect(status().isBadRequest());

        List<CustomerFeedback> customerFeedbackList = customerFeedbackRepository.findAll();
        assertThat(customerFeedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerFeedbacks() throws Exception {
        // Initialize the database
        customerFeedbackRepository.saveAndFlush(customerFeedback);

        // Get all the customerFeedbackList
        restCustomerFeedbackMockMvc.perform(get("/api/customer-feedbacks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerFeedback.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getCustomerFeedback() throws Exception {
        // Initialize the database
        customerFeedbackRepository.saveAndFlush(customerFeedback);

        // Get the customerFeedback
        restCustomerFeedbackMockMvc.perform(get("/api/customer-feedbacks/{id}", customerFeedback.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerFeedback.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerFeedback() throws Exception {
        // Get the customerFeedback
        restCustomerFeedbackMockMvc.perform(get("/api/customer-feedbacks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerFeedback() throws Exception {
        // Initialize the database
        customerFeedbackService.save(customerFeedback);

        int databaseSizeBeforeUpdate = customerFeedbackRepository.findAll().size();

        // Update the customerFeedback
        CustomerFeedback updatedCustomerFeedback = customerFeedbackRepository.findById(customerFeedback.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerFeedback are not directly saved in db
        em.detach(updatedCustomerFeedback);
        updatedCustomerFeedback
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restCustomerFeedbackMockMvc.perform(put("/api/customer-feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerFeedback)))
            .andExpect(status().isOk());

        // Validate the CustomerFeedback in the database
        List<CustomerFeedback> customerFeedbackList = customerFeedbackRepository.findAll();
        assertThat(customerFeedbackList).hasSize(databaseSizeBeforeUpdate);
        CustomerFeedback testCustomerFeedback = customerFeedbackList.get(customerFeedbackList.size() - 1);
        assertThat(testCustomerFeedback.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerFeedback.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerFeedback() throws Exception {
        int databaseSizeBeforeUpdate = customerFeedbackRepository.findAll().size();

        // Create the CustomerFeedback

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerFeedbackMockMvc.perform(put("/api/customer-feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerFeedback)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerFeedback in the database
        List<CustomerFeedback> customerFeedbackList = customerFeedbackRepository.findAll();
        assertThat(customerFeedbackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerFeedback() throws Exception {
        // Initialize the database
        customerFeedbackService.save(customerFeedback);

        int databaseSizeBeforeDelete = customerFeedbackRepository.findAll().size();

        // Delete the customerFeedback
        restCustomerFeedbackMockMvc.perform(delete("/api/customer-feedbacks/{id}", customerFeedback.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerFeedback> customerFeedbackList = customerFeedbackRepository.findAll();
        assertThat(customerFeedbackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
