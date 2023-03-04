package com.artem.korolchuk.study.microblog.service;

import com.artem.korolchuk.study.microblog.repository.ClientRepository;
import com.artem.korolchuk.study.microblog.entity.Client;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClientServiceImpl implements ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Override
    public void create(Client client) {
        clientRepository.save(client);
    }
    
    @Override
    public List<Client> readAll() {
        return clientRepository.findAll();
    }
    
    @Override
    public Client read(int id) {
        Optional<Client> optClient = clientRepository.findById(id);
        
        if (!optClient.isPresent()) {
            return null;
        } else {
            return optClient.get();
        }
    }
    
    @Override
    public boolean update(Client client, int id) {
        Optional<Client> optClient = clientRepository.findById(id);
        
        if (!optClient.isPresent()) {
            return false;
        }
        
        Client dbClient = clientRepository.findById(id).get();
        
        dbClient.setLogin(client.getLogin());
        dbClient.setPassword(client.getPassword());
        
        clientRepository.save(dbClient);
        
        dbClient = clientRepository.findById(id).get();
        
        return (dbClient.getLogin().equals(client.getLogin()) &&
                dbClient.getPassword().equals(client.getPassword()));
    }
            
    @Override
    public boolean delete(int id) {
        Optional<Client> optClient = clientRepository.findById(id);
        
        if (!optClient.isPresent()) {
            return false;
        }
        
        clientRepository.deleteById(id);
        return (!clientRepository.findById(id).isPresent());
    }
    
}
