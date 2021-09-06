package com.example.haulmont.backend.service;

import com.example.haulmont.backend.DAO.ClientDAO;
import com.example.haulmont.backend.entities.Client;
import com.example.haulmont.backend.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements ClientDAO{

    private ClientRepo clientRepo;

    @Autowired
    public void setClientRepo(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }


    @Override
    public List<Client> allClients() {
        return clientRepo.findAll();
    }

    @Override
    public Client add(Client client) {
        return clientRepo.saveAndFlush(client);
    }

    @Override
    public void delete(Client client) {
        clientRepo.delete(client);
    }

    @Override
    public Client edit(Client client) {
        return clientRepo.saveAndFlush(client);
    }


    public List<Client> findByName(String name){
        return clientRepo.findClientByName(name);
    }

    public Client findById(String id){
        return clientRepo.findClientById(id);
    }

    public Client findByEmail(String email){
        return clientRepo.findClientByEmail(email);
    }

    public Client findByNameAndEmail(String value){
        return clientRepo.findClientByNameAndEmail(value);
    }


    public Client findByPassport_number(String pasnum){
        return clientRepo.findClientByPassport_number(pasnum);
    }

    public List<Client> findByEmailStartsWithIgnoreCase(String email){
        return clientRepo.findByEmailStartsWithIgnoreCase(email.toLowerCase());
    }





}
