package app.controller;

import app.entity.Client;
import app.service.ClientService;
import app.validation.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ClientController {
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/client/")
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) throws CustomException {
        if (clientService.exists(client)) {
            throw new CustomException(String.format("Client %s already exists",
                    client.getFullName()), HttpStatus.CONFLICT);
        }
        clientService.save(client);
        return new ResponseEntity<Client>(client, HttpStatus.CREATED);
    }
}