package com.example.haulmont.backend.DAO;

import com.example.haulmont.backend.entities.Client;

import java.util.List;

public interface ClientDAO {

    List<Client> allClients();
    Client add(Client client);
    void delete(Client client);
    Client edit(Client client);
}
