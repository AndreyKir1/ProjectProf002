package firma.hibernate.service.client;

import firma.hibernate.dao.client.ClientDAO;
import firma.hibernate.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientDAO dao;

    @Override
    public void create(Client client) {
        if (client != null) {
            client.setId(dao.create(client));
        }
    }

    @Override
    public Client read(Long id) {
        if (dao.read(id) != null) {
            return dao.read(id);
        } else {
            return null;
        }
    }

    @Override
    public boolean update(Client update) {
        if (update != null) {
            return dao.update(update);
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Client delete) {
        if (delete != null) {
            return dao.delete(delete);
        } else {
            return false;
        }
    }

    @Override
    public List<Client> getAll() {
        return dao.getAll();
    }
}
