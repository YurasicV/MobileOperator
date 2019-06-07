package app.controller;

import app.entity.Client;
import app.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ClientController {
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/client/")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        if (clientService.exists(client)) {
            return new ResponseEntity<Client>(client, HttpStatus.CONFLICT);
        }
        clientService.save(client);
        return new ResponseEntity<Client>(client, HttpStatus.CREATED);
    }
}