package com.booking.ejb;

import com.booking.entity.Client;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ClientServiceBean {

  @PersistenceContext(unitName = "bookingPU")
  private EntityManager em;
  
  public List<Client> getAllClients() {
      return em.createQuery("SELECT c FROM Client c ORDER BY c.id", Client.class)
                .getResultList();
  }
  
  public Client getClientById(Integer id) {
      return em.find(Client.class, id);
  }

  public Client saveClient(Client client) {
      if (client.getId() == null) {
          em.persist(client);
          return client;
      } else {
          return em.merge(client);
      }
  }
  
}
