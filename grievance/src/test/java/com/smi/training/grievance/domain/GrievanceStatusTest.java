package com.smi.training.grievance.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.smi.training.grievance.web.rest.TestUtil;

public class GrievanceStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrievanceStatus.class);
        GrievanceStatus grievanceStatus1 = new GrievanceStatus();
        grievanceStatus1.setId(1L);
        GrievanceStatus grievanceStatus2 = new GrievanceStatus();
        grievanceStatus2.setId(grievanceStatus1.getId());
        assertThat(grievanceStatus1).isEqualTo(grievanceStatus2);
        grievanceStatus2.setId(2L);
        assertThat(grievanceStatus1).isNotEqualTo(grievanceStatus2);
        grievanceStatus1.setId(null);
        assertThat(grievanceStatus1).isNotEqualTo(grievanceStatus2);
    }
}
