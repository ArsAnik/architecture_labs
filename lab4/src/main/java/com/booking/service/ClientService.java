package com.booking.service;

import com.booking.entity.*;
import com.booking.event.AuditEvent;
import com.booking.repository.AuditChangeDBRepository;
import com.booking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ClientService {
    @Autowired private ClientRepository clientRepository;
    @Autowired private AuditChangeDBRepository auditRepo;
    @Autowired private ApplicationEventPublisher eventPublisher;

    public List<Client> getAllClients() { return clientRepository.findAll(); }
    public Client getClientById(Integer id) { return clientRepository.findById(id).orElse(null); }

    public Client saveClient(Client client) {
        Map<String, Object> oldValues = Map.of();
        
        Client saved = clientRepository.save(client);
        
        Map<String, Object> newValues = Map.of(
            "name", saved.getName(),
            "email", saved.getEmail(),
            "phone", saved.getPhone()
        );
        
        auditRepo.save(AuditChangeDB.create("client", saved.getId(), "create", oldValues, newValues));
        return saved;
    }

    public void deleteClient(Integer id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            Map<String, Object> oldValues = Map.of("name", client.getName(), "email", client.getEmail(), "phone", client.getPhone());
            clientRepository.deleteById(id);
            auditRepo.save(AuditChangeDB.create("client", id, "delete", oldValues, null));
            eventPublisher.publishEvent(new AuditEvent(this, "client", id, "delete", oldValues));
        }
    }
}
