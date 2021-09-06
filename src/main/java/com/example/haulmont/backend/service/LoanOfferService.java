package com.example.haulmont.backend.service;

import com.example.haulmont.backend.DAO.LoanOfferDAO;
import com.example.haulmont.backend.entities.Client;
import com.example.haulmont.backend.entities.LoanOffer;
import com.example.haulmont.backend.repo.LoanOfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class LoanOfferService implements LoanOfferDAO {

    private LoanOfferRepo loanOfferRepo;

    @Autowired
    public void setLoanOfferRepo(LoanOfferRepo loanOfferRepo) {
        this.loanOfferRepo = loanOfferRepo;
    }
    @Override
    public List<LoanOffer> allLoanOffers() {
        return loanOfferRepo.findAll();
    }

    @Override
    public LoanOffer add(LoanOffer loan_offer) {
        return loanOfferRepo.saveAndFlush(loan_offer);
    }

    @Override
    public void delete(LoanOffer loan_offer) {
        loanOfferRepo.delete(loan_offer);
    }

    @Override
    public LoanOffer edit(LoanOffer loan_offer) {
        return loanOfferRepo.saveAndFlush(loan_offer);
    }

    public LoanOffer findById(String id){
        return loanOfferRepo.findClientById(id);
    }

    public List<LoanOffer> findNameOrCreditSumOrCreditLimit(String search){
        return  loanOfferRepo.findByNameOrCreditLimitOrCredit_sum(search);
    }
}
