package app.controller;

import app.entity.City;
import app.service.CityService;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
public class CityControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CityService cityService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createCity_whenCityExists_thenReturnJsonArray() throws Exception {

        City dnipro = new City();
        dnipro.setId(1L);
        dnipro.setName("Dnipro");

        given(cityService.exists(ArgumentMatchers.any(City.class))).willReturn(false);

        mvc.perform(post("/api/city/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dnipro)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(dnipro.getName())));
    }

    @Test
    public void createCity_whenCityDoesnotExist_thenReturnErrorMessage() throws Exception {

        City dnipro = new City();
        dnipro.setId(1L);
        dnipro.setName("Dnipro");

        given(cityService.exists(ArgumentMatchers.any(City.class))).willReturn(true);

        mvc.perform(post("/api/city/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dnipro)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is(String.format("City %s already exists",
                        dnipro.getName()))));
    }
}