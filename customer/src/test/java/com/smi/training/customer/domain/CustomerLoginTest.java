package com.smi.training.customer.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.smi.training.customer.web.rest.TestUtil;

public class CustomerLoginTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerLogin.class);
        CustomerLogin customerLogin1 = new CustomerLogin();
        customerLogin1.setId(1L);
        CustomerLogin customerLogin2 = new CustomerLogin();
        customerLogin2.setId(customerLogin1.getId());
        assertThat(customerLogin1).isEqualTo(customerLogin2);
        customerLogin2.setId(2L);
        assertThat(customerLogin1).isNotEqualTo(customerLogin2);
        customerLogin1.setId(null);
        assertThat(customerLogin1).isNotEqualTo(customerLogin2);
    }
}
