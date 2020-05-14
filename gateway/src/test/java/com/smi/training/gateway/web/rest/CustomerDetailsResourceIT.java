package com.smi.training.gateway.web.rest;

import com.smi.training.gateway.GatewayApp;
import com.smi.training.gateway.domain.CustomerDetails;
import com.smi.training.gateway.domain.CustomerLogin;
import com.smi.training.gateway.repository.CustomerDetailsRepository;
import com.smi.training.gateway.service.CustomerDetailsService;

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

import com.smi.training.gateway.domain.enumeration.Gender;
/**
 * Integration tests for the {@link CustomerDetailsResource} REST controller.
 */
@SpringBootTest(classes = GatewayApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CustomerDetailsResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerDetailsMockMvc;

    private CustomerDetails customerDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerDetails createEntity(EntityManager em) {
        CustomerDetails customerDetails = new CustomerDetails()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .gender(DEFAULT_GENDER)
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY);
        // Add required entity
        CustomerLogin customerLogin;
        if (TestUtil.findAll(em, CustomerLogin.class).isEmpty()) {
            customerLogin = CustomerLoginResourceIT.createEntity(em);
            em.persist(customerLogin);
            em.flush();
        } else {
            customerLogin = TestUtil.findAll(em, CustomerLogin.class).get(0);
        }
        customerDetails.setCustomerLogin(customerLogin);
        return customerDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerDetails createUpdatedEntity(EntityManager em) {
        CustomerDetails customerDetails = new CustomerDetails()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY);
        // Add required entity
        CustomerLogin customerLogin;
        if (TestUtil.findAll(em, CustomerLogin.class).isEmpty()) {
            customerLogin = CustomerLoginResourceIT.createUpdatedEntity(em);
            em.persist(customerLogin);
            em.flush();
        } else {
            customerLogin = TestUtil.findAll(em, CustomerLogin.class).get(0);
        }
        customerDetails.setCustomerLogin(customerLogin);
        return customerDetails;
    }

    @BeforeEach
    public void initTest() {
        customerDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerDetails() throws Exception {
        int databaseSizeBeforeCreate = customerDetailsRepository.findAll().size();

        // Create the CustomerDetails
        restCustomerDetailsMockMvc.perform(post("/api/customer-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDetails)))
            .andExpect(status().isCreated());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerDetails testCustomerDetails = customerDetailsList.get(customerDetailsList.size() - 1);
        assertThat(testCustomerDetails.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCustomerDetails.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCustomerDetails.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testCustomerDetails.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testCustomerDetails.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testCustomerDetails.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCustomerDetails.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCustomerDetails.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void createCustomerDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerDetailsRepository.findAll().size();

        // Create the CustomerDetails with an existing ID
        customerDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerDetailsMockMvc.perform(post("/api/customer-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDetails)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerDetailsRepository.findAll().size();
        // set the field null
        customerDetails.setFirstName(null);

        // Create the CustomerDetails, which fails.

        restCustomerDetailsMockMvc.perform(post("/api/customer-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDetails)))
            .andExpect(status().isBadRequest());

        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerDetailsRepository.findAll().size();
        // set the field null
        customerDetails.setLastName(null);

        // Create the CustomerDetails, which fails.

        restCustomerDetailsMockMvc.perform(post("/api/customer-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDetails)))
            .andExpect(status().isBadRequest());

        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerDetailsRepository.findAll().size();
        // set the field null
        customerDetails.setGender(null);

        // Create the CustomerDetails, which fails.

        restCustomerDetailsMockMvc.perform(post("/api/customer-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDetails)))
            .andExpect(status().isBadRequest());

        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressLine1IsRequired() throws Exception {
        int databaseSizeBeforeTest = customerDetailsRepository.findAll().size();
        // set the field null
        customerDetails.setAddressLine1(null);

        // Create the CustomerDetails, which fails.

        restCustomerDetailsMockMvc.perform(post("/api/customer-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDetails)))
            .andExpect(status().isBadRequest());

        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerDetailsRepository.findAll().size();
        // set the field null
        customerDetails.setCity(null);

        // Create the CustomerDetails, which fails.

        restCustomerDetailsMockMvc.perform(post("/api/customer-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDetails)))
            .andExpect(status().isBadRequest());

        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerDetailsRepository.findAll().size();
        // set the field null
        customerDetails.setState(null);

        // Create the CustomerDetails, which fails.

        restCustomerDetailsMockMvc.perform(post("/api/customer-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDetails)))
            .andExpect(status().isBadRequest());

        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerDetailsRepository.findAll().size();
        // set the field null
        customerDetails.setCountry(null);

        // Create the CustomerDetails, which fails.

        restCustomerDetailsMockMvc.perform(post("/api/customer-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDetails)))
            .andExpect(status().isBadRequest());

        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerDetails() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList
        restCustomerDetailsMockMvc.perform(get("/api/customer-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1)))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }
    
    @Test
    @Transactional
    public void getCustomerDetails() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get the customerDetails
        restCustomerDetailsMockMvc.perform(get("/api/customer-details/{id}", customerDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerDetails.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerDetails() throws Exception {
        // Get the customerDetails
        restCustomerDetailsMockMvc.perform(get("/api/customer-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerDetails() throws Exception {
        // Initialize the database
        customerDetailsService.save(customerDetails);

        int databaseSizeBeforeUpdate = customerDetailsRepository.findAll().size();

        // Update the customerDetails
        CustomerDetails updatedCustomerDetails = customerDetailsRepository.findById(customerDetails.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerDetails are not directly saved in db
        em.detach(updatedCustomerDetails);
        updatedCustomerDetails
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY);

        restCustomerDetailsMockMvc.perform(put("/api/customer-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerDetails)))
            .andExpect(status().isOk());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeUpdate);
        CustomerDetails testCustomerDetails = customerDetailsList.get(customerDetailsList.size() - 1);
        assertThat(testCustomerDetails.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCustomerDetails.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCustomerDetails.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testCustomerDetails.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testCustomerDetails.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testCustomerDetails.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustomerDetails.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCustomerDetails.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerDetails() throws Exception {
        int databaseSizeBeforeUpdate = customerDetailsRepository.findAll().size();

        // Create the CustomerDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerDetailsMockMvc.perform(put("/api/customer-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDetails)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerDetails() throws Exception {
        // Initialize the database
        customerDetailsService.save(customerDetails);

        int databaseSizeBeforeDelete = customerDetailsRepository.findAll().size();

        // Delete the customerDetails
        restCustomerDetailsMockMvc.perform(delete("/api/customer-details/{id}", customerDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
