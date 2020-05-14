package com.smi.training.customer.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A CustomerLogin.
 */
@Entity
@Table(name = "customer_login")
public class CustomerLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Column(name = "aadhar", nullable = false)
    private String aadhar;

    @OneToMany(mappedBy = "customerLogin")
    private Set<CustomerGrievance> ids = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public CustomerLogin userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public CustomerLogin password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public CustomerLogin email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public CustomerLogin phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAadhar() {
        return aadhar;
    }

    public CustomerLogin aadhar(String aadhar) {
        this.aadhar = aadhar;
        return this;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public Set<CustomerGrievance> getIds() {
        return ids;
    }

    public CustomerLogin ids(Set<CustomerGrievance> customerGrievances) {
        this.ids = customerGrievances;
        return this;
    }

    public CustomerLogin addId(CustomerGrievance customerGrievance) {
        this.ids.add(customerGrievance);
        customerGrievance.setCustomerLogin(this);
        return this;
    }

    public CustomerLogin removeId(CustomerGrievance customerGrievance) {
        this.ids.remove(customerGrievance);
        customerGrievance.setCustomerLogin(null);
        return this;
    }

    public void setIds(Set<CustomerGrievance> customerGrievances) {
        this.ids = customerGrievances;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerLogin)) {
            return false;
        }
        return id != null && id.equals(((CustomerLogin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CustomerLogin{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", aadhar='" + getAadhar() + "'" +
            "}";
    }
}
