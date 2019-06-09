package app.service;

import app.entity.Client;
import app.entity.PhoneNumber;
import app.repository.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ClientServiceTest {

    @InjectMocks
    ClientService manager;

    @Mock
    ClientRepository clientRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void exists() {
        // given
        Client client = new Client();
        client.setId(1L);
        when(clientRepository.existsById(client.getId())).thenReturn(true);

        // when
        boolean result = manager.exists(client);

        // then
        assertTrue(result);
    }

    @Test
    public void save() {
        // given
        Client client = new Client();
        client.setFullName("Ivanov");

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setClient(client);
        phoneNumber.setPhone("0123456789");

        client.setPhoneNumbers(Collections.singleton(phoneNumber));

        // when
        manager.save(client);

        // then
        verify(clientRepository, times(1)).save(client);
    }
}