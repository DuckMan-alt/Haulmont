package com.example.haulmont.backend.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "Client")
@Table(name = "client",
        uniqueConstraints = {
                @UniqueConstraint(name = "client_email_unique", columnNames = "email"),
                @UniqueConstraint(name = "client_passport_number_unique", columnNames = "passport_number")
        }
        )
public class Client {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false,columnDefinition = "CHAR(36)")
    private String id;

    @Column(
            name = "name",
            nullable = false,
            length = 50,
            columnDefinition = "varchar(50)"
    )
    private String name;

    @Column(
            name = "phone_number",
            columnDefinition = "varchar(11)"
    )
    private String phone_number;

    @Column(name = "email",
            nullable = false,
            columnDefinition = "varchar(255)")
    private String email;

    @Column(
            name = "passport_number",
            length = 10,
            columnDefinition = "varchar(10)"
    )
    private String passport_number;

    public Client() {
    }

    public Client(String name, String phone_number, String email, String passport_number) {
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.passport_number = passport_number;
    }

    public Client(String id, String name, String phone_number, String email, String passport_number) {
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.passport_number = passport_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassport_number() {
        return passport_number;
    }

    public void setPassport_number(String passport_number) {
        this.passport_number = passport_number;
    }



    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone_number=" + phone_number +
                ", email='" + email + '\'' +
                ", passport_number=" + passport_number +
                '}';
    }
}
