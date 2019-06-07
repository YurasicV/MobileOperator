package app.controller;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CallControllerTest {
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private CallController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void createCall() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/call/";
        URI uri = new URI(baseUrl);

        String request = "{" +
                "\"callerPhoneNumber\": {\"phone\": \"0501234567\"}," +
                "\"recipientPhoneNumber\": {\"phone\": \"0731234567\"}," +
                "\"callDt\": \"2019-06-06T04:20:00.000+0000\"," +
                "\"duration\": 500," +
                "\"city\": {\"name\": \"Kyiv\"}" +
                "}";
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("id"));
    }
}