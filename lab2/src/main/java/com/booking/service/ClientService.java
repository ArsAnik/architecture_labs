package com.booking.service;

import com.booking.entity.Client;
import com.booking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }
    
    public void deleteClient(Integer id) {
        clientRepository.deleteById(id);
    }
}
