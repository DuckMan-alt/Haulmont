package com.example.haulmont.backend.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "Credit")
@Table(name = "credit")
public class Credit {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false,columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "credit_limit",
            nullable = false
    )
    private String credit_limit;
    @Column(name = "interest_rate",
            nullable = false
    )
    private Double interest_rate;

    public Credit() {
    }

    public Credit(String credit_limit, Double interest_rate) {
        this.credit_limit = credit_limit;
        this.interest_rate = interest_rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(String credit_limit) {
        this.credit_limit = credit_limit;
    }

    public Double getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(Double interest_rate) {
        this.interest_rate = interest_rate;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id='" + id + '\'' +
                ", credit_limit=" + credit_limit +
                ", interest_rate=" + interest_rate +
                '}';
    }
}
