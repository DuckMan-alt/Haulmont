package com.example.haulmont.backend.service;

import com.example.haulmont.backend.DAO.CreditDAO;
import com.example.haulmont.backend.entities.Client;
import com.example.haulmont.backend.entities.Credit;
import com.example.haulmont.backend.repo.ClientRepo;
import com.example.haulmont.backend.repo.CreditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CreditService implements CreditDAO {

    private CreditRepo creditRepo;

    @Autowired
    public void setCreditRepo(CreditRepo creditRepo) {
        this.creditRepo = creditRepo;
    }
    @Override
    public List<Credit> allCredits() {
        return creditRepo.findAll();
    }

    @Override
    public Credit add(Credit credit) {
        return creditRepo.saveAndFlush(credit);
    }

    @Override
    public void delete(Credit credit) {
        creditRepo.delete(credit);
    }

    @Override
    public Credit edit(Credit credit) {
        return creditRepo.saveAndFlush(credit);
    }

    public Credit findById(String id){
        return creditRepo.findClientById(id);
    }

}
