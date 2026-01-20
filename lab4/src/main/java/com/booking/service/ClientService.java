package com.booking.service;

import com.booking.entity.*;
import com.booking.config.JmsConfig;
import com.booking.repository.ClientRepository;
import com.booking.publisher.AuditMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ClientService {
    @Autowired private ClientRepository clientRepository;
    @Autowired private AuditMessagePublisher auditPublisher;

    public List<Client> getAllClients() { return clientRepository.findAll(); }
    public Client getClientById(Integer id) { return clientRepository.findById(id).orElse(null); }

    public Client saveClient(Client client) {      
        Client saved = clientRepository.save(client);
        
        Map<String, Object> newValues = Map.of(
            "name", saved.getName(),
            "email", saved.getEmail(),
            "phone", saved.getPhone()
        );
        auditPublisher.publishAudit("client", saved.getId(), "create", null, newValues);
        return saved;
    }

    public void deleteClient(Integer id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            Map<String, Object> oldValues = Map.of("name", client.getName(), "email", client.getEmail(), "phone", client.getPhone());
            clientRepository.deleteById(id);
            auditPublisher.publishAudit("client", id, "delete", oldValues, null);
        }
    }
}
