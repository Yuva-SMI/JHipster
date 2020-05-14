package com.smi.training.customer.web.rest;

import com.smi.training.customer.CustomerApp;
import com.smi.training.customer.domain.CustomerGrievance;
import com.smi.training.customer.domain.Department;
import com.smi.training.customer.domain.CustomerLogin;
import com.smi.training.customer.domain.CustomerType;
import com.smi.training.customer.repository.CustomerGrievanceRepository;
import com.smi.training.customer.service.CustomerGrievanceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CustomerGrievanceResource} REST controller.
 */
@SpringBootTest(classes = CustomerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CustomerGrievanceResourceIT {

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private CustomerGrievanceRepository customerGrievanceRepository;

    @Autowired
    private CustomerGrievanceService customerGrievanceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerGrievanceMockMvc;

    private CustomerGrievance customerGrievance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerGrievance createEntity(EntityManager em) {
        CustomerGrievance customerGrievance = new CustomerGrievance()
            .subject(DEFAULT_SUBJECT)
            .message(DEFAULT_MESSAGE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        // Add required entity
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            department = DepartmentResourceIT.createEntity(em);
            em.persist(department);
            em.flush();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        customerGrievance.setDepartment(department);
        // Add required entity
        CustomerLogin customerLogin;
        if (TestUtil.findAll(em, CustomerLogin.class).isEmpty()) {
            customerLogin = CustomerLoginResourceIT.createEntity(em);
            em.persist(customerLogin);
            em.flush();
        } else {
            customerLogin = TestUtil.findAll(em, CustomerLogin.class).get(0);
        }
        customerGrievance.setCustomerLogin(customerLogin);
        // Add required entity
        CustomerType customerType;
        if (TestUtil.findAll(em, CustomerType.class).isEmpty()) {
            customerType = CustomerTypeResourceIT.createEntity(em);
            em.persist(customerType);
            em.flush();
        } else {
            customerType = TestUtil.findAll(em, CustomerType.class).get(0);
        }
        customerGrievance.setCustomerType(customerType);
        return customerGrievance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerGrievance createUpdatedEntity(EntityManager em) {
        CustomerGrievance customerGrievance = new CustomerGrievance()
            .subject(UPDATED_SUBJECT)
            .message(UPDATED_MESSAGE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        // Add required entity
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            department = DepartmentResourceIT.createUpdatedEntity(em);
            em.persist(department);
            em.flush();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        customerGrievance.setDepartment(department);
        // Add required entity
        CustomerLogin customerLogin;
        if (TestUtil.findAll(em, CustomerLogin.class).isEmpty()) {
            customerLogin = CustomerLoginResourceIT.createUpdatedEntity(em);
            em.persist(customerLogin);
            em.flush();
        } else {
            customerLogin = TestUtil.findAll(em, CustomerLogin.class).get(0);
        }
        customerGrievance.setCustomerLogin(customerLogin);
        // Add required entity
        CustomerType customerType;
        if (TestUtil.findAll(em, CustomerType.class).isEmpty()) {
            customerType = CustomerTypeResourceIT.createUpdatedEntity(em);
            em.persist(customerType);
            em.flush();
        } else {
            customerType = TestUtil.findAll(em, CustomerType.class).get(0);
        }
        customerGrievance.setCustomerType(customerType);
        return customerGrievance;
    }

    @BeforeEach
    public void initTest() {
        customerGrievance = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerGrievance() throws Exception {
        int databaseSizeBeforeCreate = customerGrievanceRepository.findAll().size();

        // Create the CustomerGrievance
        restCustomerGrievanceMockMvc.perform(post("/api/customer-grievances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerGrievance)))
            .andExpect(status().isCreated());

        // Validate the CustomerGrievance in the database
        List<CustomerGrievance> customerGrievanceList = customerGrievanceRepository.findAll();
        assertThat(customerGrievanceList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerGrievance testCustomerGrievance = customerGrievanceList.get(customerGrievanceList.size() - 1);
        assertThat(testCustomerGrievance.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testCustomerGrievance.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testCustomerGrievance.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCustomerGrievance.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createCustomerGrievanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerGrievanceRepository.findAll().size();

        // Create the CustomerGrievance with an existing ID
        customerGrievance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerGrievanceMockMvc.perform(post("/api/customer-grievances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerGrievance)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerGrievance in the database
        List<CustomerGrievance> customerGrievanceList = customerGrievanceRepository.findAll();
        assertThat(customerGrievanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSubjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerGrievanceRepository.findAll().size();
        // set the field null
        customerGrievance.setSubject(null);

        // Create the CustomerGrievance, which fails.

        restCustomerGrievanceMockMvc.perform(post("/api/customer-grievances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerGrievance)))
            .andExpect(status().isBadRequest());

        List<CustomerGrievance> customerGrievanceList = customerGrievanceRepository.findAll();
        assertThat(customerGrievanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerGrievances() throws Exception {
        // Initialize the database
        customerGrievanceRepository.saveAndFlush(customerGrievance);

        // Get all the customerGrievanceList
        restCustomerGrievanceMockMvc.perform(get("/api/customer-grievances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerGrievance.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getCustomerGrievance() throws Exception {
        // Initialize the database
        customerGrievanceRepository.saveAndFlush(customerGrievance);

        // Get the customerGrievance
        restCustomerGrievanceMockMvc.perform(get("/api/customer-grievances/{id}", customerGrievance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerGrievance.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerGrievance() throws Exception {
        // Get the customerGrievance
        restCustomerGrievanceMockMvc.perform(get("/api/customer-grievances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerGrievance() throws Exception {
        // Initialize the database
        customerGrievanceService.save(customerGrievance);

        int databaseSizeBeforeUpdate = customerGrievanceRepository.findAll().size();

        // Update the customerGrievance
        CustomerGrievance updatedCustomerGrievance = customerGrievanceRepository.findById(customerGrievance.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerGrievance are not directly saved in db
        em.detach(updatedCustomerGrievance);
        updatedCustomerGrievance
            .subject(UPDATED_SUBJECT)
            .message(UPDATED_MESSAGE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restCustomerGrievanceMockMvc.perform(put("/api/customer-grievances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerGrievance)))
            .andExpect(status().isOk());

        // Validate the CustomerGrievance in the database
        List<CustomerGrievance> customerGrievanceList = customerGrievanceRepository.findAll();
        assertThat(customerGrievanceList).hasSize(databaseSizeBeforeUpdate);
        CustomerGrievance testCustomerGrievance = customerGrievanceList.get(customerGrievanceList.size() - 1);
        assertThat(testCustomerGrievance.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testCustomerGrievance.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testCustomerGrievance.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCustomerGrievance.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerGrievance() throws Exception {
        int databaseSizeBeforeUpdate = customerGrievanceRepository.findAll().size();

        // Create the CustomerGrievance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerGrievanceMockMvc.perform(put("/api/customer-grievances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerGrievance)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerGrievance in the database
        List<CustomerGrievance> customerGrievanceList = customerGrievanceRepository.findAll();
        assertThat(customerGrievanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerGrievance() throws Exception {
        // Initialize the database
        customerGrievanceService.save(customerGrievance);

        int databaseSizeBeforeDelete = customerGrievanceRepository.findAll().size();

        // Delete the customerGrievance
        restCustomerGrievanceMockMvc.perform(delete("/api/customer-grievances/{id}", customerGrievance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerGrievance> customerGrievanceList = customerGrievanceRepository.findAll();
        assertThat(customerGrievanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
