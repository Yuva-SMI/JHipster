package com.smi.training.customer.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.smi.training.customer.web.rest.TestUtil;

public class CustomerFeedbackTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerFeedback.class);
        CustomerFeedback customerFeedback1 = new CustomerFeedback();
        customerFeedback1.setId(1L);
        CustomerFeedback customerFeedback2 = new CustomerFeedback();
        customerFeedback2.setId(customerFeedback1.getId());
        assertThat(customerFeedback1).isEqualTo(customerFeedback2);
        customerFeedback2.setId(2L);
        assertThat(customerFeedback1).isNotEqualTo(customerFeedback2);
        customerFeedback1.setId(null);
        assertThat(customerFeedback1).isNotEqualTo(customerFeedback2);
    }
}
