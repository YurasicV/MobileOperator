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
public class ClientControllerTest {
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private ClientController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void createClient() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/client/";
        URI uri = new URI(baseUrl);

        String request = "{" +
                "\"fullName\": \"Fedorov\"," +
                "\"birthday\": \"1990-04-04\"," +
                "\"gender\": \"MALE\"," +
                "\"phoneNumbers\": [" +
                "{\"phone\": \"0661234567\"}," +
                "{\"phone\": \"0501234567\"}" +
                "]" +
                "}";
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("id"));
    }
}