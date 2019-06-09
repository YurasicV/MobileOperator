package app.service;

import app.dto.CallsPerCity;
import app.dto.ClientAndDateRange;
import app.entity.Call;
import app.repository.CallRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ReportServiceTest {

    @InjectMocks
    ReportService manager;

    @Mock
    CallRepository callRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findCallsPerCities() {
        // given
        List<CallsPerCity> list = new ArrayList<>();
        list.add(new CallsPerCity("Dnipro", 3L));
        list.add(new CallsPerCity("Kyiv", 4L));

        when(callRepository.findCallsPerCities())
                .thenReturn(list);

        // when
        List<CallsPerCity> found = manager.findCallsPerCities();

        // then
        assertEquals(list.size(), found.size());
        verify(callRepository, times(1)).findCallsPerCities();
    }

    @Test
    public void findTheLongestCallByClientAndDataRange() {
        // given
        Call call = new Call();
        call.setId(1L);

        ClientAndDateRange params = new ClientAndDateRange();

        when(callRepository.findCallsByClientAndDataRange(params, PageRequest.of(0,1)))
                .thenReturn(Collections.singletonList(call));

        // when
        Call found = manager.findTheLongestCallByClientAndDataRange(params);

        // then
        assertEquals(call.getId(), found.getId());
    }
}
