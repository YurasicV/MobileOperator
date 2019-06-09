package app.service;

import app.entity.City;
import app.repository.CityRepository;
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

public class CityServiceTest {

    @InjectMocks
    CityService manager;

    @Mock
    CityRepository cityRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void exists() {
        // given
        City city = new City("Kyiv");
        city.setId(1L);
        when(cityRepository.existsById(city.getId())).thenReturn(true);
        when(cityRepository.findFirstByName(city.getName()))
                .thenReturn(Collections.singletonList(city));

        // when
        boolean result = manager.exists(city);

        // then
        assertTrue(result);
    }

    @Test
    public void save() {
        // given
        City city = new City("Dnipro");

        // when
        manager.save(city);

        // then
        verify(cityRepository, times(1)).save(city);
    }
}