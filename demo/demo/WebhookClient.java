package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebhookClient implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void run(String... args) {
        try {
            // Step 1: Generate Webhook
            String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("name", "Saurabh Malik");
            requestBody.put("regNo", "REG12347");  // Your own student ID
            requestBody.put("email", "saurabh2269.be22@chitkara.edu.in");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String webhook = response.getBody().get("webhook").toString();
                String accessToken = response.getBody().get("accessToken").toString();

                System.out.println("✅ Webhook: " + webhook);
                System.out.println("✅ AccessToken: " + accessToken);

                // Step 2: Solve the SQL problem (REPLACE with actual query)
                String finalQuery = "SELECT p.AMOUNT AS SALARY, " +
                        "CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, " +
                        "TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE, " +
                        "d.DEPARTMENT_NAME " +
                        "FROM PAYMENTS p " +
                        "JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID " +
                        "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID " +
                        "WHERE DAY(p.PAYMENT_TIME) != 1 " +
                        "ORDER BY p.AMOUNT DESC LIMIT 1;";


                // Step 3: Submit the query to the webhook
                Map<String, String> submission = new HashMap<>();
                submission.put("finalQuery", finalQuery);

                HttpHeaders authHeaders = new HttpHeaders();
                authHeaders.setContentType(MediaType.APPLICATION_JSON);
                authHeaders.setBearerAuth(accessToken); // JWT token

                HttpEntity<Map<String, String>> postEntity = new HttpEntity<>(submission, authHeaders);

                ResponseEntity<String> postResponse = restTemplate.postForEntity(webhook, postEntity, String.class);
                System.out.println("✅ Submission Response: " + postResponse.getBody());
            } else {
                System.out.println("❌ Failed to register or fetch webhook/token.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
