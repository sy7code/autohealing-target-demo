package com.example.demo;

public class VulnerableService {
    // Snyk should detect this hardcoded dummy secret
    private static final String DUMMY_API_KEY = "AIzaSyA1B2C3D4E5F6G7H8I9J0K1L2M3N4O5P6Q";

    public void processData() {
        System.out.println("Processing data with key: " + DUMMY_API_KEY);
    }
}
