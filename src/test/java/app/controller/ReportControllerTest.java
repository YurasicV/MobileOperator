package app.controller;

import app.dto.CallsPerCity;
import app.dto.ClientAndDateRange;
import app.entity.Call;
import app.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(ReportController.class)
public class ReportControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReportService reportService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void listCallsPerCitiesNotEmptyTest() throws Exception {

        List<CallsPerCity> list = new ArrayList<>();
        list.add(new CallsPerCity("Dnipro", 2L));
        list.add(new CallsPerCity("Kyiv", 5L));

        given(reportService.findCallsPerCities()).willReturn(list);

        mvc.perform(get("/api//calls-per-cities/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(hasSize(2))))
                .andExpect(jsonPath("$[0].cityName", is("Dnipro")));
    }

    @Test
    public void listCallsPerCitiesEmptyTest() throws Exception {

        List<CallsPerCity> list = new ArrayList<>();

        given(reportService.findCallsPerCities()).willReturn(list);

        mvc.perform(get("/api//calls-per-cities/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTheLongestCallNotEmptyTest() throws Exception {

        Call call = new Call();
        call.setId(1L);

        ClientAndDateRange clientAndDateRange = new ClientAndDateRange();
        clientAndDateRange.setClientId(1L);
        clientAndDateRange.setDateFrom(new Date());
        clientAndDateRange.setDateTo(new Date());

        given(reportService.findTheLongestCallByClientAndDataRange(ArgumentMatchers
                .any(ClientAndDateRange.class))).willReturn(call);

        mvc.perform(post("/api//the-longest-call/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientAndDateRange)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void getTheLongestCallEmptyTest() throws Exception {

        ClientAndDateRange clientAndDateRange = new ClientAndDateRange();
        clientAndDateRange.setClientId(1L);
        clientAndDateRange.setDateFrom(new Date());
        clientAndDateRange.setDateTo(new Date());

        given(reportService.findTheLongestCallByClientAndDataRange(ArgumentMatchers
                .any(ClientAndDateRange.class))).willReturn(null);

        mvc.perform(post("/api//the-longest-call/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientAndDateRange)))
                .andExpect(status().isNotFound());
    }
}