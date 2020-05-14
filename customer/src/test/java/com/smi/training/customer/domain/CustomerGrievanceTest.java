package com.smi.training.customer.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.smi.training.customer.web.rest.TestUtil;

public class CustomerGrievanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerGrievance.class);
        CustomerGrievance customerGrievance1 = new CustomerGrievance();
        customerGrievance1.setId(1L);
        CustomerGrievance customerGrievance2 = new CustomerGrievance();
        customerGrievance2.setId(customerGrievance1.getId());
        assertThat(customerGrievance1).isEqualTo(customerGrievance2);
        customerGrievance2.setId(2L);
        assertThat(customerGrievance1).isNotEqualTo(customerGrievance2);
        customerGrievance1.setId(null);
        assertThat(customerGrievance1).isNotEqualTo(customerGrievance2);
    }
}
