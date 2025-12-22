package com.booking.controller;

import com.booking.entity.Client;
import com.booking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@RestController
@RequestMapping("/api")
public class ClientRestController {

    @Autowired private ClientService clientService;

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Integer id) {
        Client client = clientService.getClientById(id);
        return client != null ? ResponseEntity.ok(client) : ResponseEntity.notFound().build();
    }

    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        clientService.saveClient(client);
        return ResponseEntity.ok(client);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Integer id, @RequestBody Client client) {
        Client existing = clientService.getClientById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        existing.setName(client.getName());
        existing.setPhone(client.getPhone());
        existing.setEmail(client.getEmail());
        clientService.saveClient(existing);
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Integer id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/xml/clients", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getAllClientsXml() throws Exception {
        List<Client> clients = clientService.getAllClients();

        XmlMapper xmlMapper = new XmlMapper();

        String xmlBody = xmlMapper.writer()
                .withRootName("clients")
                .writeValueAsString(clients);

        String fullXml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<?xml-stylesheet type=\"text/xsl\" href=\"/clients.xsl\"?>\n" +
            xmlBody;

        return ResponseEntity.ok()
                .header("Content-Type", "application/xml; charset=UTF-8")
                .body(fullXml);
    }

    @GetMapping(value = "/xml/clients/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getClientXml(@PathVariable Integer id) throws Exception {
        Client client = clientService.getClientById(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        XmlMapper xmlMapper = new XmlMapper();

        String xmlBody = xmlMapper
                .writer()
                .withRootName("client")
                .writeValueAsString(client);

        String fullXml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<?xml-stylesheet type=\"text/xsl\" href=\"/client.xsl\"?>\n" +
            xmlBody;

        return ResponseEntity.ok()
                .header("Content-Type", "application/xml; charset=UTF-8")
                .body(fullXml);
    }

}