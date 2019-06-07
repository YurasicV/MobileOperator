package app.service;

import app.entity.Client;
import app.entity.PhoneNumber;
import app.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public boolean exists(Client client) {
        Long clientId = client.getId();
        return clientId != null && clientRepository.existsById(clientId);
    }

    public void save(Client client) {
        for (PhoneNumber phoneNumber: client.getPhoneNumbers()) {
            phoneNumber.setClient(client);
        }
        clientRepository.save(client);
    }
}
