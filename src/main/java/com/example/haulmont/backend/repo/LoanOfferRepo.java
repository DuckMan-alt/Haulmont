package com.example.haulmont.backend.repo;

import com.example.haulmont.backend.entities.Client;
import com.example.haulmont.backend.entities.LoanOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanOfferRepo extends JpaRepository<LoanOffer,String> {


    @Query("SELECT ln FROM Loan_offer ln WHERE ln.id = ?1")
    LoanOffer findClientById(String id);

    @Query("select ln from Loan_offer  ln where ln.client.name like %?1% or ln.credit.credit_limit like %?1% or ln.credit_sum like %?1%")
    List<LoanOffer> findByNameOrCreditLimitOrCredit_sum(String search);
}
