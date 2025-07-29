package com.example.webhookclient.service;

import com.example.webhookclient.model.WebhookRequest;
import com.example.webhookclient.model.WebhookResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String INIT_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

    public void execute() {
        WebhookRequest request = new WebhookRequest("John Doe", "REG12347", "john@example.com");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<WebhookRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<WebhookResponse> response = restTemplate.exchange(
                INIT_URL, HttpMethod.POST, httpEntity, WebhookResponse.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            WebhookResponse webhookResponse = response.getBody();
            String webhookUrl = webhookResponse.getWebhook();
            String token = webhookResponse.getAccessToken();

            // Decide which question to solve based on regNo
            String regNo = request.getRegNo();
            int lastDigit = Integer.parseInt(regNo.replaceAll("\\D", "")) % 10;

            String finalSQL = (lastDigit % 2 == 0)
                    ? "SELECT * FROM even_table WHERE condition = true;" // Replace with real solution
                    : "SELECT * FROM odd_table WHERE condition = true;";  // Replace with real solution

            submitFinalQuery(webhookUrl, token, finalSQL);
        }
    }

    private void submitFinalQuery(String webhookUrl, String accessToken, String finalSQL) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = String.format("{\"finalQuery\": \"%s\"}", finalSQL);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, entity, String.class);

        System.out.println("Submission Response: " + response.getBody());
    }
}
