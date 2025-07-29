package com.example.webhookclient.model;

public class WebhookRequest {
    private String name;
    private String regNo;
    private String email;

    // ✅ Constructor to fix the error
    public WebhookRequest(String name, String regNo, String email) {
        this.name = name;
        this.regNo = regNo;
        this.email = email;
    }

    // ✅ Getters
    public String getName() {
        return name;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getEmail() {
        return email;
    }

    // Optional: Setters if needed
}
