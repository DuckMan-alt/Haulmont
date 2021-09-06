package com.example.haulmont.backend.DAO;

import com.example.haulmont.backend.entities.LoanOffer;

import java.util.List;

public interface LoanOfferDAO {

    List<LoanOffer> allLoanOffers();
    LoanOffer add(LoanOffer loan_offer);
    void delete(LoanOffer loan_offer);
    LoanOffer edit(LoanOffer loan_offer);
}
