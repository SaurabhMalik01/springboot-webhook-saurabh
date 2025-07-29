package com.example.webhookclient;

import com.example.webhookclient.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class WebhookClientApplication {

	@Autowired
	private WebhookService webhookService;

	public static void main(String[] args) {
		SpringApplication.run(WebhookClientApplication.class, args);
	}


	public void runOnStartup() {
		webhookService.execute();
	}
}
