package com.smi.training.customer.web.rest;

import com.smi.training.customer.CustomerApp;
import com.smi.training.customer.domain.CustomerLogin;
import com.smi.training.customer.repository.CustomerLoginRepository;
import com.smi.training.customer.service.CustomerLoginService;

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
 * Integration tests for the {@link CustomerLoginResource} REST controller.
 */
@SpringBootTest(classes = CustomerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CustomerLoginResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "^r*]q@]$&N.jeC*{f";
    private static final String UPDATED_EMAIL = "%@g.7.=V]c&";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR = "BBBBBBBBBB";

    @Autowired
    private CustomerLoginRepository customerLoginRepository;

    @Autowired
    private CustomerLoginService customerLoginService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerLoginMockMvc;

    private CustomerLogin customerLogin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerLogin createEntity(EntityManager em) {
        CustomerLogin customerLogin = new CustomerLogin()
            .userName(DEFAULT_USER_NAME)
            .password(DEFAULT_PASSWORD)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .aadhar(DEFAULT_AADHAR);
        return customerLogin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerLogin createUpdatedEntity(EntityManager em) {
        CustomerLogin customerLogin = new CustomerLogin()
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .aadhar(UPDATED_AADHAR);
        return customerLogin;
    }

    @BeforeEach
    public void initTest() {
        customerLogin = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerLogin() throws Exception {
        int databaseSizeBeforeCreate = customerLoginRepository.findAll().size();

        // Create the CustomerLogin
        restCustomerLoginMockMvc.perform(post("/api/customer-logins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerLogin)))
            .andExpect(status().isCreated());

        // Validate the CustomerLogin in the database
        List<CustomerLogin> customerLoginList = customerLoginRepository.findAll();
        assertThat(customerLoginList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerLogin testCustomerLogin = customerLoginList.get(customerLoginList.size() - 1);
        assertThat(testCustomerLogin.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testCustomerLogin.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testCustomerLogin.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomerLogin.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCustomerLogin.getAadhar()).isEqualTo(DEFAULT_AADHAR);
    }

    @Test
    @Transactional
    public void createCustomerLoginWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerLoginRepository.findAll().size();

        // Create the CustomerLogin with an existing ID
        customerLogin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerLoginMockMvc.perform(post("/api/customer-logins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerLogin)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerLogin in the database
        List<CustomerLogin> customerLoginList = customerLoginRepository.findAll();
        assertThat(customerLoginList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerLoginRepository.findAll().size();
        // set the field null
        customerLogin.setUserName(null);

        // Create the CustomerLogin, which fails.

        restCustomerLoginMockMvc.perform(post("/api/customer-logins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerLogin)))
            .andExpect(status().isBadRequest());

        List<CustomerLogin> customerLoginList = customerLoginRepository.findAll();
        assertThat(customerLoginList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerLoginRepository.findAll().size();
        // set the field null
        customerLogin.setPassword(null);

        // Create the CustomerLogin, which fails.

        restCustomerLoginMockMvc.perform(post("/api/customer-logins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerLogin)))
            .andExpect(status().isBadRequest());

        List<CustomerLogin> customerLoginList = customerLoginRepository.findAll();
        assertThat(customerLoginList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerLoginRepository.findAll().size();
        // set the field null
        customerLogin.setEmail(null);

        // Create the CustomerLogin, which fails.

        restCustomerLoginMockMvc.perform(post("/api/customer-logins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerLogin)))
            .andExpect(status().isBadRequest());

        List<CustomerLogin> customerLoginList = customerLoginRepository.findAll();
        assertThat(customerLoginList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerLoginRepository.findAll().size();
        // set the field null
        customerLogin.setPhone(null);

        // Create the CustomerLogin, which fails.

        restCustomerLoginMockMvc.perform(post("/api/customer-logins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerLogin)))
            .andExpect(status().isBadRequest());

        List<CustomerLogin> customerLoginList = customerLoginRepository.findAll();
        assertThat(customerLoginList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAadharIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerLoginRepository.findAll().size();
        // set the field null
        customerLogin.setAadhar(null);

        // Create the CustomerLogin, which fails.

        restCustomerLoginMockMvc.perform(post("/api/customer-logins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerLogin)))
            .andExpect(status().isBadRequest());

        List<CustomerLogin> customerLoginList = customerLoginRepository.findAll();
        assertThat(customerLoginList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerLogins() throws Exception {
        // Initialize the database
        customerLoginRepository.saveAndFlush(customerLogin);

        // Get all the customerLoginList
        restCustomerLoginMockMvc.perform(get("/api/customer-logins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerLogin.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].aadhar").value(hasItem(DEFAULT_AADHAR)));
    }
    
    @Test
    @Transactional
    public void getCustomerLogin() throws Exception {
        // Initialize the database
        customerLoginRepository.saveAndFlush(customerLogin);

        // Get the customerLogin
        restCustomerLoginMockMvc.perform(get("/api/customer-logins/{id}", customerLogin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerLogin.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.aadhar").value(DEFAULT_AADHAR));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerLogin() throws Exception {
        // Get the customerLogin
        restCustomerLoginMockMvc.perform(get("/api/customer-logins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerLogin() throws Exception {
        // Initialize the database
        customerLoginService.save(customerLogin);

        int databaseSizeBeforeUpdate = customerLoginRepository.findAll().size();

        // Update the customerLogin
        CustomerLogin updatedCustomerLogin = customerLoginRepository.findById(customerLogin.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerLogin are not directly saved in db
        em.detach(updatedCustomerLogin);
        updatedCustomerLogin
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .aadhar(UPDATED_AADHAR);

        restCustomerLoginMockMvc.perform(put("/api/customer-logins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerLogin)))
            .andExpect(status().isOk());

        // Validate the CustomerLogin in the database
        List<CustomerLogin> customerLoginList = customerLoginRepository.findAll();
        assertThat(customerLoginList).hasSize(databaseSizeBeforeUpdate);
        CustomerLogin testCustomerLogin = customerLoginList.get(customerLoginList.size() - 1);
        assertThat(testCustomerLogin.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testCustomerLogin.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testCustomerLogin.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerLogin.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustomerLogin.getAadhar()).isEqualTo(UPDATED_AADHAR);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerLogin() throws Exception {
        int databaseSizeBeforeUpdate = customerLoginRepository.findAll().size();

        // Create the CustomerLogin

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerLoginMockMvc.perform(put("/api/customer-logins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerLogin)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerLogin in the database
        List<CustomerLogin> customerLoginList = customerLoginRepository.findAll();
        assertThat(customerLoginList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerLogin() throws Exception {
        // Initialize the database
        customerLoginService.save(customerLogin);

        int databaseSizeBeforeDelete = customerLoginRepository.findAll().size();

        // Delete the customerLogin
        restCustomerLoginMockMvc.perform(delete("/api/customer-logins/{id}", customerLogin.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerLogin> customerLoginList = customerLoginRepository.findAll();
        assertThat(customerLoginList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
