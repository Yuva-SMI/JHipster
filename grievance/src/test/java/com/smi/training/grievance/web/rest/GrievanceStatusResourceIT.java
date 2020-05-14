package com.smi.training.grievance.web.rest;

import com.smi.training.grievance.GrievanceApp;
import com.smi.training.grievance.domain.GrievanceStatus;
import com.smi.training.grievance.repository.GrievanceStatusRepository;

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
 * Integration tests for the {@link GrievanceStatusResource} REST controller.
 */
@SpringBootTest(classes = GrievanceApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class GrievanceStatusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private GrievanceStatusRepository grievanceStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrievanceStatusMockMvc;

    private GrievanceStatus grievanceStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrievanceStatus createEntity(EntityManager em) {
        GrievanceStatus grievanceStatus = new GrievanceStatus()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS);
        return grievanceStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrievanceStatus createUpdatedEntity(EntityManager em) {
        GrievanceStatus grievanceStatus = new GrievanceStatus()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        return grievanceStatus;
    }

    @BeforeEach
    public void initTest() {
        grievanceStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrievanceStatus() throws Exception {
        int databaseSizeBeforeCreate = grievanceStatusRepository.findAll().size();

        // Create the GrievanceStatus
        restGrievanceStatusMockMvc.perform(post("/api/grievance-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grievanceStatus)))
            .andExpect(status().isCreated());

        // Validate the GrievanceStatus in the database
        List<GrievanceStatus> grievanceStatusList = grievanceStatusRepository.findAll();
        assertThat(grievanceStatusList).hasSize(databaseSizeBeforeCreate + 1);
        GrievanceStatus testGrievanceStatus = grievanceStatusList.get(grievanceStatusList.size() - 1);
        assertThat(testGrievanceStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGrievanceStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGrievanceStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createGrievanceStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grievanceStatusRepository.findAll().size();

        // Create the GrievanceStatus with an existing ID
        grievanceStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrievanceStatusMockMvc.perform(post("/api/grievance-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grievanceStatus)))
            .andExpect(status().isBadRequest());

        // Validate the GrievanceStatus in the database
        List<GrievanceStatus> grievanceStatusList = grievanceStatusRepository.findAll();
        assertThat(grievanceStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = grievanceStatusRepository.findAll().size();
        // set the field null
        grievanceStatus.setName(null);

        // Create the GrievanceStatus, which fails.

        restGrievanceStatusMockMvc.perform(post("/api/grievance-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grievanceStatus)))
            .andExpect(status().isBadRequest());

        List<GrievanceStatus> grievanceStatusList = grievanceStatusRepository.findAll();
        assertThat(grievanceStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = grievanceStatusRepository.findAll().size();
        // set the field null
        grievanceStatus.setStatus(null);

        // Create the GrievanceStatus, which fails.

        restGrievanceStatusMockMvc.perform(post("/api/grievance-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grievanceStatus)))
            .andExpect(status().isBadRequest());

        List<GrievanceStatus> grievanceStatusList = grievanceStatusRepository.findAll();
        assertThat(grievanceStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGrievanceStatuses() throws Exception {
        // Initialize the database
        grievanceStatusRepository.saveAndFlush(grievanceStatus);

        // Get all the grievanceStatusList
        restGrievanceStatusMockMvc.perform(get("/api/grievance-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grievanceStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getGrievanceStatus() throws Exception {
        // Initialize the database
        grievanceStatusRepository.saveAndFlush(grievanceStatus);

        // Get the grievanceStatus
        restGrievanceStatusMockMvc.perform(get("/api/grievance-statuses/{id}", grievanceStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grievanceStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingGrievanceStatus() throws Exception {
        // Get the grievanceStatus
        restGrievanceStatusMockMvc.perform(get("/api/grievance-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrievanceStatus() throws Exception {
        // Initialize the database
        grievanceStatusRepository.saveAndFlush(grievanceStatus);

        int databaseSizeBeforeUpdate = grievanceStatusRepository.findAll().size();

        // Update the grievanceStatus
        GrievanceStatus updatedGrievanceStatus = grievanceStatusRepository.findById(grievanceStatus.getId()).get();
        // Disconnect from session so that the updates on updatedGrievanceStatus are not directly saved in db
        em.detach(updatedGrievanceStatus);
        updatedGrievanceStatus
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);

        restGrievanceStatusMockMvc.perform(put("/api/grievance-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrievanceStatus)))
            .andExpect(status().isOk());

        // Validate the GrievanceStatus in the database
        List<GrievanceStatus> grievanceStatusList = grievanceStatusRepository.findAll();
        assertThat(grievanceStatusList).hasSize(databaseSizeBeforeUpdate);
        GrievanceStatus testGrievanceStatus = grievanceStatusList.get(grievanceStatusList.size() - 1);
        assertThat(testGrievanceStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGrievanceStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGrievanceStatus.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingGrievanceStatus() throws Exception {
        int databaseSizeBeforeUpdate = grievanceStatusRepository.findAll().size();

        // Create the GrievanceStatus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrievanceStatusMockMvc.perform(put("/api/grievance-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grievanceStatus)))
            .andExpect(status().isBadRequest());

        // Validate the GrievanceStatus in the database
        List<GrievanceStatus> grievanceStatusList = grievanceStatusRepository.findAll();
        assertThat(grievanceStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGrievanceStatus() throws Exception {
        // Initialize the database
        grievanceStatusRepository.saveAndFlush(grievanceStatus);

        int databaseSizeBeforeDelete = grievanceStatusRepository.findAll().size();

        // Delete the grievanceStatus
        restGrievanceStatusMockMvc.perform(delete("/api/grievance-statuses/{id}", grievanceStatus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrievanceStatus> grievanceStatusList = grievanceStatusRepository.findAll();
        assertThat(grievanceStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
