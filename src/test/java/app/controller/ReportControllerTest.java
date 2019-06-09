package app.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportControllerTest {
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private ReportController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void listCallsPerCities() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/calls-per-cities/";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("callsNumber"));
    }

    @Test
    public void getTheLongestCall() throws URISyntaxException, JSONException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/the-longest-call/";
        URI uri = new URI(baseUrl);

        String request = "{\"clientId\": 1,\"dateFrom\": \"2019-06-04\",\"dateTo\": \"2019-06-07\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JSONObject> entity = new HttpEntity<>(new JSONObject(request), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class);

        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("id"));
    }
}