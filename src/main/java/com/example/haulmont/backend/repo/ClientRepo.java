package com.example.haulmont.backend.repo;

import com.example.haulmont.backend.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<Client, String> {

    /*
    @Query("select name from Client  where name = :text")
    Client findByName(@Param("text") String text);
    */

    @Query("SELECT cl FROM Client cl WHERE cl.name = ?1")
    List<Client> findClientByName(String name);

    @Query("SELECT cl FROM Client cl WHERE cl.id = ?1")
    Client findClientById(String id);

    @Query("SELECT cl FROM Client cl WHERE cl.email = ?1")
    Client findClientByEmail(String email);

    @Query("SELECT cl FROM Client cl WHERE cl.passport_number = ?1")
    Client findClientByPassport_number(String passport_number);

    @Query("SELECT cl FROM Client cl WHERE cl.name = ?1 or cl.email = ?1")
    Client findClientByNameAndEmail(String value);

    @Query("select cl from Client  cl where cl.email like %?1% or cl.name like %?1%")
    List<Client> findByEmailStartsWithIgnoreCase(String email);

}
