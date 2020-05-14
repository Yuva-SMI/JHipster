package com.smi.training.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A CustomerGrievance.
 */
@Entity
@Table(name = "customer_grievance")
public class CustomerGrievance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "message")
    private String message;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @OneToMany(mappedBy = "customerGrievance")
    private Set<CustomerFeedback> ids = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("customerGrievances")
    private Department department;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("ids")
    private CustomerLogin customerLogin;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("ids")
    private CustomerType customerType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public CustomerGrievance subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public CustomerGrievance message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getImage() {
        return image;
    }

    public CustomerGrievance image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public CustomerGrievance imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Set<CustomerFeedback> getIds() {
        return ids;
    }

    public CustomerGrievance ids(Set<CustomerFeedback> customerFeedbacks) {
        this.ids = customerFeedbacks;
        return this;
    }

    public CustomerGrievance addId(CustomerFeedback customerFeedback) {
        this.ids.add(customerFeedback);
        customerFeedback.setCustomerGrievance(this);
        return this;
    }

    public CustomerGrievance removeId(CustomerFeedback customerFeedback) {
        this.ids.remove(customerFeedback);
        customerFeedback.setCustomerGrievance(null);
        return this;
    }

    public void setIds(Set<CustomerFeedback> customerFeedbacks) {
        this.ids = customerFeedbacks;
    }

    public Department getDepartment() {
        return department;
    }

    public CustomerGrievance department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public CustomerLogin getCustomerLogin() {
        return customerLogin;
    }

    public CustomerGrievance customerLogin(CustomerLogin customerLogin) {
        this.customerLogin = customerLogin;
        return this;
    }

    public void setCustomerLogin(CustomerLogin customerLogin) {
        this.customerLogin = customerLogin;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public CustomerGrievance customerType(CustomerType customerType) {
        this.customerType = customerType;
        return this;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerGrievance)) {
            return false;
        }
        return id != null && id.equals(((CustomerGrievance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CustomerGrievance{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", message='" + getMessage() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
