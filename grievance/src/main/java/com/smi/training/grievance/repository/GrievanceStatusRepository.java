package com.smi.training.grievance.repository;

import com.smi.training.grievance.domain.GrievanceStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GrievanceStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrievanceStatusRepository extends JpaRepository<GrievanceStatus, Long> {
}
