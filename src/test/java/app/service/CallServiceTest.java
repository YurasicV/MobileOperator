package app.service;

import app.entity.Call;
import app.entity.City;
import app.entity.Client;
import app.entity.PhoneNumber;
import app.repository.CallRepository;
import app.repository.CityRepository;
import app.repository.PhoneNumberRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class CallServiceTest {

    @InjectMocks
    CallService manager;

    @Mock
    CityRepository cityRepository;

    @Mock
    CallRepository callRepository;

    @Mock
    PhoneNumberRepository phoneNumberRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void exists() {
        // given
        Call call = new Call();
        call.setId(1L);
        when(callRepository.existsById(call.getId())).thenReturn(true);

        // when
        boolean result = manager.exists(call);

        // then
        assertTrue(result);
    }

    @Test
    public void save() {
        // given
        City dnipro = new City("Dnipro");

        Client ivanov = new Client();
        ivanov.setFullName("Ivanov");

        Client petrov = new Client();
        petrov.setFullName("Petrov");

        PhoneNumber phoneNumberIvanov = new PhoneNumber();
        phoneNumberIvanov.setClient(ivanov);
        phoneNumberIvanov.setPhone("0123456789");

        PhoneNumber phoneNumberPetrov = new PhoneNumber();
        phoneNumberPetrov.setClient(petrov);
        phoneNumberPetrov.setPhone("9876543210");

        ivanov.setPhoneNumbers(new HashSet<>(Collections.singletonList(phoneNumberIvanov)));
        petrov.setPhoneNumbers(new HashSet<>(Collections.singletonList(phoneNumberPetrov)));

        Call call = new Call();
        call.setId(1L);
        call.setCallerPhoneNumber(phoneNumberIvanov);
        call.setRecipientPhoneNumber(phoneNumberPetrov);
        call.setCity(dnipro);

        when(phoneNumberRepository.findFirstByPhone(phoneNumberIvanov.getPhone()))
                .thenReturn(Collections.singletonList(phoneNumberIvanov));
        when(phoneNumberRepository.findFirstByPhone(phoneNumberPetrov.getPhone()))
                .thenReturn(Collections.singletonList(phoneNumberPetrov));
        when(cityRepository.findFirstByName(dnipro.getName()))
                .thenReturn(Collections.singletonList(dnipro));

        // when
        manager.save(call);

        // then
        verify(callRepository, times(1)).save(call);
    }
}