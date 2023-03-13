package com.artem.korolchuk.study.microblog.controller;

import com.artem.korolchuk.study.microblog.service.ClientService;
import com.artem.korolchuk.study.microblog.entity.Client;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import com.google.cloud.spring.pubsub.support.AcknowledgeablePubsubMessage;
import com.google.pubsub.v1.PubsubMessage;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    
    public static String allowedOrNot = null;
    
    public final static String TOPIC_ID = "publish-topic";
    public final static String SUB_ID = "receive-topic-sub";
    
    private final PubSubTemplate pubSubTemplate;
    
    @Autowired
    private ClientService clientService;

    public ClientController(PubSubTemplate pubSubTemplate) {
        this.pubSubTemplate = pubSubTemplate;
    }
    
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> read() throws InterruptedException {
        this.pubSubTemplate.publish(TOPIC_ID, "Can I do it?");
        
        List<PubsubMessage> messages = this.pubSubTemplate.pullAndAck(SUB_ID, 1, false);
        
        if (messages == null || messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        allowedOrNot = messages.get(0).getData().toStringUtf8();
        
        if (allowedOrNot.equals("false")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
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
