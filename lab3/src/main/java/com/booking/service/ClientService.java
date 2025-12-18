package com.booking.service;

import com.booking.entity.Client;
import com.booking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    
    public Client getClientById(Integer id) {
        return clientRepository.findById(id).orElse(null);
    }
    
    public void saveClient(Client client) {
        clientRepository.save(client);
    }
    
    public void deleteClient(Integer id) {
        clientRepository.deleteById(id);
    }
}
