package app.controller;

import app.entity.Call;
import app.entity.City;
import app.entity.PhoneNumber;
import app.service.CallService;
import app.service.PhoneNumberService;
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
@WebMvcTest(CallController.class)
public class CallControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CallService callService;

    @MockBean
    private PhoneNumberService phoneNumberService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createCall_whenCallExists_thenReturnJsonArray() throws Exception {

        PhoneNumber callerPhoneNumber = new PhoneNumber();
        callerPhoneNumber.setId(100L);
        callerPhoneNumber.setPhone("0123456789");

        PhoneNumber recipientPhoneNumber = new PhoneNumber();
        recipientPhoneNumber.setId(101L);
        recipientPhoneNumber.setPhone("9876543210");

        Call call = new Call();
        call.setId(1000L);
        call.setCallerPhoneNumber(callerPhoneNumber);
        call.setRecipientPhoneNumber(recipientPhoneNumber);
        call.setCity(new City("Dnipro"));

        given(callService.exists(ArgumentMatchers.any(Call.class))).willReturn(false);
        given(phoneNumberService.exists(ArgumentMatchers.any(PhoneNumber.class))).willReturn(true);

        mvc.perform(post("/api/call/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(call)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city.name", is(call.getCity().getName())));
    }

    @Test
    public void createCall_whenCallDoesnotExist_thenReturnErrorMessage() throws Exception {

        Call call = new Call();
        call.setId(1L);

        given(callService.exists(ArgumentMatchers.any(Call.class))).willReturn(true);
        given(phoneNumberService.exists(ArgumentMatchers.any(PhoneNumber.class))).willReturn(true);

        mvc.perform(post("/api/call/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(call)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("Such call already exists")));
    }
}