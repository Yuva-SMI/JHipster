package com.smi.training.grievance.web.rest;

import com.smi.training.grievance.domain.GrievanceStatus;
import com.smi.training.grievance.repository.GrievanceStatusRepository;
import com.smi.training.grievance.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.smi.training.grievance.domain.GrievanceStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GrievanceStatusResource {

    private final Logger log = LoggerFactory.getLogger(GrievanceStatusResource.class);

    private static final String ENTITY_NAME = "grievanceGrievanceStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrievanceStatusRepository grievanceStatusRepository;

    public GrievanceStatusResource(GrievanceStatusRepository grievanceStatusRepository) {
        this.grievanceStatusRepository = grievanceStatusRepository;
    }

    /**
     * {@code POST  /grievance-statuses} : Create a new grievanceStatus.
     *
     * @param grievanceStatus the grievanceStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grievanceStatus, or with status {@code 400 (Bad Request)} if the grievanceStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grievance-statuses")
    public ResponseEntity<GrievanceStatus> createGrievanceStatus(@Valid @RequestBody GrievanceStatus grievanceStatus) throws URISyntaxException {
        log.debug("REST request to save GrievanceStatus : {}", grievanceStatus);
        if (grievanceStatus.getId() != null) {
            throw new BadRequestAlertException("A new grievanceStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrievanceStatus result = grievanceStatusRepository.save(grievanceStatus);
        return ResponseEntity.created(new URI("/api/grievance-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grievance-statuses} : Updates an existing grievanceStatus.
     *
     * @param grievanceStatus the grievanceStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grievanceStatus,
     * or with status {@code 400 (Bad Request)} if the grievanceStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grievanceStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grievance-statuses")
    public ResponseEntity<GrievanceStatus> updateGrievanceStatus(@Valid @RequestBody GrievanceStatus grievanceStatus) throws URISyntaxException {
        log.debug("REST request to update GrievanceStatus : {}", grievanceStatus);
        if (grievanceStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrievanceStatus result = grievanceStatusRepository.save(grievanceStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grievanceStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grievance-statuses} : get all the grievanceStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grievanceStatuses in body.
     */
    @GetMapping("/grievance-statuses")
    public List<GrievanceStatus> getAllGrievanceStatuses() {
        log.debug("REST request to get all GrievanceStatuses");
        return grievanceStatusRepository.findAll();
    }

    /**
     * {@code GET  /grievance-statuses/:id} : get the "id" grievanceStatus.
     *
     * @param id the id of the grievanceStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grievanceStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grievance-statuses/{id}")
    public ResponseEntity<GrievanceStatus> getGrievanceStatus(@PathVariable Long id) {
        log.debug("REST request to get GrievanceStatus : {}", id);
        Optional<GrievanceStatus> grievanceStatus = grievanceStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(grievanceStatus);
    }

    /**
     * {@code DELETE  /grievance-statuses/:id} : delete the "id" grievanceStatus.
     *
     * @param id the id of the grievanceStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grievance-statuses/{id}")
    public ResponseEntity<Void> deleteGrievanceStatus(@PathVariable Long id) {
        log.debug("REST request to delete GrievanceStatus : {}", id);
        grievanceStatusRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
