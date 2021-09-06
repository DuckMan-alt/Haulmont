package com.example.haulmont.backend.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Loan_offer")
@Table(name = "loan_offer")
public class LoanOffer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false,columnDefinition = "CHAR(36)")
    private String id;
    @ManyToOne
    @JoinColumn(
            name = "client_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "client_offer_fk"
            )
    )
    private Client client;
    @ManyToOne
    @JoinColumn(
            name = "credit_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "credit_offer_fk"
            )
    )
    private Credit credit;
    @Column(name = "credit_sum",
            nullable = false
    )
    private String credit_sum;
    @Column(name = "time_interval",
            nullable = false
    )
    private Integer time_interval;
    @Column(name = "date",
            nullable = false
    )
    private LocalDate date;

    public LoanOffer(Client client, Credit credit, String credit_sum, Integer time_interval, LocalDate date) {
        this.client = client;
        this.credit = credit;
        this.credit_sum = credit_sum;
        this.time_interval = time_interval;
        this.date = date;
    }

    public LoanOffer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public String getCredit_sum() {
        return credit_sum;
    }

    public void setCredit_sum(String credit_sum) {
        this.credit_sum = credit_sum;
    }

    public Integer getTime_interval() {
        return time_interval;
    }

    public void setTime_interval(Integer time_interval) {
        this.time_interval = time_interval;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNameClient(){
        return  client.getName();
    }
    public Double getInterestRateClient(){
        return  credit.getInterest_rate();
    }
    public String getCreditLimit(){return credit.getCredit_limit();}


    @Override
    public String toString() {
        return "Loan_offer{" +
                "id='" + id + '\'' +
                ", client=" + client +
                ", credit=" + credit +
                ", credit_sum=" + credit_sum +
                ", time_interval=" + time_interval +
                ", date=" + date +
                '}';
    }
}
