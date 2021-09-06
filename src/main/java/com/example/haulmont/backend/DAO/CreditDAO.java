package com.example.haulmont.backend.DAO;

import com.example.haulmont.backend.entities.Credit;

import java.util.List;

public interface CreditDAO {
    List<Credit> allCredits();
    Credit add(Credit credit);
    void delete(Credit credit);
    Credit edit(Credit credit);
}
