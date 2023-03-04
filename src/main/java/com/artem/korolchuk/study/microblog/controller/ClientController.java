package com.artem.korolchuk.study.microblog.controller;

import com.artem.korolchuk.study.microblog.service.ClientService;
import com.artem.korolchuk.study.microblog.entity.Client;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    
    @Autowired
    private ClientService clientService;
    
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> read() {
        List<Client> clients = clientService.readAll();
        
        if (clients == null || clients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }
    }
    
    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> read(@PathVariable("id") int id) {
        Client client = clientService.read(id);
        
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(client, HttpStatus.OK);
        }
    }
    
    @PostMapping("/clients")
    public ResponseEntity<?> create(@RequestBody Client client) {
        clientService.create(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PutMapping("/clients/{id}")
    public ResponseEntity<?> update(@RequestBody Client client, @PathVariable("id") int id) {
        if (clientService.update(client, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
    
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (clientService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}
