package app.service;

import app.entity.City;
import app.entity.PhoneNumber;
import app.repository.PhoneNumberRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class PhoneNumberServiceTest {

    @InjectMocks
    PhoneNumberService manager;

    @Mock
    PhoneNumberRepository phoneNumberRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void exists() {
        // given
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPhone("0123456789");
        when(phoneNumberRepository.findFirstByPhone(phoneNumber.getPhone()))
                .thenReturn(Collections.singletonList(phoneNumber));

        // when
        boolean result = manager.exists(phoneNumber);

        // then
        assertTrue(result);
    }

    @Test
    public void save() {
        // given
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPhone("0123456789");

        // when
        manager.save(phoneNumber);

        // then
        verify(phoneNumberRepository, times(1)).save(phoneNumber);
    }

    @Test
    public void findFirstByPhone() {
        // given
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPhone("0123456789");
        when(phoneNumberRepository.findFirstByPhone(phoneNumber.getPhone()))
                .thenReturn(Collections.singletonList(phoneNumber));

        // when
        List<PhoneNumber> phoneNumbers = manager.findFirstByPhone(phoneNumber.getPhone());

        // then
        assertEquals(1, phoneNumbers.size());
        assertEquals(phoneNumbers.get(0), phoneNumber);
    }
}