package com.example.haulmont.backend.repo;

import com.example.haulmont.backend.entities.Client;
import com.example.haulmont.backend.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepo extends JpaRepository<Credit,String> {

    @Query("SELECT cr FROM Credit cr WHERE cr.id = ?1")
    Credit findClientById(String id);


}
