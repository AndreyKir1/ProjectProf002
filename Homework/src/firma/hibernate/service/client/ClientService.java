package firma.hibernate.service.client;


import firma.hibernate.entity.Client;

import java.util.List;

public interface ClientService {
    void create(Client client);
    Client read(Long id);
    boolean update(Client client);
    boolean delete(Client client);
    List<Client> getAll();
}
